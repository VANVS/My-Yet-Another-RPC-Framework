import bean.SocketParam;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import server.YARServer;
import functions.*;

import java.util.Random;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static junit.framework.TestCase.*;

public class TestCilent {
    private static final String ip = SocketParam.DEFAULT_IP;
    private static final int port = SocketParam.DEFAULT_PORT;

    private static YARServer server;
    //private static YARClientStub clientStub;

    private static ExecutorService threadPool = Executors.newCachedThreadPool();

    @BeforeClass
    public static void init() {
        server = new YARServer(ip, port);
        new Thread(server).start();
        //clientStub = new YARClientStub(ip, port);
    }


    @Test
    public void TestMutiCilents(){
        int clientNums = 5;
        List<Callable<Boolean>> tasks = new ArrayList<>();
        for(int i=0; i<clientNums; i++){
            YARClientStub clientStub = new YARClientStub(ip, port);
            MockTask task = new MockTask(clientStub);
            threadPool.submit(task);
            tasks.add(task);
        }
        for( Callable<Boolean> task :tasks){
            try {
                assertTrue(task.call());
            } catch (Exception e) {
                e.printStackTrace();
                fail();
                break;
            }
        }
    }

    @AfterClass
    public static void StopService(){
        server.stop();
    }

    private static class MockTask implements Callable<Boolean>{
        Random random = new Random();
        private final YARClientStub clientStub;

        public MockTask(YARClientStub stub){
            this.clientStub = stub;
        }

        @Override
        public Boolean call() throws Exception{
            if(random.nextInt() % 2 == 0){
                List<Object> params = new ArrayList<>();
                float a = random.nextFloat(),b =random.nextFloat();
                params.add(a);
                params.add(b);
                //System.out.println(a + "+" + b + "=" + clientStub.InvokeServer(SumFunc.getNAME(), params));
                return ((float)clientStub.InvokeServer(SumFunc.getNAME(), params))==(a+b);
            }else{
                List<Object> params = new ArrayList<>();
                String str = UUID.randomUUID().toString();
                params.add(str);
                return str.toUpperCase().equals((String)clientStub.InvokeServer(UppercaseFunc.getNAME(),params));
            }
        }
    }
}
