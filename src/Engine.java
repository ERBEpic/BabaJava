import jdk.nashorn.internal.runtime.JSONFunctions;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

//Wait.. Does a 4D array with 5 1D elements take up much more space than a 1D array with 5 1D elements?
//(EX.) Baba - keke- wall are stored in that order next to eachother. In the 4D array beacuse idk there is one will they take up that much more space?
//An arraylist, of 2x2 arrays, of arraylists. Storing Baba objects? or integers?
//Advantages of storing Baba Objects - I have no idea.
//Advantages of storing ints. - Less memory. Easier to code (hopefully)
//Lets do ints.
public class Engine {


    public static newmemoryController newmemoryEater;

    static {
        try {
            newmemoryEater = new newmemoryController();
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private static int[][][][] levelSelect = new int[20][15][3][5];//the last one is basically the equivalent of BabaObject
    public static int[][][][] levelStoragePush = new int[39][39][4][5];
    //public static BabaObjects properties = new BabaObjects();

    private static int xTiles = 20;
    private static int yTiles = 20;
    public static memoryController memoryEater = new memoryController();
    public static Engine BabaEngine = new Engine();

    private boolean isYou = true;
    private int Level;
    public Engine(){
        xTiles = 20;
        yTiles = 20;
        /*for (int i = 0; i < levelStoragePush.length; i++) {
            for (int j = 0; j < levelStoragePush[i].length; j++) {
                levelStoragePush[i][j]= new ArrayList<BabaObjects>();
            }
        }*/
    }

    public static int getxTiles(){
        return xTiles;
    }
    public static int getyTiles(){
        return yTiles;
    }
    public boolean isYou(){
        return isYou;
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
                        Baba3DFrame.babakey.removeImage(i,j,k);
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
            Baba3DFrame.babakey.removeImage(i, j, k);
        }
    }
    public void moveUp(){int[][][][] maybe= memoryEater.pullLatestState();
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
                        Baba3DFrame.babakey.removeImage(i,j,k);
                    }
                }
            }
        }
        playGame();
    }
    public void moveDown(){
        //Put something here to find an open z position
        int[][][][] maybe= memoryEater.pullLatestState();
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
                        Baba3DFrame.babakey.removeImage(i,j,k);
                    }
                }
            }
        }
        playGame();
    }
    public void moveWait(){
        playGame();
        Baba3DFrame.babakey.repaint();
    }
    public void moveUndo(){
        int x=Engine.memoryEater.getPointer();
        if(Engine.memoryEater.pullNState(x)==null){
            System.out.println("null!!");
        }
        Engine.memoryEater.removePointer();int[][][][] arraynew = new int[40][40][4][4];

        for (int i = 0; i < 2; i++) {
            arraynew[5][4][1][i] = 3;
        }
        arraynew[10][11][1][0]=6;
        Engine.levelStoragePush=Engine.memoryEater.pullLatestState();
    }

    public static void playGame(){//Idk why this has to be static but it doesnt break so
       /* for (int i = 0; i < levelStoragePush.length; i++) {
            for (int j = 0; j < levelStoragePush[i].length; j++) {
                for (int k = 0; k < (levelStoragePush[i][j].size()) ; k++) {
                    if (levelStoragePush[i][j].get(k).checkIfDeleted()==false&&levelStoragePush[i][j].get(k).checkIfDead()==false){
                        if (levelStorage[i][j]==null){
                            levelStorage[i][j] =new ArrayList<BabaObjects>();
                        }
                        levelStorage[i][j].add(levelStoragePush[i][j].get(k));
                    }
                }
            }
        }*/
        Engine.moveProperty();
        for (int i = 0; i < levelStoragePush.length-1; i++) {
            for (int j = 0; j < levelStoragePush[i].length-1; j++) {
                for (int k = 0; k < levelStoragePush[i][j].length ; k++) {
                    if (levelStoragePush[i][j][k][0]!=0||levelStoragePush[i][j][k]!=null){
                        levelStoragePush[i][j][k][3]=0;
                        levelStoragePush[i][j][k][4]=0;
                    }}}}
        memoryEater.pushNewState(levelStoragePush);
    }

    public void resetLevel(){
        memoryEater.resetState();
        levelStoragePush= memoryEater.pullLatestState();
    }

    //Babaobjects here


    private static boolean[][] propertiesStorage = new boolean[100][100];//[]=id,[][]=property


    public static void moveYouLeft(){
        int[][][][] maybe= Engine.memoryEater.pullLatestState();
        for (int i = 0; i < Engine.getxTiles(); i++) {
            for (int j = 1; j < Engine.getyTiles(); j++) {
                for (int k = 0; k < maybe[i][j].length ; k++) {
                    if (maybe[i][j][k][0]!=0&&maybe[i][j][k][3]<1) {
                        //donext Put something here to find an open z position (this applies to all but im not doing them all and flooding intellij)
                        Engine.levelStoragePush[i][j-1][k][0]=maybe[i][j][k][0];
                        Engine.levelStoragePush[i][j-1][k][1]=2;
                        Engine.levelStoragePush[i][j-1][k][2]=maybe[i][j][k][2]+1;
                        if (Engine.levelStoragePush[i][j-1][k][2]>3){Engine.levelStoragePush[i][j-1][k][2]=0;}
                        Engine.levelStoragePush[i][j][k][0]=0;
                        Engine.levelStoragePush[i][j][k][1]=0;
                        Engine.levelStoragePush[i][j][k][2]=0;
                        Engine.levelStoragePush[i][j][k][3] = 0;
                        Engine.levelStoragePush[i][j][k][4] = 0;
                        System.out.println(i+" "+j+" "+k);
                        Baba3DFrame.babakey.removeImage(i,j,k);
                    }
                }
            }
        }
        Engine.playGame();
    }

    public static void moveYouRight(){
        System.out.println(Engine.getxTiles());
        int[][][][] maybe= Engine.memoryEater.pullLatestState();
        for (int i = 0; i < Engine.getxTiles(); i++) {
            for (int j = 0; j < Engine.getyTiles()-1; j++) {
                for (int k = 0; k < maybe[i][j].length ; k++) {
                    if (maybe[i][j][k][0]!=0&&maybe[i][j][k][3]<1) {
                        //Put something here to find an open z position
                        Engine.levelStoragePush[i][j+1][k][0]=maybe[i][j][k][0];
                        Engine.levelStoragePush[i][j+1][k][1]=0;
                        Engine.levelStoragePush[i][j+1][k][2]=maybe[i][j][k][2]+1;
                        Engine.levelStoragePush[i][j+1][k][3]++;
                        if (Engine.levelStoragePush[i][j+1][k][2]>3){Engine.levelStoragePush[i][j+1][k][2]=0;}
                        Engine.levelStoragePush[i][j][k][0]=0;
                        Engine.levelStoragePush[i][j][k][1]=0;
                        Engine.levelStoragePush[i][j][k][2]=0;
                        Engine.levelStoragePush[i][j][k][3] = 0;
                        Engine.levelStoragePush[i][j][k][4] = 0;
                        System.out.println(i+" "+j+" "+k);
                        Baba3DFrame.babakey.removeImage(i,j,k);
                    }
                }
            }
        }
        Engine.playGame();
    }

    public static void moveYouUp() {//Whydoesthisworkasstaticidontunderstand
        int[][][][] maybe = Engine.memoryEater.pullLatestState();
        for (int i = 1; i < Engine.getxTiles(); i++) {
            for (int j = 0; j < Engine.getyTiles(); j++) {
                for (int k = 0; k < maybe[i][j].length; k++) {
                    if (maybe[i][j][k][0]!=0&&maybe[i][j][k][3]<1) {
                        //Put something here to find an open z position
                        Engine.levelStoragePush[i-1][j][k][0] = maybe[i][j][k][0];
                        Engine.levelStoragePush[i-1][j][k][1] = 1;
                        Engine.levelStoragePush[i-1][j][k][2] = maybe[i][j][k][2] + 1;
                        if (Engine.levelStoragePush[i-1][j][k][2] > 3) {
                            Engine.levelStoragePush[i-1][j][k][2] = 0;
                        }
                        Engine.levelStoragePush[i][j][k][0] = 0;
                        Engine.levelStoragePush[i][j][k][1] = 0;
                        Engine.levelStoragePush[i][j][k][2] = 0;
                        Engine.levelStoragePush[i][j][k][3] = 0;
                        Engine.levelStoragePush[i][j][k][4] = 0;
                        System.out.println(i + " " + j + " " + k);
                        Baba3DFrame.babakey.removeImage(i, j, k);
                    }
                }
            }
        }
        Engine.playGame();
    }
    public static void moveYouDown(){
        int[][][][] maybe= Engine.memoryEater.pullLatestState();
        for (int i = 0; i < Engine.getxTiles()-1; i++) {
            for (int j = 0; j < Engine.getyTiles(); j++) {
                for (int k = 0; k < maybe[i][j].length ; k++) {
                    if (maybe[i][j][k][0]!=0&&maybe[i][j][k][3]<1) {
                        //Put something here to find an open z position
                        Engine.levelStoragePush[i+1][j][k][0]=maybe[i][j][k][0];
                        Engine.levelStoragePush[i+1][j][k][1]=3;
                        Engine.levelStoragePush[i+1][j][k][2]=maybe[i][j][k][2]+1;
                        Engine.levelStoragePush[i+1][j][k][3]++;
                        if (Engine.levelStoragePush[i+1][j][k][2]>3){Engine.levelStoragePush[i+1][j][k][2]=0;}
                        Engine.levelStoragePush[i][j][k][0]=0;
                        Engine.levelStoragePush[i][j][k][1]=0;
                        Engine.levelStoragePush[i][j][k][2]=0;
                        Engine.levelStoragePush[i][j][k][3]=0;
                        Engine.levelStoragePush[i][j][k][4]=0;
                        System.out.println(i+" "+j+" "+k);
                        Baba3DFrame.babakey.removeImage(i,j,k);
                    }
                }
            }
        }
        Engine.playGame();
    }
    public static void moveProperty(){
        int[][][][] maybe= Engine.levelStoragePush;
        for (int i = 0; i <= maybe.length-1; i++) {
            for (int j = 0; j <= maybe[i].length-1; j++) {
                for (int k = 0; k < maybe[i][j].length ; k++) {
                    if (maybe[i][j][k][0]!=0) {
                        if(Engine.checkProperty(Engine.levelStoragePush[i][j][k][0],4)&&Engine.levelStoragePush[i][j][k][4]<1){
                            Engine.BabaEngine.moveLeft(i,j,k);
                            System.out.println("hio");
                        }}else{/*Engine.levelStoragePush[i][j][k][4]--;*/}
                }
            }
        }
    }
    public static boolean checkProperty(int id, int property){
        return Engine.propertiesStorage[id][property];
    }
    public static void setProperty(int id, int prop, boolean sign){
        Engine.propertiesStorage[id][prop]=sign;
    }

}
