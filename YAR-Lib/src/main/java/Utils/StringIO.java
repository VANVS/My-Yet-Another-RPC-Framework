package Utils;

import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;

public class StringIO {
    public static void writeString(ObjectOutput out, String str) throws IOException {
        if (str == null || str.length() == 0) {
            out.writeInt(0);
            return;
        }
        byte[] data = str.getBytes();
        out.writeInt(data.length);
        out.write(data);
    }

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
