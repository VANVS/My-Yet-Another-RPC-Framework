package bean;

import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import static Utils.StringIO.writeString;
import static Utils.StringIO.readString;

public class RPCRequest implements Externalizable {
    private String requestID;
    private String FuncName;
    //private String methodName;
    private List<Object> params;
    //private final List<String> types;


    public RPCRequest(){
        this.params = new ArrayList<>();
        //this.types = new ArrayList<>();
    }

    public RPCRequest(String FuncName) {
        this.FuncName = FuncName;
        //this.methodName = methodName;
        this.params = new ArrayList<>();
        //this.types = new ArrayList<>();
    }

    public RPCRequest(String FuncName,List<Object> params) {
        this.FuncName = FuncName;
        //this.methodName = methodName;
        this.params = params;
        //this.types = new ArrayList<>();
    }

    @Override
    public void writeExternal(ObjectOutput out) throws IOException {
        writeString(out, requestID);
        writeString(out, FuncName);
        //writeString(out, methodName);
        out.writeInt(params.size());
        for(int i=0; i<params.size();i++){
            //writeString(out, types.get(i));
            out.writeObject(params.get(i));
        }
    }

    @Override
    public void readExternal(ObjectInput in) throws IOException, ClassNotFoundException{
        requestID = readString(in);
        FuncName = readString(in);
        //methodName = readString(in);
        params = new ArrayList<>();
        int para_num = in.readInt();
        for(int i=0; i < para_num;i++){
            //types.add(readString(in));
            params.add(in.readObject());
        }
    }

    public void addParams(Object... args) {
        params.addAll(Arrays.asList(args));
    }

    public void addParams(List<Object> args) {
        params.addAll(args);
    }

    /*public void addTypes(Class<?>... types) {
        for (Class<?> type : types) {
            this.types.add(type.getName());
        }
    }*/

    public String getRequestID() {
        return requestID;
    }

    public void setRequestID(String requestID) {
        this.requestID = requestID;
    }

    /*public void setFuncName(String funcName) {
        FuncName = funcName;
    }*/

    public String getFuncName() {
        return FuncName;
    }

    public List<Object> getParams() {
        return params;
    }

    /*public List<String> getTypes() {
        return types;
    }*/

    @Override
    public String toString() {
        return "RPCRequest{" +
                "requestID='" + requestID + '\'' +
                ", FuncName='" + FuncName + '\'' +
                //", methodName='" + methodName + '\'' +
                ", params=" + params +
                //", types=" + types +
                '}';
    }
}