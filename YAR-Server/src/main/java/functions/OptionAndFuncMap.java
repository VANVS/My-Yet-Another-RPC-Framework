package functions;

import java.util.List;
import java.util.Map;
import java.util.HashMap;
import java.util.Set;
import functions.*;

public class OptionAndFuncMap {
    static Map<String, RemoteFunc> map = new HashMap<>();
    static {
        map.put(SumFunc.getNAME(),SumFunc.getInstance());
        map.put(UppercaseFunc.getNAME(),UppercaseFunc.getInstance());
    }

    public static void AddFunc(String id, RemoteFunc func){
        if (map.containsKey(id)){
            System.out.println("该函数标识符已存在！");
            return;
        }

        map.put(id, func);
    }

    public static void RemoveFunc(String id){
        if (!map.containsKey(id)){
            System.out.println("该函数标识符不存在！");
            return;
        }
        map.remove(id);
    }

    public static Object CallFunc(String id, List<Object> params){
        return map.get(id).function(params);
    }

    public static String GetFuncErrorInfo(String id){return map.get(id).GetErrorInfo();}

    public static Set<String> GetAllFuncName(){
        return map.keySet();
    }
}
