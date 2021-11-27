import bean.RPCRequest;
import bean.RPCResponse;

import java.util.List;

public interface RPCClientProxy {
    public void addRequest(RPCRequest request);
    public RPCResponse Call(String RequestID) throws Exception;
}
