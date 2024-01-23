import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;

public class NetworkServer {
    private static final int PORT = 12345;

    private static int clientIdCounter = 1;
    private static Map<Integer, ObjectOutputStream> clientsMap = new HashMap<>();

    public static void main(String[] args) {
        try {
            ServerSocket serverSocket = new ServerSocket(PORT);
            System.out.println("Server waiting for clients...");

            while (true) {
                Socket clientSocket = serverSocket.accept();
                System.out.println("Client connected. Assigning ID...");

                int clientId = clientIdCounter++;
                System.out.println("Assigned ID to client: " + clientId);

                ObjectOutputStream clientOutput = new ObjectOutputStream(clientSocket.getOutputStream());
                clientsMap.put(clientId, clientOutput);

                // Create a new thread to handle the client
                Thread clientHandlerThread = new Thread(() -> handleClient(clientId, clientSocket));
                clientHandlerThread.start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void handleClient(int clientId, Socket clientSocket) {
        try {
            ObjectInputStream clientInput = new ObjectInputStream(clientSocket.getInputStream());

            // Example: Send a welcome message to the client
            Message welcomeMessage = new Message(0, "Hai");
            clientsMap.get(clientId).writeObject(welcomeMessage);

            while (true) {
                // Deserialize the object received from the client
                Message receivedMessage = (Message) clientInput.readObject();

                // Process the received object based on its ID and payload
                int messageId = receivedMessage.getMessageId();
                Object payload = receivedMessage.getPayload();

                System.out.println("Received Message ID from client " + clientId + ": " + messageId);
                System.out.println("Received Payload: " + payload);

                // Example: Send a response to the client
                Message responseObject = new Message(2, 5);
                clientsMap.get(clientId).writeObject(responseObject);
            }
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}
