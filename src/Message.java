import java.io.Serializable;

public class Message implements Serializable {
    private static final long serialVersionUID = 102L; // Same value on both client and server
    int id;
    Object data;
    int userId;
    public Message(int messageId, Object payload, int Userid) {
        id = messageId;
        data = payload;
        userId = Userid;
    }
    public Message(int messageId, int Userid){
        id = messageId;
        userId = Userid;
        data = null;
    }
    public int getMessageId() {
        return id;
    }

    public Object getPayload() {
        return data;
    }
    public int getUserId(){return userId;}
}