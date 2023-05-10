import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class newmemoryController {
    static private int[][][][][] memoryStorer = new int[21][40][40][2][5];
    static private int[][][][] firstState = new int[40][40][2][5];
    static private int pointer = 0;
    /*static Path path = Paths.get("save.txt");
    static BufferedReader br;
    static {
        try {
            br = Files.newBufferedReader(path);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }*/
    public newmemoryController() throws IOException {
        ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(new File("serializedArray.data")));
        //oos.writeObject(data);
        oos.flush();
        oos.close();
    }
}
