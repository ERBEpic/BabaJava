import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

public class memoryController {
    public static void main(String[] args) {
        ArrayList<ArrayList<Integer>[][]> list = new ArrayList<>();

        // Create a 2D array of ArrayLists
        ArrayList<Integer>[][] array1 = new ArrayList[2][2];
        array1[0][0] = new ArrayList<Integer>();
        array1[0][0].add(1);
        array1[0][1] = new ArrayList<Integer>();
        array1[0][1].add(2);
        array1[1][0] = new ArrayList<Integer>();
        array1[1][0].add(3);
        array1[1][1] = new ArrayList<Integer>();
        array1[1][1].add(4);
        list.add(array1);
    }
    @SuppressWarnings("rawtypes")
    static ArrayList<ArrayList<Integer>[][]> memoryEater = new ArrayList<>();
     static int pointer = 0;//Self evident what it does. Adds and removes always come paired with this.
    public memoryController(ArrayList<Integer>[][] x){//Start off the stack. NEEDS TO BE ONE. Will get angy if there isnt.
        memoryEater.add(x);pointer++;
    }

    public ArrayList<Integer>[][] pullEndState(){
        return memoryEater.get(pointer);
    }
    public ArrayList<Integer>[][] pullNState(int x){
        return memoryEater.get(x);
    }
    public void pushNewState(ArrayList<Integer>[][] x){
        memoryEater.add(x);pointer++;
    }
    public void removeLastState(){
        if (pointer>0){
        memoryEater.remove(pointer);pointer--;}else throw new ArrayIndexOutOfBoundsException("Stack Underflow");
    }

}
