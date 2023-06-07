import java.io.*;
import java.util.ArrayDeque;
import java.util.Arrays;
/*
What this class does is it is basically an interface for the ArrayDeque memoryStack, allowing access for Engine to talk to it.
It also stores the class I made for deepcopying, but thats more just a DRY(don't repeat yourself) thing.
*/

//Why is java so annoying when it comes to references and copying? Why cant I just copy one object, not copy a reference to an object?
public class newmemoryController {
    private Engine EngineReference;
    private int[][][][] firstState = new int[40][40][4][5];
    private ArrayDeque<int[][][][]> memoryStack = new ArrayDeque<int[][][][]>();
    private ArrayDeque<int[][]> propertiesStack = new ArrayDeque<int[][]>();

    public newmemoryController(Engine engine) throws IOException, ClassNotFoundException {
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
        propertiesStack.push(propertiesStorage);
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
        System.out.println(id);
        propertiesStack.clear();
        EngineReference.clearProperties();
        memoryStack.clear();
        ObjectInputStream ois = null;
        try {
            ois = new ObjectInputStream(new FileInputStream("levels/level"+id+".data"));
        Object obj = ois.readObject();
        ois.close();
        firstState = (int[][][][])obj;
        }catch (IOException e){throw new RuntimeException(e);}catch (ClassNotFoundException e){throw new RuntimeException(e);}
        for (int i = 0; i < firstState.length; i++) {//There really is no better place to do this
            for (int j = 0; j < firstState[i].length; j++) {
                for (int k = 0; k < firstState[i][j].length; k++) {
                    if(firstState[i][j][k][0]==8){//Skulls face DOWN by default
                        firstState[i][j][k][1]=3;
                    }
                }
            }
        }
        memoryStack.push(deepCopy(firstState));
        memoryStack.push(deepCopy(firstState));
    }
    public void allOut00(){
        /*for (int[][][][] element : memoryStack) {
            int value = element[0][0][0][0];
            System.out.print(value);
        }
        System.out.println(" ");

         */
    }
    private int[][] propertiesStorage = new int[100][100];//[]=id,[][]=property
    /*
    public int checkProperty(int id, int property){
        return propertiesStack.peek()[id][property];
    }
    public void setProperty(int id, int prop, int sign){
        propertiesStack.peek()[id][prop]=sign;//This feels like it should not be allowed
    }

     */
    public int[][] popreties(){
        return propertiesStack.pop();
    }
    public void pushreties(int[][] x){
        propertiesStack.push(x);
    }
    public int[][] peekreties(){
        return propertiesStack.peek();
    }
    public int propSize(){return propertiesStack.size();}
    public static int[][][][] deepCopy(int[][][][] array) {//This is why I dont like java.
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