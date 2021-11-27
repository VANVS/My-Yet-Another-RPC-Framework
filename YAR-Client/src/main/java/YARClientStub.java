import bean.RPCRequest;
import bean.RPCResponse;
import bean.Status;
import java.util.UUID;

import java.util.List;

public class YARClientStub {
    private final RPCClientProxy proxy;

    public YARClientStub(String ip, int port){
        proxy = YARClientProxy.GetInstance(ip, port);
    }


    public Object InvokeServer(String funcName, List<Object> params){
        RPCRequest request = new RPCRequest(funcName);
        request.addParams(params);
        request.setRequestID(UUID.randomUUID().toString());
        proxy.addRequest(request);
        RPCResponse response;
        try{
            response = proxy.Call(request.getRequestID());
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }

        if(response.getStatus() != Status.OK){
            System.out.println("服务器状态发生错误！");
            System.out.println(response.getResult());
            return null;
        }

        return response.getResult();
    }



}
