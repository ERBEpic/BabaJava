import java.awt.event.KeyEvent;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.Map;
public class Protocol implements Serializable {
    private static final long serialVersionUID = 102L;
    public static void messageRecievingProtocolClient(Message a) throws IOException {
        // Process the received object based on its ID and payload
        int messageId = a.getMessageId();
        Object data = a.getPayload();
        System.out.println("receive");
        switch(messageId){
            case -1 ->{//Stop execution
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
                System.exit(-1);
            }
            case 1 -> {//Update memory
                NetworkClient.updateLevel((int[][][][]) data);
                System.out.println("Update");
            }
            case 2 -> {//Submit ID
                NetworkClient.userId = a.getUserId();
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
            case -1 ->{// I am stopping execution (in key)

            }
            case 1 -> {//I need a new memory state
                message = new Message(1,null,userId);
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
            case 5 ->{//Key Input
                KeyEvent e = (KeyEvent) data;
                int keyCode = e.getKeyCode();
                System.out.println("Send");
                switch(keyCode){
                    case KeyEvent.VK_ESCAPE -> {
                        message = new Message(5,-1,userId);//this is what ExitOnClose calls, so it works great. 1 for user decided to close
                        System.exit(-1);
                    }
                    case KeyEvent.VK_UP, KeyEvent.VK_W -> message = new Message(5,0,userId);
                case KeyEvent.VK_DOWN, KeyEvent.VK_S -> message = new Message(5,1,userId);
                case KeyEvent.VK_LEFT, KeyEvent.VK_A -> message = new Message(5,2,userId);
                case KeyEvent.VK_RIGHT, KeyEvent.VK_D -> message = new Message(5,3,userId);
                case KeyEvent.VK_SPACE -> message = new Message(5,4,userId);
                case KeyEvent.VK_R -> {
                    NetworkClient.outputStream.writeObject(new Message(5,5,userId));
                    message = new Message(5,4,userId);}

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
            }
            case 1 -> {//Memory (Blank)
                messageSendingProtocolServer(1,null,0);
            }
            case 2 -> {//ID (Blank)

            }
            case 3 -> {//User Messages (to everyone)
                for (Map.Entry<Integer, ObjectOutputStream> entry : NetworkServer.clientsMap.entrySet()) {
                    if(entry.getKey()!=a.getUserId()) {
                        message = new Message(3,data,0);
                        NetworkServer.clientsMap.get(entry.getKey()).writeObject(message);
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

                message = new Message(-1,userId);
                NetworkServer.clientsMap.get(userId).writeObject(message);
                try {
                    Thread.sleep(10);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
                NetworkServer.clientRunMap.put(userId, false);
                NetworkServer.clientsMap.get(userId).close();
                NetworkServer.clientsMap.remove(userId);


                //Code to send stop message to the user.
            }
            case 1 -> {//New memory states
                message = new Message(1,NetworkServer.BabaEngine.newmemoryEater.peek(),0);//No need for any userID. Send to all
                for (Map.Entry<Integer, ObjectOutputStream> entry : NetworkServer.clientsMap.entrySet()) {
                    NetworkServer.clientsMap.get(entry.getKey()).writeObject(message);
                }
            }
            case 2 -> {//Submit Identification Tag
                message = new Message(2,null,userId);
                NetworkServer.clientsMap.get(userId).writeObject(message);
            }
            case 3 -> {//User Messages (to everyone ELSE)
                message = new Message(3,data,0);
                for (Map.Entry<Integer, ObjectOutputStream> entry : NetworkServer.clientsMap.entrySet()) {
                        NetworkServer.clientsMap.get(entry.getKey()).writeObject(message);
                }
            }
            case 4 ->{//Bad Input (Make sure it does NOT go to everyone)
                message = new Message (4,null,userId);
                NetworkServer.clientsMap.get(userId).writeObject(message);
            }
            case 5 ->{//New KeyEvent N/A
            }
        }


    }
}
