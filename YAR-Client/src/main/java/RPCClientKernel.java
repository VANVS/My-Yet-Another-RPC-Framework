import beans.RPCRequest;
import beans.RPCResponse;

// 规定RPC内核应该具有的方法，即添加请求、请求服务端函数方法
public interface RPCClientKernel {
    public void addRequest(RPCRequest request);
    public RPCResponse Call(String RequestID) throws Exception;
}
