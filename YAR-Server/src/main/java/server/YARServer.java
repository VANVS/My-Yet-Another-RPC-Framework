package server;

import Utils.CommandUtil;
import bean.SocketParam;
import org.apache.commons.cli.CommandLine;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class YARServer implements Runnable{
    private final String ip;
    private final int port;
    private boolean stop;

    private static final ExecutorService executor = Executors.newCachedThreadPool();


    public YARServer(String ip, int port) {
        this.ip = ip;
        this.port = port;
        stop = false;
    }

    public void stop() {
        stop = true;
        Thread.currentThread().interrupt();
    }

    @Override
    public void run(){
        try {
            ServerSocket serverSocket = new ServerSocket();
            serverSocket.bind(new InetSocketAddress(ip, port));

            System.out.format("Server bind to %s:%d success",ip, port);

            Socket socket ;
            while((socket = serverSocket.accept()) != null && !stop){
                System.out.println("accept " + socket.getRemoteSocketAddress());
                YARServerStub serverStub = new YARServerStub(socket);
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
        //String func = cmd.getOptionValue("f");

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
