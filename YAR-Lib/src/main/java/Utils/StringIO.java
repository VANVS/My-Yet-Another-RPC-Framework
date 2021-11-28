package Utils;

import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;

/**
 * @className: StringIO
 * @packageName: Utils
 * @author 王如轩
 * @description: 序列化反序列化字符串到指定流中
 **/
public class StringIO {
    /**
     * @param out: 输出流
     * @param str: 待序列化字符串
     * @return void
     * @author 王如轩
     * @description 序列化字符串到给定输出流中
     */
    public static void writeString(ObjectOutput out, String str) throws IOException {
        if (str == null || str.length() == 0) {
            out.writeInt(0);
            return;
        }
        byte[] data = str.getBytes();
        out.writeInt(data.length);
        out.write(data);
    }


    /**
     * @param in: 输入流
     * @return String
     * @author 王如轩
     * @description 从输入流反序列化到string中
     */
    public static String readString(ObjectInput in) throws IOException {
        int len = in.readInt();
        if (len == 0) {
            return null;
        }
        byte[] buffer = new byte[len];
        in.read(buffer);
        return new String(buffer);
    }
}
