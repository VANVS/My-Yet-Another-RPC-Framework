package server;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.List;

import beans.*;
import functions.OptionAndFuncMap;


/**
 * @className: YARServer
 * @packageName: YAR-Server
 * @author 王如轩
 * @description: server存根类，负责解析客户端请求，并根据请求内容调用相应的参数
 **/
public class YARServerStub implements Runnable{
    private final Socket socket;

    /**
     * @param soc: 接受数据的套接字
     * @return null
     * @author 王如轩
     * @description 构造函数
     */
    public YARServerStub(Socket soc){
        this.socket = soc;
    }

    /**
     * @param request: 客户端的请求
     * @return RPCResponse
     * @author 王如轩
     * @description 解析客户端请求，并根据请求内容调用相应的参数
     */
    private static RPCResponse ParseRequest(RPCRequest request){
        RPCResponse response = new RPCResponse();
        String FuncName = request.getFuncName();
        List<Object> params = request.getParams();
        response.setRequestID(request.getRequestID());
        try{
            // 调用相应的函数
            Object result = OptionAndFuncMap.CallFunc(FuncName,params);
            if(result != null){
                response.setStatus(Status.OK);
                response.setResult(result);
            }else{
                response.setStatus(Status.ERROR);
                response.setResult(OptionAndFuncMap.GetFuncErrorInfo(FuncName));
            }
        }catch (Exception e){
            response.setResult(e.getMessage());
            response.setStatus(Status.ERROR);
        }
        return response;
    }

    @Override
    /**
     * @param :
     * @return void
     * @author 王如轩
     * @description 重新Runnable的run方法，线程调用运行的方法，负责将客户端的输入数据流反序列化到request，解析调用获得结果后，在序列化会返回的输出流中
     */
    public void run(){
        RPCRequest request = new RPCRequest();
        RPCResponse response;
        try{
            request.readExternal(new ObjectInputStream(socket.getInputStream()));
            response = ParseRequest(request);
            ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream());
            response.writeExternal(oos);
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}
