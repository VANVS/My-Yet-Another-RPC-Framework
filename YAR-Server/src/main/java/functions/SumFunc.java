package functions;

import java.util.ArrayList;
import java.util.List;

public class SumFunc extends RemoteFunc {
    private static SumFunc INSTANCE = new SumFunc();
    private static String NAME = "sum";
    /*static {
        OptionAndFuncMap.AddFunc("sum", INSTANCE);
    }*/
    public static SumFunc getInstance(){
        return INSTANCE;
    }

    public static String getNAME() {
        return NAME;
    }

    public static String ErrorInfo = "";

    @Override
    public Object function(List<Object> params){
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
