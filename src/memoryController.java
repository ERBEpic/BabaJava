public class memoryController {//Memorycontroller stores 4 things. 1. ID. Baba=1. Keke = 2. ETc
    //TWO. The rotation. 0=Unrotatable, 1=up,2=right
    //THREE. Walking Animation. 4 = no animation. 0 = step 0. 1 = step 1.
    //FOUR. X position
    //FIVE. Y Position
    /*public static void main(String[] args) {
       // ArrayList<ArrayList<Integer>[][]> list = new ArrayList<>();

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
        list.add(array1);
    }*/
    static private int[][][][][] memoryStorer = new int[21][40][40][2][5];
    //i,x,y,z,a
    //x,y simple
    //z is the point at which the thing is in (if [1][40][40] there is Baba[],Keke[], baba is at z=0 keke is at z=1
    //a is the things inside the array
    //i is for undos and stuff.
    //Why is it ordered like this? Because i want it to be 40,40,3 guaranteed, and z and i can change
    static private int[][][][] firstState = new int[40][40][2][5];
    static private int pointer = 0;//Self evident what it does. Adds and removes always come paired with this.
    public memoryController(int[][][][] x){//Start off the stack. NEEDS TO BE ONE. Will get angy if there isnt.
    }
    public memoryController(){
    }
    public static void expandTheEater(){
        int [][][][][] temp = new int[pointer+6][][][][];
        for (int i = 0; i < pointer; i++) {
            temp[i]= memoryStorer[i];
        }
        //pointer++;
        memoryStorer = temp;
    }
    public int getPointer(){return pointer;}
    public int[][][][] pullLatestState(){
        System.out.println(pointer+" "+ memoryStorer.length);
        return memoryStorer[pointer-1];
    }
    public void allOut00(){
        for (int i = 1; i <= pointer; i++) {
            System.out.print(memoryStorer[i][0][0][0][0]);
        }
        System.out.println(" ");
    }
    public int[][][][] pullNState(int x){
        return memoryStorer[x-1];
    }
    public void pushNewState(int[][][][] x){
        expandTheEater();
        memoryStorer[pointer]=x;
        pointer++;
    }
    public void pushFirstState(int[][][][] x){
        firstState=x;
    }
    public void pushAState(int[][][][] x, int i){

    }
    public void removeLastState(){
        System.out.println(pointer);
        if (pointer>0){pointer--;
            System.out.println(pointer+"pointer");}
    }
    public void resetState(){
        int[][][][] x = new int[40][40][2][5];
        x=firstState;
        memoryStorer=new int[21][40][40][2][5];
        //Engine.memoryEater.pushNewState(x);
        pointer=1;
        Engine.levelStoragePush=firstState;
        //Engine.memoryEater.pushNewState(firstState);
    }
    public void addPointer(){pointer++;}
    public void removePointer(){pointer--;}
}
