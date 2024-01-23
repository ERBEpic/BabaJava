import java.awt.event.KeyEvent;
import java.io.IOException;
import java.io.Serializable;

public class Protocol implements Serializable {
    private static final long serialVersionUID = 1L;
    public static void messageRecievingProtocolClient(Message a) throws IOException {
        // Process the received object based on its ID and payload
        int messageId = a.getMessageId();
        Object data = a.getPayload();
        switch(messageId){
            case -1 ->{//Stop execution
                System.exit(672);
            }
            case 1 -> {//Update memory
                NetworkClient.updateLevel((int[][][][]) data);
            }
            case 2 -> {//Submit ID
                NetworkClient.userId = (int) data;
            }
            case 3 -> {//User Messages (to everyone)
                String umessage = (String) data;
            }
            case 4 ->{//Bad Input (Make sure it does NOT go to everyone)
                boolean binput = (boolean) data;
            }
            case 5 ->{//New KeyEvent
                KeyEvent e = (KeyEvent) data;
                int keyCode = e.getKeyCode();


            }
        }
    }
    public static void messageSendingProtocolClient(int id, Object data,int userId) throws IOException {
        // Process the received object based on its ID and payload
        Message message = null;
        switch(id){
            case -1 ->{// I am stopping execution

            }
            case 1 -> {//I need a new memory state
                message = new Message(1,null,NetworkClient.userId);
            }
            case 2 -> {//Submit ID
                //N/A
            }
            case 3 -> {//User Messages (to everyone ELSE)
                message = new Message(3,data,NetworkClient.userId);
            }
            case 4 ->{//Bad Input (Make sure it does NOT go to everyone)
                //N/A
            }

        }
        if(message!=null) {//This should never not be null
            NetworkClient.outputStream.writeObject(message);
        }else{
            System.out.println("NULL MESSAGE - FIX CODE");
        }

    }
    public static void messageRecievingProtocolServer(Message a) throws IOException {
        // Process the received object based on its ID and payload
        int messageId = a.getMessageId();
        Object data = a.getPayload();
        switch(messageId){
            case -1 -> {//Stop exection (Blank)

            }
            case 1 -> {//Memory (Blank)

            }
            case 2 -> {//ID (Blank)

            }
            case 3 -> {//User Messages (to everyone)

            }
            case 4 ->{//Bad Input (Make sure it does NOT go to everyone)
                boolean binput = (boolean) data;
            }
            case 5 ->{//New KeyEvent
                KeyEvent e = (KeyEvent) data;
                int keyCode = e.getKeyCode();

                switch (keyCode) {
                    case KeyEvent.VK_ESCAPE -> messageSendingProtocolServer(-1,null,a.getUserId());//this is what ExitOnClose calls, so it works great. 1 for user decided to close
                    case KeyEvent.VK_UP, KeyEvent.VK_W -> NetworkServer.BabaEngine.youProperty(1,a.getUserId());
                    case KeyEvent.VK_DOWN, KeyEvent.VK_S -> NetworkServer.BabaEngine.youProperty(3,a.getUserId());
                    case KeyEvent.VK_LEFT, KeyEvent.VK_A -> NetworkServer.BabaEngine.youProperty(2,a.getUserId());
                    case KeyEvent.VK_RIGHT, KeyEvent.VK_D -> NetworkServer.BabaEngine.youProperty(0,a.getUserId());
                    case KeyEvent.VK_SPACE -> NetworkServer.BabaEngine.moveWait();//This works, but it doesnt actually do anything because move doesnt work. No point in commenting it out though because it works fine.
                    case KeyEvent.VK_R -> NetworkServer.BabaEngine.resetLevel();
                    case KeyEvent.VK_Z -> NetworkServer.BabaEngine.moveUndoNew();
                }
            }
        }
    }
    public static void messageSendingProtocolServer(int id, Object data,int userId) throws IOException {
        // Process the received object based on its ID and payload
        Message message = null;
        switch(id){
            case -1 ->{// I am stopping execution
                message = new Message(-1,null,NetworkClient.userId);
                //Code to send stop message to the user.
            }
            case 1 -> {//New memory states
                message = new Message(1,NetworkServer.BabaEngine.newmemoryEater.peek());//No need for any userID. Send to all
            }
            case 2 -> {//Submit Identification Tag
                message = new Message(2,userId,userId);
                NetworkServer.clientsMap.get(userId).writeObject(message);
            }
            case 3 -> {//User Messages (to everyone ELSE)
                message = new Message(3,data);
                for (int i = 1; i < NetworkServer.clientsMap.size(); i++) {//< or <=?
                    if(i!=userId){
                        NetworkServer.clientsMap.get(i).writeObject(message);
                    }
                }
            }
            case 4 ->{//Bad Input (Make sure it does NOT go to everyone)
                //N/A
            }
            case 5 ->{//New KeyEvent
                message = new Message(5,data,NetworkClient.userId);
            }
        }
        if(message!=null) {//This should never not be null
            NetworkClient.outputStream.writeObject(message);
        }else{
            System.out.println("NULL MESSAGE - FIX CODE");
        }

    }
}
