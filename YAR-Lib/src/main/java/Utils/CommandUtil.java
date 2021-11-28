package Utils;

import org.apache.commons.cli.*;

/**
 * @className: CommandUtil
 * @packageName: Utils
 * @author 王如轩
 * @description: 命令行解析类，负责设置接受的参数规范
 **/
public class CommandUtil {
    /**
     * @param args: 命令行接受到的参数
     * @return CommandLine
     * @author 王如轩
     * @description 调用org.apache.commons.cli包，接受并解析命令行参数
     */
    public static CommandLine getCommandLine(String[] args){
        Options options = new Options();

        // 设置传入参数
        // -i 远程服务ip
        options.addOption(Option.builder("i").hasArg()
                        .longOpt("ip")
                        .hasArg(true)
                        .required(false)
                        .desc("远程服务端的ip")
                        .type(String.class)
                        .build());
        // -p 远程服务绑定的监听端口
        options.addOption(Option.builder("p").hasArg()
                .longOpt("port")
                .hasArg(true)
                .required(false)
                .desc("服务端绑定ip")
                .type(Integer.class)
                .build());
        // -f 调用的远程函数
        options.addOption(Option.builder("f").hasArg()
                .longOpt("func")
                .hasArg(true)
                .required(false)
                .desc("调用服务端的函数名")
                .type(String.class)
                .build());

        DefaultParser commandParser = new DefaultParser();

        // 设置打印的帮助信息
        HelpFormatter formatter = new HelpFormatter();
        CommandLine cmd = null;
        String header = "参数要求如下：";
        String foot = "";

        try {
            // 解析收到的参数
            cmd = commandParser.parse(options, args);
        } catch (ParseException e) {
            formatter.printHelp("options", header,options,foot);
            System.exit(1);
        }

        // 判断ip地址格式是否正确
        String ip = cmd.getOptionValue("i");
        if(ip != null && !ip.matches("(?=(\\b|\\D))(((\\d{1,2})|(1\\d{1,2})|(2[0-4]\\d)|(25[0-5]))\\.){3}((\\d{1,2})|(1\\d{1,2})|(2[0-4]\\d)|(25[0-5]))(?=(\\b|\\D))"))
        {
            System.out.println("ip地址格式错误！");
            return null;
        }

        return cmd;
    }



}
