package server;

import Utils.CommandUtil;
import beans.SocketParam;
import org.apache.commons.cli.CommandLine;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @className: YARServer
 * @packageName: YAR-Server
 * @author 王如轩
 * @description: 服务端底层核心类，负责接受客户端数据并进行下一步工作
 **/
public class YARServer implements Runnable{
    private final String ip;
    private final int port;
    private boolean stop;

    /*** 线程池 ***/
    private static final ExecutorService executor = Executors.newCachedThreadPool();

    /**
     * @param ip: 服务端ip地址
     * @param port: 服务端绑定ip
     * @author 王如轩
     * @description 构造函数
     */
    public YARServer(String ip, int port) {
        this.ip = ip;
        this.port = port;
        stop = false;
    }

    /**
     * @param :
     * @return void
     * @author 王如轩
     * @description 停止服务
     */
    public void stop() {
        stop = true;
        Thread.currentThread().interrupt();
    }

    @Override
    /**
     * @param :
     * @return void
     * @author 王如轩
     * @description 重新Runnable的run方法，线程调用运行的方法，负责绑定监听端口并接受客户端发来的请求，调用stub进行解析和调用
     */
    public void run(){
        try {
            ServerSocket serverSocket = new ServerSocket();
            // 绑定地址端口
            serverSocket.bind(new InetSocketAddress(ip, port));

            System.out.format("Server bind to %s:%d success\n",ip, port);

            Socket socket ;
            // 一直接受客户端的数据，并调用stub解析完成相应功能
            while((socket = serverSocket.accept()) != null && !stop){
                System.out.println("accept " + socket.getRemoteSocketAddress());
                YARServerStub serverStub = new YARServerStub(socket);
                // 线程池执行线程
                executor.submit(serverStub);
            }
        } catch (IOException e) {
            e.printStackTrace();
            //System.out.println(e.getMessage());
        }

    }

    public static void main(String[] args){
        CommandLine cmd = CommandUtil.getCommandLine(args);

        String ip = cmd.getOptionValue("i") == null ? SocketParam.DEFAULT_BIND_IP : cmd.getOptionValue("i");
        int port = cmd.getOptionValue("p") == null ? SocketParam.DEFAULT_PORT : Integer.parseInt(cmd.getOptionValue("p"));

        YARServer server = new YARServer(ip, port);
        Thread t = new Thread(server);
        t.start();
        try {
            t.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
