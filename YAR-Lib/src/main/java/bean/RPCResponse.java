package bean;

import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;

import static Utils.StringIO.writeString;
import static Utils.StringIO.readString;

public class RPCResponse implements Externalizable {
    private Status status;
    private String requestID;
    private Object result;

    public RPCResponse() {
    }

    public RPCResponse(Status status,String requestID, Object result) {
        this.status = status;
        this.requestID = requestID;
        this.result = result;
    }
    
    @Override
    public void writeExternal(ObjectOutput out) throws IOException{
        writeString(out, status.toString());
        writeString(out, requestID);
        out.writeObject(result);
    }

    @Override
    public void readExternal(ObjectInput in) throws IOException, ClassNotFoundException{
        status = Status.valueOf(readString(in));
        requestID = readString(in);
        result = in.readObject();
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public String getRequestID() {
        return requestID;
    }

    public void setRequestID(String requestID) {
        requestID = requestID;
    }

    public Object getResult() {
        return result;
    }

    public void setResult(Object result) {
        this.result = result;
    }

    @Override
    public String toString() {
        return "RPCResponse{" +
                "status=" + status +
                ", requestID='" + requestID + '\'' +
                ", result=" + result +
                '}';
    }
}
