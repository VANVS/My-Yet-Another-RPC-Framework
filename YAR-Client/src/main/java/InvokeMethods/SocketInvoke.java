package InvokeMethods;

import beans.RPCRequest;
import beans.RPCResponse;
import beans.Status;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.util.concurrent.Callable;

/**
 * @className: SocketInvoke
 * @packageName: YAR-Client.InvokeMethods
 * @author 王如轩
 * @description: 线程Thread实现类，通过socket(TCP)，并发地调用远端服务。
 **/
public class SocketInvoke implements Invoke, Callable<RPCResponse> {
    private final RPCRequest request;
    private final Socket socket;
    private final String ip;
    private final int port;

    /*** 尝试重连的最大次数 ***/
    private static final short RETRY_TIMES = 3;

    /**
     * @param req: 待发送的请求
     * @param ip: 服务IP地址
     * @param port: 服务监听绑定端口
     * @return null
     * @author 王如轩
     * @description 构造函数
     */
    public SocketInvoke(RPCRequest req, String ip,int port){
        this.request = req;
        this.ip = ip;
        this.port = port;
        this.socket = new Socket();
    }

    /**
     * @param response: 通过socket连接服务端，发送请求并获得响应
     * @return void
     * @author 王如轩
     * @description TODO
     */
    private void doResquest(RPCResponse response) throws IOException, ClassNotFoundException{
        // 连接服务端
        socket.connect(new InetSocketAddress(ip, port));
        // 请求序列化成数据流请求并发送至服务端
        request.writeExternal(new ObjectOutputStream(socket.getOutputStream()));
        // 接受服务端数据流并反序列化至响应
        response.readExternal(new ObjectInputStream(socket.getInputStream()));
    }

    @Override
    /**
     * @param :
     * @return RPCResponse
     * @author 王如轩
     * @description : 实现Callable接口中的run方法（线程运行的方法），发送请求并处理异常
     */
    public RPCResponse call(){
        RPCResponse response = new RPCResponse();
        // 设置该响应对应的请求ID
        response.setRequestID(request.getRequestID());
        try{
            // 尝试发送请求
            doResquest(response);
        }catch (Exception e1){
            // 尝试重新连接
            for(int i=0 ; i<RETRY_TIMES; i++){
                System.out.println("调用服务端失败，正在尝试重连("+ (i + 1) +")...");
                try{
                    doResquest(response);
                    break;
                }catch (Exception e2){
                    response.setStatus(Status.ERROR);
                    response.setResult(e2.getMessage());
                    try{
                        // 过一秒后重发
                        Thread.sleep(1000);
                    }catch (InterruptedException e3){
                        e3.printStackTrace();
                    }
                }
            }
        }

        // 发送成功后尝试关闭socket连接
        if(socket != null && socket.isConnected()){
            try{
                socket.close();
            }catch (IOException e4){
                e4.printStackTrace();
            }
        }
        return response;
    }
}