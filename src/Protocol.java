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
                System.out.println("Update");
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
                message = new Message(1);
            }
            case 2 -> {//Submit ID
                //N/A
            }
            case 3 -> {//User Messages (to everyone ELSE)
                message = new Message(3,(Object) data,NetworkClient.userId);
            }
            case 4 ->{//Bad Input (Make sure it does NOT go to everyone)
                //N/A
            }
            case 5 ->{//Key Input
                KeyEvent e = (KeyEvent) data;
                int keyCode = e.getKeyCode();
                System.out.println("Bi");
                switch(keyCode){
                case KeyEvent.VK_ESCAPE -> message = new Message(5,-1,userId);//this is what ExitOnClose calls, so it works great. 1 for user decided to close
                case KeyEvent.VK_UP, KeyEvent.VK_W -> message = new Message(5,0,userId);
                case KeyEvent.VK_DOWN, KeyEvent.VK_S -> message = new Message(5,1,userId);
                case KeyEvent.VK_LEFT, KeyEvent.VK_A -> message = new Message(5,2,userId);
                case KeyEvent.VK_RIGHT, KeyEvent.VK_D -> message = new Message(5,3,userId);
                case KeyEvent.VK_SPACE -> message = new Message(5,4,userId);
                case KeyEvent.VK_R -> message = new Message(5,5,userId);
                case KeyEvent.VK_Z -> message = new Message(5,6,userId);
                }
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
        Message message = null;
        switch(messageId){
            case -1 -> {//Stop exection (Blank)
                System.exit(-1);
            }
            case 1 -> {//Memory (Blank)

            }
            case 2 -> {//ID (Blank)

            }
            case 3 -> {//User Messages (to everyone)
                for (int i = 1; i < NetworkServer.clientsMap.size(); i++) {//< or <=
                    if(i!=a.getUserId()) {
                        message = new Message(3,data);
                        NetworkServer.clientsMap.get(i).writeObject(message);
                    }
                }
            }
            case 4 ->{//Bad Input (Blank)

            }
            case 5 ->{//New KeyEvent

                switch ((int) data) {
                    case -1 -> messageSendingProtocolServer(-1,null,a.getUserId());//this is what ExitOnClose calls, so it works great. 1 for user decided to close
                    case 0 -> NetworkServer.BabaEngine.youProperty(1);
                    case 1 -> NetworkServer.BabaEngine.youProperty(3);
                    case 2 -> NetworkServer.BabaEngine.youProperty(2);
                    case 3 -> NetworkServer.BabaEngine.youProperty(0);
                    case 4 -> NetworkServer.BabaEngine.moveWait();//This works, but it doesnt actually do anything because move doesnt work. No point in commenting it out though because it works fine.
                    case 5 -> NetworkServer.BabaEngine.resetLevel();
                    case 6 -> NetworkServer.BabaEngine.moveUndoNew();
                }
            }
        }
    }
    public static void messageSendingProtocolServer(int id, Object data,int userId) throws IOException {
        // Process the received object based on its ID and payload
        Message message;
        switch(id){
            case -1 ->{// I am stopping execution
                message = new Message(-1,NetworkClient.userId);
                NetworkServer.clientsMap.get(userId).writeObject(message);
                //Code to send stop message to the user.
            }
            case 1 -> {//New memory states
                message = new Message(1,NetworkServer.BabaEngine.newmemoryEater.peek());//No need for any userID. Send to all
                for (int i = 1; i <= NetworkServer.clientsMap.size(); i++) {
                    System.out.println(i);
                    NetworkServer.clientsMap.get(i).writeObject(message);
                }
            }
            case 2 -> {//Submit Identification Tag
                message = new Message(2,userId,userId);
                NetworkServer.clientsMap.get(userId).writeObject(message);
            }
            case 3 -> {//User Messages (to everyone ELSE)
                message = new Message(3,data);
                for (int i = 1; i <= NetworkServer.clientsMap.size(); i++) {//< or <=?
                    if(i!=userId){
                        NetworkServer.clientsMap.get(i).writeObject(message);
                    }
                }
            }
            case 4 ->{//Bad Input (Make sure it does NOT go to everyone)
                message = new Message (4);
                NetworkServer.clientsMap.get(userId).writeObject(message);
            }
            case 5 ->{//New KeyEvent N/A
            }
        }


    }
}
