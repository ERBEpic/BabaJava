import java.awt.event.KeyEvent;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class NetworkClient {
    private static final String SERVER_IP = "localhost";
    private static final int PORT = 12345;
    public static int userId;
    public static BabaFrame babakey;
    public static int[][][][] currentState;
    public static Socket socket;
    public static ObjectOutputStream outputStream;
    public NetworkClient() throws IOException {
        //id = server.requestid();
        userId = 0;
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
            ObjectInputStream clientInput = new ObjectInputStream(thissocket.getInputStream());
/*
            // Example: Send an object to the server
            Message messageObject = new Message(1, 4);
            clientOutput.writeObject(messageObject);
*/

            // Example: Receive objects from the server


            //Make a new thread here if the world ends
            while (true) {
                // Deserialize the object received from the server
                Message receivedMessage = (Message) clientInput.readObject();

                Protocol.messageRecievingProtocol(receivedMessage);
            }
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public static void keyToServer(KeyEvent e){
        //server.write(e,id);
    }
    public static void updateLevel(int[][][][] mem){
        currentState = mem;
    }
    public static int[][][][] peek(){
        return currentState;
    }
}
