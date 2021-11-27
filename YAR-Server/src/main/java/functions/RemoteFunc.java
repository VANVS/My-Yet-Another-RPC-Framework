package functions;

import java.util.ArrayList;
import java.util.List;

public abstract class RemoteFunc {
    protected static List<String> GetArgsTypes(List<Object> params){
        List<String> types = new ArrayList<>();
        for(Object param : params){
            types.add(param.getClass().getSimpleName());
        }
        return types;
    }
    public abstract Object function(List<Object> params);
    public abstract String GetErrorInfo();
}
