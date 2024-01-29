import java.awt.event.KeyEvent;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.net.Socket;

public class NetworkClient implements Serializable {
    private static final long serialVersionUID = 102L; // Same value on both client and server

    private static final String SERVER_IP = "localhost";
    private static final int PORT = 12345;
    public static int userId;
    public static int[][][][] currentState;
    public static Socket socket;
    protected static ObjectOutputStream outputStream;
    private static ObjectInputStream inputStream;
    public NetworkClient() throws IOException {
        //id = server.requestid();
        /*ObjectInputStream ois = new ObjectInputStream(new FileInputStream("levels/level0.data"));
        Object obj = ois.readObject();
        ois.close();*/

        try {
            Socket thissocket = new Socket(SERVER_IP, PORT);
            this.socket=thissocket;

            // ObjectOutputStream for sending objects to the server
            ObjectOutputStream clientOutput = new ObjectOutputStream(thissocket.getOutputStream());
            this.outputStream = clientOutput;

            // ObjectInputStream for receiving objects from the server
            inputStream = new ObjectInputStream(thissocket.getInputStream());
/*
            // Example: Send an object to the server
            Message messageObject = new Message(1, 4);
            clientOutput.writeObject(messageObject);
*/
            clientOutput.writeObject(new Message(5,4,userId));
            // Example: Receive objects from the server


            //Make a new thread here if the world ends
            while (true) {
                // Deserialize the object received from the server
                Object a = null;
                try{
                    a = inputStream.readObject();
                }catch (Exception e){
                    thissocket = new Socket(SERVER_IP, PORT);
                    this.socket=thissocket;

                    // ObjectOutputStream for sending objects to the server
                    clientOutput = new ObjectOutputStream(thissocket.getOutputStream());
                    this.outputStream = clientOutput;

                    // ObjectInputStream for receiving objects from the server
                    inputStream = new ObjectInputStream(thissocket.getInputStream());}
                if(a!=null){
                System.out.println(a);
                Message receivedMessage = (Message) a;

                Protocol.messageRecievingProtocolClient(receivedMessage);
                System.out.println(receivedMessage.getMessageId());}
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void keyToServer(KeyEvent e) throws IOException {
        Protocol.messageSendingProtocolClient(5,e,userId);
    }
    public static void updateLevel(int[][][][] mem){
        currentState = mem;
    }
    public static int[][][][] peek(){
        return currentState;
    }

}