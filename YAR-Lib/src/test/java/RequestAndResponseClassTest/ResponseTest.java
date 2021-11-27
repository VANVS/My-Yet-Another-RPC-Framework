package RequestAndResponseClassTest;

import bean.RPCResponse;
import bean.Status;
import org.junit.Test;

import java.io.*;

import static junit.framework.TestCase.assertEquals;

public class ResponseTest {
    private final static String path = "D:\\Graduate Study\\2-1\\分布式\\MyYAR\\YAR-LIB\\src\\test\\java\\RequestAndResponseClassTest\\";
    @Test
    public void TestSerializeAndUnSerialize(){
        RPCResponse response1 = new RPCResponse(Status.OK, "TestID", 1), response2 = new RPCResponse();
        try{
            ObjectOutput oo = new ObjectOutputStream(new FileOutputStream(path + "test2"));
            response1.writeExternal(oo);

            ObjectInput oi = new ObjectInputStream(new FileInputStream(path + "test2"));
            response2.readExternal(oi);

            assertEquals(response2.getRequestID(),"TestID");
            assertEquals(response2.getStatus(),Status.OK);
            assertEquals((int)response2.getResult(),1);
        } catch (IOException | ClassNotFoundException e) {
            assertEquals(0,1);
            e.printStackTrace();
        }

    }

}
