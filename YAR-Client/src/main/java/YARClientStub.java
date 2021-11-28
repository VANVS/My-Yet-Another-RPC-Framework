import beans.RPCRequest;
import beans.RPCResponse;
import beans.Status;
import java.util.UUID;

import java.util.List;

/**
 * @className: YARClientStub
 * @packageName: YAR-Client
 * @author 王如轩
 * @description: client存根类，存放服务端的地址消息，再将客户端的请求参数打包成网络消息，然后通过调用kernel远程发送给服务方。
 **/
public class YARClientStub {
    private final RPCClientKernel kernel;

    /**
     * @param ip: 服务端ip地址
     * @param port: 服务端绑定ip
     * @return null
     * @author 王如轩
     * @description 构造函数，初始化唯一的内核
     */
    public YARClientStub(String ip, int port){
        kernel = YARClientKernel.GetInstance(ip, port);
    }

    /**
     * @param funcName: 调用的服务端函数名
     * @param params: 调用的远程函数参数列表
     * @return Object
     * @author 王如轩
     * @description 通过提供的函数名和参数列表，调用远程服务
     */
    public Object InvokeServer(String funcName, List<Object> params){
        // 设置请求中的参数
        RPCRequest request = new RPCRequest(funcName);
        request.addParams(params);
        request.setRequestID(UUID.randomUUID().toString());
        kernel.addRequest(request);

        RPCResponse response;
        try{
            // 通过kernel发送请求
            response = kernel.Call(request.getRequestID());
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }

        if(response.getStatus() != Status.OK){
            System.out.println("服务器状态发生错误！");
            System.out.println(response.getResult());
            return null;
        }
        // 返回结果
        return response.getResult();
    }



}
