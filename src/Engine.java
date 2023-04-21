import java.util.Arrays;

public class Engine {
    public static Engine BabaEngine = new Engine();
    private int xTiles;
    private int yTiles;
    private boolean isYou = true;
    private int Level;
    public Engine(){
        int[][][] gameStuff = new int[40][40][5];
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
    //public int[][] findTiles(int x){
       //Addthegamestuff
    //}
    public void moveLeft(){}
    public void moveRight(){}
    public void moveDown(){}
    public void moveUp(){}
    public void moveNo(){}

    public void playGame(){}
    public void resetLevel(){}


}
