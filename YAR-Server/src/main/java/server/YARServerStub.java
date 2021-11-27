package server;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.util.List;

import bean.*;
import functions.OptionAndFuncMap;

public class YARServerStub implements Runnable{
    private final Socket socket;

    public YARServerStub(Socket soc){
        this.socket = soc;
    }

    private static RPCResponse ParseRequest(RPCRequest request){
        RPCResponse response = new RPCResponse();
        String FuncName = request.getFuncName();
        List<Object> params = request.getParams();
        response.setRequestID(request.getRequestID());
        try{
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
