import Utils.CommandUtil;
import Utils.ParseInput;
import beans.SocketParam;
import org.apache.commons.cli.CommandLine;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;


/**
 * @className: YARClientApplication
 * @packageName: YAR-Client
 * @author 王如轩
 * @description: 客户调用的client应用类，用于接受参数，调用下层stub服务。
 **/
public class YARClientApplication {
    /**
     * @param :
     * @return List<Object>
     * @author 王如轩
     * @description 提醒用户输入函数的参数
     */
    private static List<Object> GetParams(){
        List<Object> params = new ArrayList<>();

        System.out.print("请输入参数并用','隔开：");
        Scanner sc = new Scanner(System.in);
        String str = sc.next();
        if(str.contains("，")){
            System.out.println("请使用英文逗号！");
            System.exit(1);
        }

        String[] args = str.split(",");
        // 为了方便服务端判断类型，需要判断输入的参数的类型，并转成object放入参数列表中
        for(String arg :args){
            params.add(ParseInput.TransferInputToObject(arg));
        }
        return params;
    }

    // client接受参数
    /**
     * @param args: main的命令行参数
     * @return void
     * @author 王如轩
     * @description 接受命令行参数
     */
    public static void main(String[] args){
        List<Object> params = GetParams();
        // 获取命令行参数 YAR
        CommandLine cmd = CommandUtil.getCommandLine(args);

        // 根据命令行参数给所需变量赋值
        String ip = cmd.getOptionValue("i") == null ? SocketParam.DEFAULT_IP : cmd.getOptionValue("i");
        int port = cmd.getOptionValue("p") == null ? SocketParam.DEFAULT_PORT : Integer.parseInt(cmd.getOptionValue("p"));
        String func = cmd.getOptionValue("f")== null ? "sum" : cmd.getOptionValue("f");

        // 调用stub
        YARClientStub stub = new YARClientStub(ip, port);
        Object result = stub.InvokeServer(func, params);
        System.out.println(result);
    }
}
