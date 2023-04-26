import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

public class memoryController {//Memorycontroller stores 4 things. 1. ID. Baba=1. Keke = 2. ETc
    //TWO. The rotation. 0=Unrotatable, 1=up,2=right
    //THREE. Walking Animation. 4 = no animation. 0 = step 0. 1 = step 1.
    //FOUR. X position
    //FIVE. Y Position
    public static void main(String[] args) {
        ArrayList<ArrayList<Integer>[][]> list = new ArrayList<>();

        // Create a 2D array of ArrayLists
        /*ArrayList<BabaObjects>[][] array1 = new ArrayList[2][2];
        array1[0][0] = new ArrayList<BabaObjects>();
        array1[0][0].add(1);
        array1[0][1] = new ArrayList<Integer>();
        array1[0][1].add(2);
        array1[1][0] = new ArrayList<Integer>();
        array1[1][0].add(3);
        array1[1][1] = new ArrayList<Integer>();
        array1[1][1].add(4);
        list.add(array1);*/
    }
    static private ArrayList<ArrayList<BabaObjects>[][]> memoryEater = new ArrayList<>();

     static private int pointer = 0;//Self evident what it does. Adds and removes always come paired with this.
    public memoryController(ArrayList<BabaObjects>[][] x){//Start off the stack. NEEDS TO BE ONE. Will get angy if there isnt.
        memoryEater.add(x);pointer++;
    }
    public memoryController(){

    }

    public ArrayList<BabaObjects>[][] pullLatestState(){
        return memoryEater.get(pointer-1);
    }
    public ArrayList<BabaObjects>[][] pullNState(int x){
        return memoryEater.get(x);
    }
    public void pushNewState(ArrayList<BabaObjects>[][] x){
        memoryEater.add(x);pointer++;
    }
    public void removeLastState(){
        System.out.println(pointer);
        if (pointer>0){pointer--;
        memoryEater.remove(pointer);}else {};
    }

}
