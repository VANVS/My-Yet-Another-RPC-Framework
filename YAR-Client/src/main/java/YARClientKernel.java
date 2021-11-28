import beans.RPCRequest;
import beans.RPCResponse;
import InvokeMethods.Invoke;
import InvokeMethods.SocketInvoke;

import java.util.Map;
import java.util.HashMap;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;

import static beans.SocketParam.DEFAULT_IP;
import static beans.SocketParam.DEFAULT_PORT;

/**
 * @className: YARClientKernel
 * @packageName: YAR-Client
 * @author 王如轩
 * @description: client核心类，负责管理多线程服务请求，并通过TCP向服务器发送请求调用函数，并返回调用结果
 **/
public class YARClientKernel implements RPCClientKernel {
    private final String _ip;
    private final int _port;

    /*** resultMap的作用是将请求ID和其调用服务端函数的结果一一对应，防止多线程下混乱 ***/
    private final Map<String, FutureTask<RPCResponse>> resultMap;

    /** 饿汉模式返回单例对象 **/
    private static YARClientKernel INSTANCE = null;

    public static YARClientKernel GetInstance(String ip, int port){
        if(INSTANCE == null){
            INSTANCE = new YARClientKernel(ip, port);
        }
        return INSTANCE;
    }

    /**
     * @description: 构造函数
     * @param: ip：服务端IP地址
     *         port：服务端监听端口
     **/
    public YARClientKernel(String ip, int port){
        this._ip = ip;
        this._port = port;

        resultMap = new HashMap<>();
    }

    public YARClientKernel(){
        this(DEFAULT_IP, DEFAULT_PORT);
    }

    /**
     * @param request: 请求
     * @return void
     * @author 王如轩
     * @description 添加请求，将请求唯一标识符ID和调用服务的任务关联起来
     */
    public void addRequest(RPCRequest request){
        Invoke invoke = new SocketInvoke(request, _ip, _port);
        FutureTask<RPCResponse> task = new FutureTask<>(invoke);
        resultMap.put(request.getRequestID(), task);
        //System.out.println("请求 "+ request.getRequestID() + " 加入队列");
    }

    /**
     * @param RequestID:请求唯一标识符
     * @return RPCResponse
     * @author 王如轩
     * @description 根据提供的请求标识符，取出相应的请求任务并运行，调用远程服务并获得结果
     */
    public RPCResponse Call(String RequestID) throws ExecutionException, InterruptedException {
        if(!resultMap.containsKey(RequestID)){
            System.out.println("请求 "+ RequestID + " 不存在");
            return null;
        }
        //
        FutureTask<RPCResponse> task = resultMap.get(RequestID);
        new Thread(task).start();

        // 远程调用完获得结果后将对应的关系剔除
        resultMap.remove(RequestID);
        // 获得远程服务调用结果
        return task.get();
    }



}
