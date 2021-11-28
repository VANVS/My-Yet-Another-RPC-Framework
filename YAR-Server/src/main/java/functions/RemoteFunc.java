package functions;

import java.util.ArrayList;
import java.util.List;

/**
 * @className: RemoteFunc
 * @packageName: functions
 * @author 王如轩
 * @description: 抽象类，为了统一管理服务端所有的服务端函数
 **/
public abstract class RemoteFunc {
    /**
     * @param params: 传入的参数列表
     * @return List<String>
     * @author 王如轩
     * @description 返回每个参数的类型列表
     */
    protected static List<String> GetArgsTypes(List<Object> params){
        List<String> types = new ArrayList<>();
        for(Object param : params){
            types.add(param.getClass().getSimpleName());
        }
        return types;
    }
    /**
     * @param params: 传入的参数列表
     * @return Object
     * @author 王如轩
     * @description 实现类需要实现的具体函数功能
     */
    public abstract Object function(List<Object> params);

    /**
     * @param :
     * @return String
     * @author 王如轩
     * @description 返回调用函数的错误信息（如果有）
     */
    public abstract String GetErrorInfo();
}
