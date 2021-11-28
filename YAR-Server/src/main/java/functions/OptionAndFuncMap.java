package functions;

import java.util.List;
import java.util.Map;
import java.util.HashMap;
import java.util.Set;

/**
 * @className: OptionAndFuncMap
 * @packageName: functions
 * @author 王如轩
 * @description: 提供函数标识符和对应函数的对应关系
 **/
public class OptionAndFuncMap {
    // 存放函数标识符和对应函数的对应关系，这也是抽象RemoteFunc的原因
    static Map<String, RemoteFunc> map = new HashMap<>();

    /**
     * 将
     * "sum" - float sum(float a, float b)
     * "uppercase" - string uppercase(str)
     * 存入对应关系表中
     * **/
    static {
        map.put(SumFunc.getNAME(),SumFunc.getInstance());
        map.put(UppercaseFunc.getNAME(),UppercaseFunc.getInstance());
    }

    /**
     * @param id: 函数标识符
     * @param func: 新的函数功能
     * @return void
     * @author 王如轩
     * @description 添加新的对应关系
     */
    public static void AddFunc(String id, RemoteFunc func){
        if (map.containsKey(id)){
            System.out.println("该函数标识符已存在！");
            return;
        }

        map.put(id, func);
    }

    /**
     * @param id: 函数标识符
     * @return void
     * @author 王如轩
     * @description 移除已有的对应关系
     */
    public static void RemoveFunc(String id){
        if (!map.containsKey(id)){
            System.out.println("该函数标识符不存在！");
            return;
        }
        map.remove(id);
    }

    /**
     * @param id: 函数标识符
     * @param params: 函数参数列表
     * @return Object
     * @author 王如轩
     * @description 根据函数标识符和参数列表调用相应的函数
     */
    public static Object CallFunc(String id, List<Object> params){
        return map.get(id).function(params);
    }

    /**
     * @param id: 函数标识符
     * @return String
     * @author 王如轩
     * @description 根据函数标识符返回错误信息
     */
    public static String GetFuncErrorInfo(String id){return map.get(id).GetErrorInfo();}

    /**
     * @param :
     * @return Set<String>
     * @author 王如轩
     * @description 获取所有函数标识符
     */
    public static Set<String> GetAllFuncName(){
        return map.keySet();
    }
}
