import java.io.Serializable;

public class Message implements Serializable {
    int id;
    Object data;
    int userId;
    public Message(int messageId){
        id = messageId;
    }
    public Message(int messageId, Object payload) {
        id = messageId;
        data = payload;
    }
    public Message(int messageId, Object payload, int Userid) {
        id = messageId;
        data = payload;
    }
    public Message(int messageId, int Userid){
        id = messageId;
        userId = Userid;
    }
    public int getMessageId() {
        return id;
    }

    public Object getPayload() {
        return data;
    }
    public int getUserId(){return userId;}
}