import java.awt.event.KeyEvent;
import java.io.IOException;
import java.io.Serializable;

public class Protocol implements Serializable {
    private static final long serialVersionUID = 1L;
    public static void messageRecievingProtocol(Message a){
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

                switch (keyCode) {
                    case KeyEvent.VK_ESCAPE -> System.exit(1);//this is what ExitOnClose calls, so it works great. 1 for user decided to close
                    case KeyEvent.VK_UP, KeyEvent.VK_W -> EngineReference.youProperty(1);
                    case KeyEvent.VK_DOWN, KeyEvent.VK_S -> EngineReference.youProperty(3);
                    case KeyEvent.VK_LEFT, KeyEvent.VK_A -> EngineReference.youProperty(2);
                    case KeyEvent.VK_RIGHT, KeyEvent.VK_D -> EngineReference.youProperty(0);
                    case KeyEvent.VK_SPACE -> EngineReference.moveWait();//This works, but it doesnt actually do anything because move doesnt work. No point in commenting it out though because it works fine.
                    case KeyEvent.VK_R -> EngineReference.resetLevel();
                    case KeyEvent.VK_Z -> EngineReference.moveUndoNew();
                }
            }
        }
    }
    public static void messageSendingProtocol(int id, Object data) throws IOException {
        // Process the received object based on its ID and payload
        Message message = null;
        switch(id){
            case -1 ->{// I am stopping execution
                message = new Message(-1,null,NetworkClient.userId);
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
