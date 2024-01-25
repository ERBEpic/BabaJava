import java.io.IOException;

public class ClientMain {
    static BabaFrame babakey;
    public static void main(String[] args) throws IOException {
        Thread frameThread = new Thread(() -> {
            try {
                babakey = new BabaFrame(20,20);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
        frameThread.start();

        NetworkClient Client = new NetworkClient();

    }
}