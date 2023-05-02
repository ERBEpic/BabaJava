import jdk.nashorn.internal.runtime.JSONFunctions;

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
    private static int[][][][] levelSelect = new int[20][15][3][5];//the last one is basically the equivalent of BabaObject
    public static int[][][][] levelStoragePush = new int[39][39][4][5];
    public static BabaObjects properties = new BabaObjects();

    private static int xTiles = 20;
    private static int yTiles = 15;
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

    public void moveProperty(){
        int[][][][] maybe= levelStoragePush;
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
                    }}else{/*levelStoragePush[i][j][k][4]--;*/}
                }
            }
        }
    }

    public void moveLeft(int i, int j, int k) {
        if (j>0) {
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
        memoryEater.removeLastState();
        levelStoragePush= memoryEater.pullLatestState();}

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
        Engine.properties.moveProperty();
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
    }


}
