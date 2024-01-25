import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

public class NetworkServer {
    public static Engine BabaEngine;
    private static final int PORT = 12345;
    private static int clientIdCounter = 0;
    protected static Map<Integer, ObjectOutputStream> clientsMap = new TreeMap<>();
    protected static Map<Integer, Boolean> clientRunMap = new HashMap<>();
    public NetworkServer(){
        try {
            Thread engineThread = new Thread(() -> {
                try {
                    BabaEngine = new Engine();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                } catch (ClassNotFoundException e) {
                    throw new RuntimeException(e);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            });
            engineThread.start();

            ServerSocket serverSocket = new ServerSocket(PORT);
            System.out.println("Server waiting for clients...");

            while (true) {
                Socket clientSocket = serverSocket.accept();
                System.out.println("Client connected. Assigning ID...");

                int clientId = clientSocket.getPort();//Will this work? Test later
                System.out.println("Assigned ID to client: " + clientId);

                ObjectOutputStream clientOutput = new ObjectOutputStream(clientSocket.getOutputStream());
                clientsMap.put(clientId, clientOutput);

                // Create a new thread to handle the client
                Boolean Run = new Boolean(true);
                Thread clientHandlerThread = new Thread(() -> handleClient(clientId, clientSocket));
                clientHandlerThread.start();
                clientRunMap.put(clientId, true);
                try {
                    Protocol.messageSendingProtocolServer(1,null,clientId);
                } catch (IOException e) {}
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void handleClient(int clientId, Socket clientSocket) {
        try {
            ObjectInputStream clientInput = new ObjectInputStream(clientSocket.getInputStream());

            Protocol.messageSendingProtocolServer(2, clientId, clientId);

            while (NetworkServer.clientRunMap.get(clientId)) {
                // Deserialize the object received from the client
                Message receivedMessage = (Message) clientInput.readObject();

                // Process the received object based on its ID and payload
                int messageId = receivedMessage.getMessageId();
                Object payload = receivedMessage.getPayload();

                System.out.println("Received Message ID from client " + clientId + ": " + messageId);
                System.out.println("Received Payload: " + payload);

                Protocol.messageRecievingProtocolServer(receivedMessage);
            }
        } catch (SocketException e) {
            // Handle client disconnection
            System.out.println("Client " + clientId + " disconnected.");
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        } finally {
            try {
                // Close resources in a finally block
                clientSocket.close();
                clientRunMap.remove(clientId);
                clientsMap.remove(clientId);
            } catch (IOException e) {
                e.printStackTrace();  // Handle the exception appropriately
            }
        }
    }
}
