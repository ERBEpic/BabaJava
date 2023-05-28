import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Stream;

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
        newmemoryEater = new newmemoryController();
        babakey = new BabaFrameSimple(24,41,41);
        babakey.setEngine(this);//I kinda guessed you could just pass along (this) but Im glad that it works
    }

    //it should basically never crash but its easier to solve the try catch problems here



    private int[][][][] levelSelect = new int[20][15][3][5];//the last one is basically the equivalent of BabaObject
    public int[][][][] levelStoragePush = new int[40][40][4][5];
    //public static BabaObjects properties = new BabaObjects();

    private static int xTiles = 20;
    private static int yTiles = 20;
    private int[][] propertiesStorage = new int[100][100];//[]=id,[][]=property
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

    /*public void moveProperty(){//donext get this to be rotation dependant
        int[][][][] maybe= levelStoragePush;
        //Put something here to find an open z position
        for (int i = 1; i < maybe.length-1; i++) {
            for (int j = 1; j < maybe[i].length-1; j++) {
                for (int k = 0; k < maybe[i][j].length ; k++) {
                    if (maybe[i][j][k][0]!=0) {
                        if(properties.checkProperty(levelStoragePush[i][j][k][0],4)&&levelStoragePush[i][j][k][4]<1){
                        levelStoragePush[i][j-1][k][0]=maybe[i][j][k][0];
                        levelStoragePush[i][j-1][k][1]=2;
                        levelStoragePush[i][j-1][k][2]=maybe[i][j][k][2]+1;
                        levelStoragePush[i][j-1][k][4]++;
                        if (levelStoragePush[i][j-1][k][2]>3){levelStoragePush[i][j-1][k][2]=0;}
                        levelStoragePush[i][j][k][0]=0;
                        levelStoragePush[i][j][k][1]=0;
                        levelStoragePush[i][k][k][2]=0;
                        System.out.println(i+" "+j+" "+k);
                        BabaFrameSimple.babakey.removeImage(i,j,k);
                    }}else{/*levelStoragePush[i][j][k][4]--;}
                }
            }
        }
    }*/

    public void moveLeft(int i, int j, int k) {
        if (j>0) {
            //Put something here to find an open z position
            levelStoragePush[i][j - 1][k][0] = levelStoragePush[i][j][k][0];
            levelStoragePush[i][j - 1][k][1] = 2;
            levelStoragePush[i][j - 1][k][2] = levelStoragePush[i][j][k][2] + 1;
            levelStoragePush[i][j - 1][k][4]++;
            if (levelStoragePush[i][j - 1][k][2] > 3) {
                levelStoragePush[i][j - 1][k][2] = 0;
            }
            levelStoragePush[i][j][k][0] = 0;
            levelStoragePush[i][j][k][1] = 0;
            levelStoragePush[i][j][k][2] = 0;
            levelStoragePush[i][j][k][3] = 0;
            levelStoragePush[i][j][k][4] = 0;
            System.out.println(i + " " + j + " " + k);
            babakey.removeImage(i, j, k);
        }
    }
    public void moveUp(){int[][][][] maybe= this.newmemoryEater.peek();
        //Put something here to find an open z position
        for (int i = 1; i < maybe.length-1; i++) {
            for (int j = 1; j < maybe[i].length-1; j++) {
                for (int k = 0; k < maybe[i][j].length; k++) {
                    if (maybe[i][j][k][0]!=0) {
                        levelStoragePush[i-1][j][k][0]=maybe[i][j][k][0];
                        levelStoragePush[i-1][j][k][1]=1;
                        levelStoragePush[i-1][j][k][2]=maybe[i][j][k][2]+1;
                        if (levelStoragePush[i-1][j][k][2]>3){levelStoragePush[i-1][j][k][2]=0;}
                        levelStoragePush[i][j][k][0]=0;
                        levelStoragePush[i][j][k][1]=0;
                        levelStoragePush[i][k][k][2]=0;
                        System.out.println(i+" "+j+" "+k);
                        babakey.removeImage(i,j,k);
                    }
                }
            }
        }
        playGame();
    }
    public void moveDown(){
        //Put something here to find an open z position
        int[][][][] maybe= newmemoryEater.peek();
        for (int i = 1; i < maybe.length-1; i++) {
            for (int j = 1; j < maybe[i].length-1; j++) {
                for (int k = 0; k < maybe[i][j].length ; k++) {
                    if (maybe[i][j][k][0]!=0&&maybe[i][j][k][3]<1) {
                        levelStoragePush[i+1][j][k][0]=maybe[i][j][k][0];
                        levelStoragePush[i+1][j][k][1]=3;
                        levelStoragePush[i+1][j][k][2]=maybe[i][j][k][2]+1;
                        levelStoragePush[i+1][j][k][3]++;
                        if (levelStoragePush[i+1][j][k][2]>3){levelStoragePush[i+1][j][k][2]=0;}
                        levelStoragePush[i][j][k][0]=0;
                        levelStoragePush[i][j][k][1]=0;
                        levelStoragePush[i][k][k][2]=0;
                        System.out.println(i+" "+j+" "+k);
                        babakey.removeImage(i,j,k);
                    }
                }
            }
        }
        playGame();
    }
    public void moveWait(){
        playGame();
    }

    public void moveUndoNew(){
        if(this.newmemoryEater.getSize()>1){
           levelStoragePush=this.newmemoryEater.pop();
        }
    }

    public void playGame(){
        moveProperty();
        for (int i = 0; i < levelStoragePush.length-1; i++) {
            for (int j = 0; j < levelStoragePush[i].length-1; j++) {
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

    }

    public static void main(String[] args) {

    }

    public void resetLevel(){
        /*int[][][][] maybe= Engine.newmemoryEater.peek();
        for (int i = 0; i < Engine.getxTiles(); i++) {
            for (int j = 0; j < Engine.getyTiles(); j++) {
                for (int k = 0; k < maybe[i][j].length ; k++) {
                    BabaFrameSimple.babakey.removeImage(i,j,k);
                    }
                }
            }*/
       // BabaFrameSimple.babakey.removeallImage();
        levelStoragePush = new int[40][40][4][5];
        newmemoryEater.reset();


    }//fixme, wasnt even wrking before new memory. Maybe it was the root of the problem?

    //Babaobjects here





    public void moveYouLeft(){
        int[][][][] maybe= newmemoryEater.peek();
        for (int i = 0; i < Engine.getxTiles(); i++) {
            for (int j = 1; j < Engine.getyTiles(); j++) {
                for (int k = 0; k < maybe[i][j].length ; k++) {
                    if (maybe[i][j][k][0]!=0&&maybe[i][j][k][3]<1) {
                        //donext Put something here to find an open z position (this applies to all but im not doing them all and flooding intellij)
                        levelStoragePush[i][j-1][k][0]=maybe[i][j][k][0];
                        levelStoragePush[i][j-1][k][1]=2;
                        levelStoragePush[i][j-1][k][2]=maybe[i][j][k][2]+1;
                        if (levelStoragePush[i][j-1][k][2]>3){levelStoragePush[i][j-1][k][2]=0;}
                        levelStoragePush[i][j][k][0]=0;
                        levelStoragePush[i][j][k][1]=0;
                        levelStoragePush[i][j][k][2]=0;
                        levelStoragePush[i][j][k][3] = 0;
                        levelStoragePush[i][j][k][4] = 0;
                        System.out.println(i+" "+j+" "+k);
                        babakey.removeImage(i,j,k);
                    }
                }
            }
        }
        playGame();
    }

    public void moveYouRight(){
        System.out.println(Engine.getxTiles());
        int[][][][] maybe= newmemoryEater.peek();
        for (int i = 0; i < Engine.getxTiles(); i++) {
            for (int j = 0; j < Engine.getyTiles()-1; j++) {
                for (int k = 0; k < maybe[i][j].length ; k++) {
                    if (maybe[i][j][k][0]!=0&&maybe[i][j][k][3]<1) {
                        //Put something here to find an open z position
                        levelStoragePush[i][j+1][k][0]=maybe[i][j][k][0];
                        levelStoragePush[i][j+1][k][1]=0;
                        levelStoragePush[i][j+1][k][2]=maybe[i][j][k][2]+1;
                        levelStoragePush[i][j+1][k][3]++;
                        if (levelStoragePush[i][j+1][k][2]>3){levelStoragePush[i][j+1][k][2]=0;}
                        levelStoragePush[i][j][k][0]=0;
                        levelStoragePush[i][j][k][1]=0;
                        levelStoragePush[i][j][k][2]=0;
                        levelStoragePush[i][j][k][3] = 0;
                        levelStoragePush[i][j][k][4] = 0;
                        System.out.println(i+" "+j+" "+k);
                        babakey.removeImage(i,j,k);
                    }
                }
            }
        }
        playGame();
    }
    public void moveYouUp() {
        int[][][][] maybe = newmemoryEater.peek();
        System.out.println(maybe[6][7][0][0]);
        for (int i = 1; i < Engine.getxTiles(); i++) {
            for (int j = 0; j < Engine.getyTiles(); j++) {
                for (int k = 0; k < maybe[i][j].length; k++) {
                    if (maybe[i][j][k][0]!=0&&maybe[i][j][k][3]<1) {

                        //Put something here to find an open z position
                        levelStoragePush[i-1][j][k][0] = maybe[i][j][k][0];
                        levelStoragePush[i-1][j][k][1] = 1;
                        levelStoragePush[i-1][j][k][2] = maybe[i][j][k][2] + 1;
                        if (levelStoragePush[i-1][j][k][2] > 3) {
                            levelStoragePush[i-1][j][k][2] = 0;
                        }
                        levelStoragePush[i][j][k][0] = 0;
                        levelStoragePush[i][j][k][1] = 0;
                        levelStoragePush[i][j][k][2] = 0;
                        levelStoragePush[i][j][k][3] = 0;
                        levelStoragePush[i][j][k][4] = 0;
                        System.out.println(i + " " + j + " " + k);
                        babakey.removeImage(i, j, k);
                    }
                }
            }
        }
        playGame();
    }
    public void moveYouDown(){
        int[][][][] maybe= newmemoryEater.peek();
        for (int i = 0; i < Engine.getxTiles()-1; i++) {
            for (int j = 0; j < Engine.getyTiles(); j++) {
                for (int k = 0; k < maybe[i][j].length ; k++) {
                    if (maybe[i][j][k][0]!=0&&maybe[i][j][k][3]<1) {
                        //Put something here to find an open z position
                        levelStoragePush[i+1][j][k][0]=maybe[i][j][k][0];
                        levelStoragePush[i+1][j][k][1]=3;
                        levelStoragePush[i+1][j][k][2]=maybe[i][j][k][2]+1;
                        levelStoragePush[i+1][j][k][3]++;
                        if (levelStoragePush[i+1][j][k][2]>3){levelStoragePush[i+1][j][k][2]=0;}
                        levelStoragePush[i][j][k][0]=0;
                        levelStoragePush[i][j][k][1]=0;
                        levelStoragePush[i][j][k][2]=0;
                        levelStoragePush[i][j][k][3]=0;
                        levelStoragePush[i][j][k][4]=0;
                        System.out.println(i+" "+j+" "+k);
                        babakey.removeImage(i,j,k);
                    }
                }
            }
        }
        playGame();
    }
    public void moveProperty(){
        int[][][][] maybe= levelStoragePush;
        for (int i = 0; i <= maybe.length-1; i++) {
            for (int j = 0; j <= maybe[i].length-1; j++) {
                for (int k = 0; k < maybe[i][j].length ; k++) {
                    if (maybe[i][j][k][0]!=0) {
                        if((checkProperty(levelStoragePush[i][j][k][0],4)>0)&&levelStoragePush[i][j][k][4]<1){
                            moveLeft(i,j,k);
                            System.out.println("hio");
                        }}else{/*levelStoragePush[i][j][k][4]--;*/}
                }
            }
        }
    }
    private static HashMap<String, int[]> babaCache = new HashMap<String, int[]>();
    public static ArrayList<Integer> thingsExisting = new ArrayList<Integer>();//MAKE SURE ONLY ONE OF EACH OBJECT IS IN THIS LIST
    static{
        for (int i = 0; i < 10; i++) {
            thingsExisting.add(i);
        }
    }
    public void moveBetter()//todo put all the move into one BIG move method
    {int[][][][] maybe= newmemoryEater.peek();
        ArrayList<Integer> moving = new ArrayList<Integer>();
        ArrayList<Integer> movingYou = new ArrayList<Integer>();
        //Make a cache of all objects in thingsExisting that are in the level and check only those properties. Dont check everything that can exist, check everything that does exist. todo when you make a level select system, refresh thingsExisting
        for (int i = 0; i < thingsExisting.size(); i++) {
            if (propertiesStorage[thingsExisting.get(i)][0]>0){
                moving.add(thingsExisting.get(i));
            }
        }
        }

    public int checkProperty(int id, int property){
        return propertiesStorage[id][property];
    }
    public void setProperty(int id, int prop, int sign){
        propertiesStorage[id][prop]=sign;
    }


}
