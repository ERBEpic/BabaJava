import java.io.*;
import java.util.Random;

public class JsonFake {
    public static JsonFake JsonFake;
    static final String FILEPATH = "C:\\Users\\braun\\IdeaProjects\\BabaJava\\PageFilee.txt";
    static {
        try {
            JsonFake = new JsonFake();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public JsonFake() throws IOException {
        FileReader filer = new FileReader("PageFile.txt");
        BufferedReader br= new BufferedReader(filer);
        FileWriter filew = new FileWriter("PageFile.txt");
        BufferedWriter bw = new BufferedWriter(filew);
        RandomAccessFile RandomWriter = new RandomAccessFile(FILEPATH, "rw");
        RandomWriter.write(4294967294);
        RandomWriter.close();
        /*bw.write("foobar");
        bw.flush();
        System.out.println(br.read());
        bw.write("2");
        System.out.println(br.read());*/

    }

    public static void main(String[] args) {

    }
}


/*while (true){
            int x = Integer.parseInt(br.readLine());
            System.out.println(x);
            String[] strNums;
            strNums = br.readLine().split("");
            System.out.println(strNums[2]);*/