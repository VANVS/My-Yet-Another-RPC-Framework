package Utils;

/**
 * @className: ParseInput
 * @packageName: Utils
 * @author 王如轩
 * @description: 判断输入参数的类型
 **/
public class ParseInput {
    /**
     * @param str: 输入的字符串
     * @return boolean
     * @author 王如轩
     * @description 判断是否为数字
     */
    private static boolean isNumber(String str){
        String reg = "^[0-9]+(.[0-9]+)?$";
        return str.matches(reg);
    }

    /**
     * @param str: 输入的字符串
     * @return Object
     * @author 王如轩
     * @description 判断输入类型（float，int，string），并以object形式返回
     */
    public static Object TransferInputToObject(String str){
        // string
        if(!isNumber(str)){
            return str;
        }else{
            // float
            if(str.contains(".")){
                return Float.valueOf(str);
            }
            // int
            else{
                return Integer.valueOf(str);
            }
        }
    }
}
