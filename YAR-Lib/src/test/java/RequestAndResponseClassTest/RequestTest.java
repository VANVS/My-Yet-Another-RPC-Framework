package RequestAndResponseClassTest;

import java.io.*;
import java.util.List;

import bean.Status;
import org.junit.Test;
import bean.RPCRequest;

import static junit.framework.TestCase.*;

public class RequestTest {
    private final static String path = "D:\\Graduate Study\\2-1\\分布式\\MyYAR\\YAR-LIB\\src\\test\\java\\RequestAndResponseClassTest\\";
    /*@Test
    public void TestAddParamAndTypes(){
        RPCRequest request = new RPCRequest();
        request.addTypes(String.class, int.class, float.class);
        List<String> types = request.getTypes();
        //System.out.println(types);
        assertEquals(types.get(0), "java.lang.String");
        assertEquals(types.get(1), "int");
        assertEquals(types.get(2), "float");
    }*/

    @Test
    public void TestSerializeAndUnSerialize(){
        RPCRequest request = new RPCRequest("TestClass");
        //request.addTypes(float.class, float.class, int.class);
        float arg1 = 1, arg2 = 2;
        int arg3 = 3;
        request.addParams(arg1,arg2,arg3);
        request.setRequestID("TestID");

        RPCRequest result_request = new RPCRequest();
        try{
            ObjectOutput oo = new ObjectOutputStream(new FileOutputStream(path + "test1"));
            request.writeExternal(oo);

            ObjectInput oi = new ObjectInputStream(new FileInputStream(path + "test1"));
            result_request.readExternal(oi);

            assertEquals("TestID", result_request.getRequestID());
            assertEquals("TestClass", result_request.getFuncName());
            //assertEquals("TestMethod", result_request.getMethodName());
            assertEquals(arg1, result_request.getParams().get(0));
            assertEquals(arg2, result_request.getParams().get(1));
            assertEquals(arg3, result_request.getParams().get(2));
            /*assertEquals("float",result_request.getTypes().get(0));
            assertEquals("float",result_request.getTypes().get(1));
            assertEquals("int",result_request.getTypes().get(2));*/

        } catch (IOException | ClassNotFoundException e) {
            assertEquals(0,1);
            e.printStackTrace();
        }


    }
}
