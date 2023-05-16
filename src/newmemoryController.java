import java.io.*;
import java.util.ArrayDeque;

public class newmemoryController {
    static private int[][][][] firstState = new int[40][40][2][5];
    static private int pointer = 0;
    private static ArrayDeque<int[][][][]> memoryStack = new ArrayDeque<int[][][][]>();
    public newmemoryController() throws IOException, ClassNotFoundException {
        ObjectInputStream ois = new ObjectInputStream(new FileInputStream(new File("level1.data")));//todo add so multiple levels not that hard
        Object obj = ois.readObject();
        ois.close();
        firstState = (int[][][][])obj;
        //down here is a patch for old memory
        Engine.memoryEater.pushNewState(firstState);
        Engine.levelStoragePush=Engine.memoryEater.pullLatestState().clone();
        //above here
        memoryStack.push(firstState);
    }

    public int getSize(){return memoryStack.size();}

    public int[][][][] pullLatestState(){
        int[][][][] x = new int[0][][][];//this is the shortest way to return an int array at least from what i can guess
        return x;
    }
    public int[][][][] pop(){return memoryStack.pop();}
    public int[][][][] pullNState(int x){
        int[][][][] y = new int[0][][][];
        return y;
    }
    public void pushNewState(int[][][][] x){}
    public void pushAState(int[][][][] x, int i){

    }
    public void removeLastState(){}
    public void resetState(){}

}