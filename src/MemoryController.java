import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.ArrayDeque;
import java.util.Arrays;
/*
What this class does is it is basically an interface for the ArrayDeque memoryStack, allowing access for Engine to talk to it.
It also stores the class I made for deepcopying, but thats more just a DRY(don't repeat yourself) thing.
*/

//Why is java so annoying when it comes to references and copying? Why cant I just copy one object, not copy a reference to an object?
public class MemoryController {
    private Engine EngineReference;
    private int[][][][] firstState;
    private ArrayDeque<int[][][][]> memoryStack = new ArrayDeque<>();
    private ArrayDeque<int[][]> propertiesStack = new ArrayDeque<>();

    public MemoryController(Engine engine) throws IOException, ClassNotFoundException {
        EngineReference = engine;
        ObjectInputStream ois = new ObjectInputStream(new FileInputStream("levels/level0.data"));
        Object obj = ois.readObject();
        ois.close();
        firstState = (int[][][][])obj;
        //down here is a patch for old memory
        //Engine.memoryEater.pushNewState(firstState);
        //Engine.levelStoragePush=Engine.memoryEater.pullLatestState().clone();
        //above here
        memoryStack.push(deepCopy(firstState));
        EngineReference.levelStoragePush=deepCopy(memoryStack.peek());

        //[]=id,[][]=property
        int[][] propertiesStorageBlank = new int[100][100];
        propertiesStack.push(propertiesStorageBlank);
    }
    public int getSize() {
        return memoryStack.size();
    }
    public int[][][][] pop() {
        return memoryStack.pop();
    }
    public int[][][][] peek() {
        return memoryStack.peek();
    }
    public void push(int[][][][] x) {
        memoryStack.push(x);
    }


    public void reset(){
        memoryStack.clear();
    }
    public int[][][][] getFirstState(){
        return deepCopy(firstState);
    }

    public void newLevel(int id){
        propertiesStack.clear();
        EngineReference.clearProperties();
        memoryStack.clear();
        ObjectInputStream ois;
        try {
            ois = new ObjectInputStream(new FileInputStream("levels/level"+id+".data"));
        Object obj = ois.readObject();
        ois.close();
        firstState = (int[][][][])obj;
        }catch (IOException | ClassNotFoundException e){//Why is it | for exceptions but || for booleans?????
            System.out.println("Please obtain the required level files before attempting to run this code.");
            throw new RuntimeException(e);}//Also this is MEANT to crash if it doesnt read a file. If you dont have the first level, then the game wont work. You cant just move on like nothing happened, as its a required file, and you cant ask for a second input, as it will be identical to the previous.

        memoryStack.push(deepCopy(firstState));
        memoryStack.push(deepCopy(firstState));
    }

    public int[][] popreties(){
        return propertiesStack.pop();
    }
    public void pushreties(int[][] x){
        propertiesStack.push(x);
    }
    public int[][] peekreties(){
        return propertiesStack.peek();
    }
    public static int[][][][] deepCopy(int[][][][] array) {//This is why I dont like java.
        //Also, static because deepCopy is the same for all instances of memoryController.
        int[][][][] copy = new int[array.length][][][];
        for (int i = 0; i < array.length; i++) {
            copy[i] = new int[array[i].length][][];
            for (int j = 0; j < array[i].length; j++) {
                copy[i][j] = new int[array[i][j].length][];
                for (int k = 0; k < array[i][j].length; k++) {
                    copy[i][j][k] = Arrays.copyOf(array[i][j][k], array[i][j][k].length);
                }
            }
        }
        return copy;
    }
    public static int[][] deepCopy(int[][] array) {
        int[][] copy = new int[array.length][];
        for (int i = 0; i < array.length; i++) {
            copy[i] = Arrays.copyOf(array[i], array[i].length);
        }
        return copy;
    }

}