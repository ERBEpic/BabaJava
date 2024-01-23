public class Message {
    int id;
    Object data;
    int userId;
    public Message(int messageId, Object payload) {
        id = messageId;
        data = payload;
    }

    public Message(int messageId, Object payload, int Userid) {
        id = messageId;
        data = payload;
    }

    public int getMessageId() {
        return id;
    }

    public Object getPayload() {
        return data;
    }
    public int getUserId(){return userId;}
}
