import jdk.nashorn.internal.runtime.JSONFunctions;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

//FIXME STOP CARING ABOUT MEMORY THIS IS LITERALLY NEVER GOING TO USE OVER 2KB OF RAM
//TODO Plan. NO ARRAY. Array will be annoying. 2D list? That would make life easier. 3D LIST! Then compress it back into a 1D list, of ordered things.
//Wait.. Does a 4D array with 5 1D elements take up much more space than a 1D array with 5 1D elements?
//(EX.) Baba - keke- wall are stored in that order next to eachother. In the 4D array beacuse idk there is one will they take up that much more space?
//An arraylist, of 2x2 arrays, of arraylists. Storing Baba objects? or integers?
//Advantages of storing Baba Objects - I have no idea.
//Advantages of storing ints. - Less memory. Easier to code (hopefully)
//Lets do ints.
public class Engine {
    private static int[][][][] levelSelect = new int[20][15][3][3];//the last one is basically the equivalent of BabaObject
    public static int[][][][] levelStoragePush = new int[20][15][3][3];
    public static int[][][][] levelStorage = new int[20][15][3][3];


    private int xTiles = 20;
    private int yTiles = 15;
    public static memoryController memoryEater = new memoryController();
    public static Engine BabaEngine = new Engine();

    private boolean isYou = true;
    private int Level;
    public Engine(){
        xTiles = 20;
        yTiles = 15;
        /*for (int i = 0; i < levelStoragePush.length; i++) {
            for (int j = 0; j < levelStoragePush[i].length; j++) {
                levelStoragePush[i][j]= new ArrayList<BabaObjects>();
            }

        }*/
    }
    public int getxTiles(){
        return xTiles;
    }
    public int getyTiles(){
        return yTiles;
    }
    public boolean isYou(){
        return isYou;
    }
    public void setxTiles(int x){
        xTiles = x;
    }
    public void setyTiles(int y){
        yTiles = y;
    }
    public void setLevel(int x){
        Level = x;
    }
    public int getLevel(){
        return Level;
    }

    public void moveLeft(){
        int[][][][] maybe= memoryEater.pullLatestState();
        for (int i = 0; i < maybe.length; i++) {
            for (int j = 0; j < maybe[i].length; j++) {
                for (int k = 0; k < (memoryEater.getPointer()) ; k++) {
                    if (maybe[i][j][k][0]!=0) {
                        levelStoragePush[i][j-1][k][0]=maybe[i][j][k][0];
                        levelStoragePush[i][j-1][k][1]=2;
                        levelStoragePush[i][j-1][k][2]=maybe[i][j][k][2]+1;
                        if (levelStoragePush[i][j-1][k][2]>3){levelStoragePush[i][j-1][k][2]=0;}
                        levelStoragePush[i][j][k][0]=0;
                        levelStoragePush[i][j][k][1]=0;
                        levelStoragePush[i][k][k][2]=0;
                    }
                }
            }
        }
        playGame();
    }

   /* public void moveRight(){
        ArrayList<BabaObjects>[][] maybe= memoryEater.pullLatestState();
        for (int i = 0; i < maybe.length; i++) {
            for (int j = 0; j < maybe[i].length; j++) {
                for (int k = 0; k < (maybe[i][j].size()) ; k++) {
                    if (maybe[i][j].get(k).checkIfDeleted()==false){
                            maybe[i][j].get(k).moveYouRight(i,j,k);
                    }

                }
            }
        }
        playGame();
    }
    public void moveDown(){

        ArrayList<BabaObjects>[][] maybe= memoryEater.pullLatestState();
        for (int i = 0; i < maybe.length; i++) {
            for (int j = 0; j < maybe[i].length; j++) {
                if (maybe[i][j] != null) {
                    maybe[i][j].forEach(obj -> obj.moveYouDown());
                }
            }
        }
        playGame();
    }
    public void moveUp(){
        ArrayList<BabaObjects>[][] maybe= memoryEater.pullLatestState();
        for (int i = 0; i < maybe.length; i++) {
            for (int j = 0; j < maybe[i].length; j++) {
                if (maybe[i][j] != null) {
                    maybe[i][j].forEach(obj -> obj.moveYouUp());
                }
            }
        }
        playGame();
    }*/
    public void moveWait(){
        playGame();
        BabaFrame.babakey.repaint();
    }
    public void moveUndo(){
        memoryEater.removeLastState();
        levelStoragePush= memoryEater.pullLatestState();}

    public void playGame(){
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
        memoryEater.pushNewState(levelStoragePush);


    }
    public void resetLevel(){
    }


}
