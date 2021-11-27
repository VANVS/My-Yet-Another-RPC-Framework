package InvokeMethods;

import bean.RPCResponse;

import java.util.concurrent.Callable;

public interface Invoke extends Callable<RPCResponse> {
}
