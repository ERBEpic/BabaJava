import java.io.Serializable;

public class Protocol implements Serializable {
    private static final long serialVersionUID = 1L;

    public static class Message {
        int id;
        Object data;
        int userId = 0;
        public Message(int messageId, Object payload){
            id = messageId;
            data = payload;
        }
        public Message(int messageId, Object payload, int userId){
            id = messageId;
            data = payload;
        }
        public int getMessageId() {
            return id;
        }

        public Object getPayload() {
            return data;
        }
    }


    public static void messageRecievingProtocol(Message a, NetworkClient b){
        // Process the received object based on its ID and payload
        int messageId = a.getMessageId();
        Object payload = a.getPayload();
        switch(messageId){
            case -1 ->{//Stop execution
                System.exit(672);
            }
            case 1 -> {//Update memory
                b.updateLevel((int[][][][]) payload);
            }
            case 2 -> {//Submit ID
                b.userId = a.userId;
            }
            case 3 -> {//User Messages (to everyone)

            }
            case 4 ->{//Bad Input (Make sure it does NOT go to everyone)

            }
            case 5 ->{//New KeyEvent

            }
        }
    }
    public static void messageSendingProtocol(int id, Object data, int userId){
        // Process the received object based on its ID and payload

        switch(id){
            case -1 ->{//Stop execution

            }
            case 1 -> {//Update memory
                b.updateLevel((int[][][][]) payload);
            }
            case 2 -> {//Submit ID
                b.userId = a.userId;
            }
            case 3 -> {//User Messages (to everyone)

            }
            case 4 ->{//Bad Input (Make sure it does NOT go to everyone)

            }
            case 5 ->{//New KeyEvent

            }
        }
    }
}
