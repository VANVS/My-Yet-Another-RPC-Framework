package InvokeMethods;

import beans.RPCResponse;

import java.util.concurrent.Callable;

/**
 * @interfaceName: YARClientStub.InvokeMethods
 * @packageName: Invoke
 * @author 王如轩
 * @description: 规范调用方法的接口
 **/
public interface Invoke extends Callable<RPCResponse> {
}
