package functions;

import java.util.List;

/**
 * @className: UppercaseFunc
 * @packageName: functions
 * @author 王如轩
 * @description: 实现Uppercase
 **/
public class UppercaseFunc extends RemoteFunc {
    private static final UppercaseFunc INSTANCE = new UppercaseFunc();

    public static String ErrorInfo = "";

    public static UppercaseFunc getInstance(){
        return INSTANCE;
    }
    public static String getNAME() {
        return "uppercase";
    }

    @Override
    /**
     * @param params: 参数列表
     * @return Object
     * @author 王如轩
     * @description 实现 string uppercase(str)函数功能
     */
    public Object function(List<Object> params){
        if (params.size() != 1 || !(params.get(0) instanceof String)){
            ErrorInfo = "uppercase 函数参数错误，需要参数为："+String.class.getSimpleName()+"，您的参数为：" + RemoteFunc.GetArgsTypes(params);
            System.out.println(ErrorInfo);
            return null;
        }
        try{
            return ((String) params.get(0)).toUpperCase();
        }catch (Exception e){
            return null;
        }
    }

    @Override
    public String GetErrorInfo(){
        return ErrorInfo;
    }

}
