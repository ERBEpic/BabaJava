import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

//Wait.. Does a 4D array with 5 1D elements take up much more space than a 1D array with 5 1D elements?
//(EX.) Baba - keke- wall are stored in that order next to eachother. In the 4D array beacuse idk there is one will they take up that much more space?
//An arraylist, of 2x2 arrays, of arraylists. Storing Baba objects? or integers?
//Advantages of storing Baba Objects - I have no idea.
//Advantages of storing ints. - Less memory. Easier to code (hopefully)
//Lets do ints.
public class Engine {
    //public static memoryController memoryEater = new memoryController();
    public newmemoryController newmemoryEater;
    public BabaFrameSimple babakey;

    public Engine() throws IOException, ClassNotFoundException, InterruptedException {
        Thread.sleep(100);
        newmemoryEater = new newmemoryController(this);//every engine NEEDS a memorycontroller
        babakey = new BabaFrameSimple(20,20);
        babakey.setEngine(this);//I kinda guessed you could just pass along (this) but Im glad that it works
        //^^ also not every frame needs an engine so its changeable (this isnt just inconsistent coding)

    }

    //it should basically never crash but its easier to solve the try catch problems here



    public int[][][][] levelStoragePush = new int[40][40][4][5];
    //public static BabaObjects properties = new BabaObjects();

    private static int xTiles = 20;
    private static int yTiles = 20;

    private boolean isYou = true;
    private int Level;


    public static int getxTiles(){
        return xTiles;
    }
    public static int getyTiles(){
        return yTiles;
    }
    public static void setxTiles(int x){
        xTiles = x;
    }
    public static void setyTiles(int y){
        yTiles = y;
    }
    public void setLevel(int x){
        Level = x;
    }
    public int getLevel(){
        return Level;
    }

    public void moveWait(){
        playGame();
    }

    public void moveUndoNew(){//not work :(
        if(this.newmemoryEater.getSize()>1){
           levelStoragePush=this.newmemoryEater.pop();
        }
    }


    public static void main(String[] args) {

    }

    public void resetLevel(){//This is weirdly not working? Like nothing happens???
        newmemoryEater.reset();
        int[][][][] x  = newmemoryEater.peek();
        newmemoryEater.allOut00();
        levelStoragePush= x;


    }//fixme, wasnt even wrking before new memory. Maybe it was the root of the problem?







    private int getOpenIndex(int i, int j,int[][][][] maybe) {
        for (int k = 0; k < maybe[i][j].length; k++) {
            if (levelStoragePush[i][j][k][0] == 0) {
                return k;
            }
        }
        return -1; // No open index found
    }
    public static int[][][][] expandZTile(int x, int y, int[][][][] maybe){//I stole this from levelCreator
        int currentDepth = maybe[x][y].length;
        int[][] newArray = new int[currentDepth + 1][5];
        for (int z = 0; z < currentDepth; z++) {
            for (int i = 0; i < 5; i++) {
                newArray[z][i] = maybe[x][y][z][i];
            }
        }
        for (int i = 0; i < 5; i++) {
            newArray[currentDepth][i] = 0;
        }
        maybe[x][y] = newArray;//above this just expands
        return maybe;
    }

    private static HashMap<String, int[]> babaCache = new HashMap<String, int[]>();
    public static ArrayList<Integer> thingsExisting = new ArrayList<Integer>();//MAKE SURE ONLY ONE OF EACH OBJECT IS IN THIS LIST
    static{
        for (int i = 0; i < 10; i++) {
            thingsExisting.add(i);
        }
    }

    //Special
    public void youProperty(int d){
        int vertical=0;
        int horizontal=0;
        for (int i = 0; i < levelStoragePush.length; i++) {
            for (int j = 0; j < levelStoragePush[i].length; j++) {
                for (int k = 0; k < levelStoragePush[i][j].length ; k++) {
                    if (levelStoragePush[i][j][k][0]!=0) {

                        if((newmemoryEater.checkProperty(levelStoragePush[i][j][k][0],0)>0)&&levelStoragePush[i][j][k][3]<1){

                            switch(d){
                                case 0:
                                case 4:
                                    vertical=1;
                                    System.out.println("vertitcal1");
                                    break;
                                case 1:
                                case 5:
                                    horizontal=-1;
                                    System.out.println("horizontal-1");

                                    break;
                                case 3:
                                case 7:
                                    horizontal=1;
                                    System.out.println("horizontal1");
                                    break;
                                case 2:
                                case 6:
                                    vertical=-1;
                                    System.out.println("vertitcal-1");
                                    break;
                            }
                            System.out.println(horizontal+"horizontal");
                            System.out.println(horizontal+"horizontal");
                            System.out.println(horizontal+"horizontal");
                            System.out.println(vertical+"vertical");
                            System.out.println(vertical+"vertical");
                            System.out.println(vertical+"vertical");
                            levelStoragePush[i][j][k][1] =d%4;//rotation

                            if (i+horizontal!=-1&&i+horizontal!=getxTiles()&&j+vertical!=-1&&j+vertical!=getyTiles()) {

                                int temp =getOpenIndex(i+horizontal,j+vertical,levelStoragePush);
                                System.out.println(temp);
                                while (temp==-1){//this should never run more than once but its nice to have it freeze when something goes wrong
                                    levelStoragePush=expandZTile(i+horizontal,j+vertical,levelStoragePush);
                                    temp = getOpenIndex(i+horizontal,j+vertical,levelStoragePush);
                                    System.out.println(temp);
                                }//above here is the code to find an open Z position. Working :)


                                levelStoragePush[i+horizontal][j+vertical][temp][0] = levelStoragePush[i][j][k][0];//Copy ID                                levelStoragePush[i+1][j][temp][1]=3;
                                levelStoragePush[i+horizontal][j+vertical][temp][1] =d%4;//rotation
                                levelStoragePush[i+horizontal][j+vertical][temp][2]=levelStoragePush[i][j][k][2]+1;//walkingcycle
                                levelStoragePush[i+horizontal][j+vertical][temp][3]++;//hasbeenmoved


                                if (levelStoragePush[i+horizontal][j+vertical][temp][2]>3){levelStoragePush[i+horizontal][j+vertical][temp][2]=0;}
                                levelStoragePush[i][j][k][0]=0;
                                levelStoragePush[i][j][k][1]=0;
                                levelStoragePush[i][j][k][2]=0;
                                levelStoragePush[i][j][k][3]=0;
                                levelStoragePush[i][j][k][4]=0;
                                System.out.println(i+" "+j+" "+k);
                                babakey.removeImage(i,j,k);

                            }
                        }}
                }
            }
        }
        playGame();
    }
    public void playGame(){
        updateOrder();
        for (int i = 0; i < levelStoragePush.length; i++) {
            for (int j = 0; j < levelStoragePush[i].length; j++) {
                for (int k = 0; k < levelStoragePush[i][j].length ; k++) {
                    if (levelStoragePush[i][j][k][0]!=0||levelStoragePush[i][j][k]!=null){
                        levelStoragePush[i][j][k][3]=0;
                        levelStoragePush[i][j][k][4]=0;
                    }}}}
        newmemoryEater.push(levelStoragePush);
        System.out.println(levelStoragePush[0][0][0][0]+"play");
        try {
            babakey.ParserDisplay();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        levelStoragePush= newmemoryEater.peek();

    }
    //Controller of update order
    public void updateOrder(){
        shiftProperty();
        moveProperty();
        defeatProperty();
        winProperty();
    }
    //Active properties (run on game cycle, IE shift&move, defeat& win, theyre all in UpdateOrder)
    public void moveProperty(){//ID 4, on gamecycle moves tiles in direction of rotation
        int rotation = 0;
        int vertical=0;
        int horizontal=0;
        for (int i = 0; i < levelStoragePush.length; i++) {
            for (int j = 0; j < levelStoragePush[i].length; j++) {
                for (int k = 0; k < levelStoragePush[i][j].length ; k++) {
                    if (levelStoragePush[i][j][k][0]!=0) {

                        if((newmemoryEater.checkProperty(levelStoragePush[i][j][k][0],4)>0)&&levelStoragePush[i][j][k][4]<1){
                            rotation = levelStoragePush[i][j][k][1];
                            System.out.println(rotation+"rotation");
                            System.out.println(rotation+"rotation");
                            System.out.println(rotation+"rotation");
                            switch(rotation){
                                case 0:
                                case 4:
                                    vertical=1;
                                    break;
                                case 1:
                                case 5:
                                    horizontal=-1;
                                    break;
                                case 3:
                                case 7:
                                    horizontal=1;
                                    break;
                                case 2:
                                case 6:
                                    vertical=-1;
                                    break;
                            }
                            System.out.println(horizontal+"horizontal");
                            System.out.println(horizontal+"horizontal");
                            System.out.println(horizontal+"horizontal");
                            System.out.println(vertical+"vertical");
                            System.out.println(vertical+"vertical");
                            System.out.println(vertical+"vertical");

                            if (i+horizontal!=-1&&i+horizontal!=getxTiles()&&j+vertical!=-1&&j+vertical!=getyTiles()) {


                                int temp =getOpenIndex(i+horizontal,j+vertical,levelStoragePush);
                                System.out.println(temp);
                                while (temp==-1){//this should never run more than once but its nice to have it freeze when something goes wrong
                                    levelStoragePush=expandZTile(i+horizontal,j+vertical,levelStoragePush);
                                    temp = getOpenIndex(i+horizontal,j+vertical,levelStoragePush);
                                    System.out.println(temp);
                                }//above here is the code to find an open Z position. Working :)



                                levelStoragePush[i+horizontal][j+vertical][temp][0] = levelStoragePush[i][j][k][0];
                                levelStoragePush[i+horizontal][j+vertical][temp][1] =levelStoragePush[i][j][k][1];
                                levelStoragePush[i+horizontal][j+vertical][temp][2] = levelStoragePush[i][j][k][2] + 1;
                                levelStoragePush[i+horizontal][j+vertical][temp][4]++;
                                if (levelStoragePush[i+horizontal][j+vertical][temp][2] > 3) {
                                    levelStoragePush[i+horizontal][j+vertical][temp][2] = 0;
                                }


                                levelStoragePush[i][j][k][0] = 0;
                                levelStoragePush[i][j][k][1] = 0;
                                levelStoragePush[i][j][k][2] = 0;
                                levelStoragePush[i][j][k][3] = 0;
                                levelStoragePush[i][j][k][4] = 0;
                                System.out.println(i + " " + j + " " + k);
                                babakey.removeImage(i, j, k);
                            }
                        }}
                }
            }
        }
    }
    public void shiftProperty(){}
    public void defeatProperty(){}
    public void winProperty(){}
    //Reactive properties (stop, float, push, they are not in UpdateOrder)

    //Misc
}
