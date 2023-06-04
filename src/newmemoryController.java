import java.io.*;
import java.util.ArrayDeque;
import java.util.Arrays;

public class newmemoryController {
    private Engine EngineReference;
    static public int[][][][] firstState = new int[40][40][4][5];
    private ArrayDeque<int[][][][]> memoryStack = new ArrayDeque<int[][][][]>();
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
    public int getSize(){return memoryStack.size();}

    public int[][][][] pop(){return memoryStack.pop();}
    public int[][][][] peek(){return memoryStack.peek();}//no need for long names if the people who made Arraydeque did it right the first time
    public void push(int[][][][] x){memoryStack.push(x);}

    public void reset(){

        while(true){try{memoryStack.pop();}catch(Exception e){break;}}
        memoryStack.push(firstState);
    }
    public void allOut00(){
        for (int[][][][] element : memoryStack) {
            int value = element[0][0][0][0];
            System.out.print(value);
        }
        System.out.println(" ");
    }
    private int[][] propertiesStorage = new int[100][100];//[]=id,[][]=property
    public int checkProperty(int id, int property){//TODO ALL OF PROPERTIESSTORAGE NEEDS TO GO INTO MEMORYCONTROLLER
        return propertiesStorage[id][property];
    }
    public void setProperty(int id, int prop, int sign){
        propertiesStorage[id][prop]=sign;
    }
}