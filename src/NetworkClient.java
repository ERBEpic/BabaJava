import java.awt.event.KeyEvent;
import java.io.*;
import java.net.Socket;

public class NetworkClient {
    private static final String SERVER_IP = "localhost";
    private static final int PORT = 12345;

    public int userId;
    public BabaFrame babakey;
    public int[][][][] currentState;

    public NetworkClient() throws IOException {
        babakey = new BabaFrame(20,20,this);
        //id = server.requestid();
        userId = 0;
        /*ObjectInputStream ois = new ObjectInputStream(new FileInputStream("levels/level0.data"));
        Object obj = ois.readObject();
        ois.close();*/

        try {
            Socket socket = new Socket(SERVER_IP, PORT);

            // ObjectOutputStream for sending objects to the server
            ObjectOutputStream clientOutput = new ObjectOutputStream(socket.getOutputStream());

            // ObjectInputStream for receiving objects from the server
            ObjectInputStream clientInput = new ObjectInputStream(socket.getInputStream());
/*
            // Example: Send an object to the server
            Message messageObject = new Message(1, 4);
            clientOutput.writeObject(messageObject);
*/

            // Example: Receive objects from the server


            //Make a new thread here if the world ends
            while (true) {
                // Deserialize the object received from the server
                Protocol.Message receivedMessage = (Protocol.Message) clientInput.readObject();

                Protocol.messageRecievingProtocol(receivedMessage, this);
            }
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public void keyToServer(KeyEvent e){
        //server.write(e,id);
    }
    public void updateLevel(int[][][][] mem){
        currentState = mem;
    }
    public int[][][][] peek(){
        return currentState;
    }
}
