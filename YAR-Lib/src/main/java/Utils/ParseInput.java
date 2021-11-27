package Utils;

public class ParseInput {
    private static boolean isNumber(String str){
        String reg = "^[0-9]+(.[0-9]+)?$";
        return str.matches(reg);
    }

    public static Object TransferInputToObject(String str){
        if(!isNumber(str)){
            return str;
        }else{
            if(str.contains(".")){
                return Float.valueOf(str);
            }else{
                return Integer.valueOf(str);
            }
        }
    }
}
