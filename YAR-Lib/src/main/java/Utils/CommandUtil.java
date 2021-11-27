package Utils;

import org.apache.commons.cli.*;

public class CommandUtil {
    public static CommandLine getCommandLine(String[] args){
        Options options = new Options();

        // 设置传入参数
        options.addOption(Option.builder("i").hasArg()
                        .longOpt("ip")
                        .hasArg(true)
                        .required(false)
                        .desc("远程服务端的ip")
                        .type(String.class)
                        .build());

        options.addOption(Option.builder("p").hasArg()
                .longOpt("port")
                .hasArg(true)
                .required(false)
                .desc("服务端绑定ip")
                .type(Integer.class)
                .build());

        options.addOption(Option.builder("f").hasArg()
                .longOpt("func")
                .hasArg(true)
                .required(false)
                .desc("调用服务端的函数名")
                .type(String.class)
                .build());


        DefaultParser commandParser = new DefaultParser();
        HelpFormatter formatter = new HelpFormatter();
        CommandLine cmd = null;
        String header = "参数要求如下：";
        String foot = "";
        //formatter.printHelp("options", header,options,foot);

        try {
            cmd = commandParser.parse(options, args);
        } catch (ParseException e) {
            formatter.printHelp("options", header,options,foot);
            System.exit(1);
        }

        String ip = cmd.getOptionValue("i");
        if(ip != null && !ip.matches("(?=(\\b|\\D))(((\\d{1,2})|(1\\d{1,2})|(2[0-4]\\d)|(25[0-5]))\\.){3}((\\d{1,2})|(1\\d{1,2})|(2[0-4]\\d)|(25[0-5]))(?=(\\b|\\D))"))
        {
            System.out.println("ip地址格式错误！");
            return null;
        }

        return cmd;
    }



}
