import org.apache.commons.cli.CommandLine;
import org.junit.Test;
import com.alibaba.fastjson.JSONObject;
import static Utils.CommandUtil.getCommandLine;
import static junit.framework.TestCase.assertEquals;
import static junit.framework.TestCase.assertNull;

public class CommandTest {
    @Test
    public void testOptions(){
        String[] args1 = {"-i", "192.168.21.33", "-p", "800"};
        CommandLine cmd1 = getCommandLine(args1);
        assertEquals(cmd1.getOptionValue("ip"),"192.168.21.33");
        assertEquals(cmd1.getOptionValue("port"),"800");

        String[] args2 = {"-p", "800"};
        CommandLine cmd2 = getCommandLine(args2);
        assertEquals(cmd2.getOptionValue("port"),"800");
        assertNull(cmd2.getOptionValue("ip"));
    }
}
