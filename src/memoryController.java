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
    private int[][][][][] memoryEater = new int[40][40][3][][];
    //x,y,a,z,i
    //x,y simple
    //z is the point at which the thing is in (if [1][40][40] there is Baba[],Keke[], baba is at z=0 keke is at z=1
    //a is the things inside the array
    //i is for undos and stuff.
    //Why is it ordered like this? Because i want it to be 40,40,3 guaranteed, and z and i can change

    //Pointer is THE NEXT PLACE TO BE WRITTEN IN.
    private int pointer = 1;//Self evident what it does. Adds and removes always come paired with this.
    public memoryController(int[][][][] x){//Start off the stack. NEEDS TO BE ONE. Will get angy if there isnt.
        int [][][][][] temp = new int[40][40][3][pointer][];
        temp
        pointer++;
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
        if (pointer>1){pointer--;
            memoryEater.remove(pointer);}else {};
    }
    public void resetState(){


    }

}
