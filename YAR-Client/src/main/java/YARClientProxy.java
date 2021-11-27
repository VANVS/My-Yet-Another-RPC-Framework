import bean.RPCRequest;
import bean.RPCResponse;
import InvokeMethods.Invoke;
import InvokeMethods.SocketInvoke;

import java.net.Socket;
import java.util.Map;
import java.util.HashMap;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;

import static bean.SocketParam.DEFAULT_IP;
import static bean.SocketParam.DEFAULT_PORT;

/*
* Yar_Client::__call — 调用远程服务
* Yar_Client::__construct — 创建一个客户端实例
* Yar_Client::setOpt — 设置调用的配置
* */
public class YARClientProxy implements RPCClientProxy {
    private final String _ip;
    private final int _port;

    // resultMap的作用是将请求ID和其调用服务端函数的结果一一对应，防止多线程下混乱
    private final Map<String, FutureTask<RPCResponse>> resultMap;

    private static YARClientProxy INSTANCE = null;

    public static YARClientProxy GetInstance(String ip, int port){
        if(INSTANCE == null){
            INSTANCE = new YARClientProxy(ip, port);
        }
        return INSTANCE;
    }

    public YARClientProxy(String ip, int port){
        this._ip = ip;
        this._port = port;

        resultMap = new HashMap<>();
    }

    public YARClientProxy(){
        this(DEFAULT_IP, DEFAULT_PORT);
    }

    // 添加请求
    public void addRequest(RPCRequest request){
        Invoke invoke = new SocketInvoke(request, _ip, _port);
        FutureTask<RPCResponse> task = new FutureTask<>(invoke);
        /*synchronized (this){
            resultMap.put(request.getRequestID(), task);
            System.out.println("请求 "+ request.getRequestID() + " 已添加到队列");
        }*/
        resultMap.put(request.getRequestID(), task);
        //System.out.println("请求 "+ request.getRequestID() + " 已添加到队列");
    }

    /*public void addRequest(RPCRequest request, Socket socket){
        Invoke invoke = new SocketInvoke(request, socket, _ip, _port);
        FutureTask<RPCResponse> task = new FutureTask<>(invoke);
        resultMap.put(request.getRequestID(), task);
    }*/


    public RPCResponse Call(String RequestID) throws ExecutionException, InterruptedException {
        if(!resultMap.containsKey(RequestID)){
            System.out.println("请求 "+ RequestID + " 不存在");
            return null;
        }
        FutureTask<RPCResponse> task = resultMap.get(RequestID);
        new Thread(task).start();

        // 远程调用完获得结果后将对应的关系剔除
        resultMap.remove(RequestID);
        return task.get();
    }



}
