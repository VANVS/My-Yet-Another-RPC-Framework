package functions;

import java.util.List;

public class UppercaseFunc extends RemoteFunc {
    private static UppercaseFunc INSTANCE = new UppercaseFunc();
    private static String NAME = "uppercase";

    public static String ErrorInfo = "";
    /*static {
        OptionAndFuncMap.AddFunc("uppercase", INSTANCE);
    }*/
    public static UppercaseFunc getInstance(){
        return INSTANCE;
    }
    public static String getNAME() {
        return NAME;
    }
    @Override
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
