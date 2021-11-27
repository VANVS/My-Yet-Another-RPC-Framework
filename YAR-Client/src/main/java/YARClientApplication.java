import Utils.CommandUtil;
import Utils.ParseInput;
import bean.SocketParam;
import com.alibaba.fastjson.*;
import org.apache.commons.cli.CommandLine;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class YARClientApplication {
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
        for(String arg :args){
            params.add(ParseInput.TransferInputToObject(arg));
        }
        return params;
    }


    public static void main(String[] args){
        //List<Object> params = GetParams();
        CommandLine cmd = CommandUtil.getCommandLine(args);

        String ip = cmd.getOptionValue("i") == null ? SocketParam.DEFAULT_IP : cmd.getOptionValue("i");
        int port = cmd.getOptionValue("p") == null ? SocketParam.DEFAULT_PORT : Integer.parseInt(cmd.getOptionValue("p"));
        String func = cmd.getOptionValue("f")== null ? "sum" : cmd.getOptionValue("f");

        YARClientStub stub = new YARClientStub(ip, port);
        Object result = stub.InvokeServer(func, GetParams());
        System.out.println(result);
    }
}
