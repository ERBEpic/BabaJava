import java.io.*;
import java.util.ArrayDeque;
import java.util.Arrays;

public class newmemoryController {
    private Engine EngineReference;
    static public int[][][][] firstState = new int[40][40][4][5];
    private static ArrayDeque<int[][][][]> memoryStack = new ArrayDeque<int[][][][]>();
    public newmemoryController(Engine engine) throws IOException, ClassNotFoundException {
        EngineReference = engine;
        ObjectInputStream ois = new ObjectInputStream(new FileInputStream("level1.data"));//todo add so multiple levels not that hard
        Object obj = ois.readObject();
        ois.close();
        firstState = (int[][][][])obj;
        //down here is a patch for old memory
        //Engine.memoryEater.pushNewState(firstState);
        //Engine.levelStoragePush=Engine.memoryEater.pullLatestState().clone();
        //above here
        memoryStack.push(firstState);
        EngineReference.levelStoragePush=firstState;
    }

    public static void main(String[] args) {

    }
    public int getSize(){return memoryStack.size();}

    public int[][][][] pullLatestState(){
        int[][][][] x = new int[0][][][];//this is the shortest way to return an int array at least from what i can guess
        return x;
    }
    public int[][][][] pop(){return memoryStack.pop();}
    public int[][][][] peek(){return memoryStack.peek();}//no need for long names if the people who made Arraydeque did it right the first time
    public void push(int[][][][] x){memoryStack.push(x);}

    public void reset(){
        memoryStack.push(firstState);
    }
    public void allOut00(){
        for (int[][][][] element : memoryStack) {
            int value = element[0][0][0][0];
            System.out.print(value);
        }
        System.out.println(" ");
    }
}