import functions.*;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static junit.framework.TestCase.*;

public class FuncTest {
    @Test
    public void TestSum(){
        SumFunc sum = new SumFunc();
        List<Object> args1 = new ArrayList<>();
        args1.add(1.0f);
        args1.add(2.5f);
        assertEquals(3.5f, sum.function(args1));

        List<Object> args2 = new ArrayList<>();
        args2.add(1.0);
        args2.add(100000L);
        assertNull(sum.function(args2));

        List<Object> args3 = new ArrayList<>();
        args3.add("1");
        assertNull(sum.function(args3));
    }

    @Test
    public void TestUpperCase(){
        UppercaseFunc uppercase = new UppercaseFunc();
        List<Object> args1 = new ArrayList<>();
        args1.add("aBcdeFg");
        assertEquals("ABCDEFG",uppercase.function(args1));

        List<Object> args2 = new ArrayList<>();
        args2.add("aBcdeFg");
        args2.add("aBcdeFg");
        assertNull(uppercase.function(args2));

        List<Object> args3 = new ArrayList<>();
        args3.add(1);
        args3.add(1.0);
        assertNull(uppercase.function(args3));
    }

    @Test
    public void TestCallFuncThrowMap(){
        String id = "sum";
        List<Object> params = new ArrayList<>();
        params.add(1f);
        params.add(2.3f);
        Object result = OptionAndFuncMap.CallFunc(id, params);
        assertTrue(result instanceof Float);
        assertEquals(result, 3.3f);

        params.clear();
        params.add(1);
        params.add(2.0f);
        result = OptionAndFuncMap.CallFunc(id, params);
        assertNull(result);
    }
}
