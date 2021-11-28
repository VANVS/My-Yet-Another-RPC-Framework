package beans;

import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import static Utils.StringIO.writeString;
import static Utils.StringIO.readString;

/**
 * @className: RPCRequest
 * @packageName: beans
 * @author 王如轩
 * @description: 封装TCP请求，包含请求标识符，函数名，函数参数列表，并实现可序列化Externalizable接口
 **/
public class RPCRequest implements Externalizable {
    private String requestID;       // 请求唯一标识符
    private String FuncName;        // 请求函数名
    private List<Object> params;    // 函数参数列表

    /**
     * 提供三种不同的构造函数
     **/
    public RPCRequest(){
        this.params = new ArrayList<>();
    }

    public RPCRequest(String FuncName) {
        this.FuncName = FuncName;
        this.params = new ArrayList<>();
    }

    public RPCRequest(String FuncName,List<Object> params) {
        this.FuncName = FuncName;
        this.params = params;
    }

    /**
     * 重写序列化和反序列化方法
     **/
    @Override
    public void writeExternal(ObjectOutput out) throws IOException {
        writeString(out, requestID);
        writeString(out, FuncName);
        out.writeInt(params.size());
        for (Object param : params) {
            out.writeObject(param);
        }
    }

    @Override
    public void readExternal(ObjectInput in) throws IOException, ClassNotFoundException{
        requestID = readString(in);
        FuncName = readString(in);
        params = new ArrayList<>();
        int para_num = in.readInt();
        for(int i=0; i < para_num;i++){
            params.add(in.readObject());
        }
    }

    /**
     * @param args: 参数列表
     * @return ：void
     * @author 王如轩
     * @description 添加参数列表到request中
     */
    public void addParams(Object... args) {
        params.addAll(Arrays.asList(args));
    }

    public void addParams(List<Object> args) {
        params.addAll(args);
    }

    /**
     * @getter
     * @setter
     */
    public String getRequestID() {
        return requestID;
    }

    public void setRequestID(String requestID) {
        this.requestID = requestID;
    }


    public String getFuncName() {
        return FuncName;
    }

    public List<Object> getParams() {
        return params;
    }

    @Override
    public String toString() {
        return "RPCRequest{" +
                "requestID='" + requestID + '\'' +
                ", FuncName='" + FuncName + '\'' +
                ", params=" + params +
                '}';
    }
}