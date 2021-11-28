package functions;

import java.util.ArrayList;
import java.util.List;

/**
 * @className: SumFunc
 * @packageName: functions
 * @author 王如轩
 * @description: 实现RemoteFunc
 **/
public class SumFunc extends RemoteFunc {
    private static final SumFunc INSTANCE = new SumFunc();

    public static SumFunc getInstance(){
        return INSTANCE;
    }

    public static String getNAME() {
        return "sum";
    }

    public static String ErrorInfo = "";

    /**
     * @param params: 参数列表
     * @return Object
     * @author 王如轩
     * @description 实现 float sum(float a, float b)函数功能
     */
    @Override
    public Object function(List<Object> params){
        // 判断参数类型是否正确
        List<String> correctTypes = new ArrayList<>();
        correctTypes.add(Float.class.getSimpleName());
        correctTypes.add(Float.class.getSimpleName());
        if (params.size() != 2 || (!(params.get(0) instanceof Float) && !(params.get(1) instanceof Float))){
            ErrorInfo = "uppercase 函数参数错误，需要参数为："+correctTypes+"，您的参数为：" + RemoteFunc.GetArgsTypes(params);
            System.out.println(ErrorInfo);
            return null;
        }
        try {
            return (float)params.get(0) + (float)params.get(1);
        }catch (Exception e){
            return null;
        }
    }

    @Override
    public String GetErrorInfo(){
        return ErrorInfo;
    }

}
