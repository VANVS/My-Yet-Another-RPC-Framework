package InvokeMethods;

import bean.RPCRequest;
import bean.RPCResponse;
import bean.Status;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.util.concurrent.Callable;

public class SocketInvoke implements Invoke, Callable<RPCResponse> {
    private final RPCRequest request;
    private final Socket socket;
    private final String ip;
    private final int port;

    private static final short RETRY_TIMES = 3;

    public SocketInvoke(RPCRequest req, String ip,int port){
        this.request = req;
        this.ip = ip;
        this.port = port;
        this.socket = new Socket();
    }

    /*public SocketInvoke(RPCRequest req, Socket soc, String ip,int port){
        this.request = req;
        this.ip = ip;
        this.port = port;
        this.socket = soc;
    }*/

    private void doResquest(RPCResponse response) throws IOException, ClassNotFoundException{
        socket.connect(new InetSocketAddress(ip, port));
        request.writeExternal(new ObjectOutputStream(socket.getOutputStream()));
        response.readExternal(new ObjectInputStream(socket.getInputStream()));
    }

    @Override
    public RPCResponse call(){
        RPCResponse response = new RPCResponse();
        response.setRequestID(request.getRequestID());
        try{
            doResquest(response);
        }catch (Exception e1){
            for(int i=0 ; i<RETRY_TIMES; i++){
                System.out.println("调用服务端失败，正在尝试重连("+ (i + 1) +")...");
                try{
                    doResquest(response);
                    break;
                }catch (Exception e2){
                    response.setStatus(Status.ERROR);
                    response.setResult(e2.getMessage());
                    try{
                        Thread.sleep(1000);
                    }catch (InterruptedException e3){
                        e3.printStackTrace();
                    }
                }
            }
        }

        if(socket != null && socket.isConnected()){
            try{
                socket.close();
            }catch (IOException e4){
                e4.printStackTrace();
            }
        }
        return response;
    }
}