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
    private static ArrayList<BabaObjects>[][] levelSelect = new ArrayList[20][15];
    private int xTiles = 20;
    private int yTiles = 15;
    public static memoryController memoryEater = new memoryController();
    public static Engine BabaEngine = new Engine();

    private boolean isYou = true;
    private int Level;
    public Engine(){
        xTiles = 20;
        yTiles = 15;
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
        ArrayList<BabaObjects>[][] maybe= memoryEater.pullLatestState();
        for (int i = 0; i < maybe.length; i++) {
            for (int j = 0; j < maybe[i].length; j++) {
                if (maybe[i][j] != null) {
                    maybe[i][j].forEach(obj -> obj.moveYouLeft());
                }
            }
        }
        playGame();
        }

    public void moveRight(){
        ArrayList<BabaObjects>[][] maybe= memoryEater.pullLatestState();
        for (int i = 0; i < maybe.length; i++) {
            for (int j = 0; j < maybe[i].length; j++) {
                if (maybe[i][j] != null) {
                    for (int k = 0; k < (maybe[i][j].size()) ; k++) {


                        maybe[i][j].get(k).moveYouRight(i, j, k);
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
    }
    public void moveWait(){
        playGame();
        BabaFrame.babakey.repaint();
    }
    public void moveUndo(){memoryEater.removeLastState();}

    public void playGame(){
        memoryEater.pushNewState(memoryEater.pullLatestState());
    }
    public void resetLevel(){}


}
