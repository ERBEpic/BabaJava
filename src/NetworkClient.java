import java.awt.event.KeyEvent;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;

public class NetworkClient {
    private int id;
    public BabaFrame babakey;

    public int[][][][] currentState;
    public NetworkClient() throws IOException {
        babakey = new BabaFrame(20,20,this);
        //id = server.requestid();
        id = 0;
        /*ObjectInputStream ois = new ObjectInputStream(new FileInputStream("levels/level0.data"));
        Object obj = ois.readObject();
        ois.close();*/

        while(true){

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
