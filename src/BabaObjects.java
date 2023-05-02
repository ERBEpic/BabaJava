import javax.imageio.ImageIO;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class BabaObjects {
    private boolean[][] propertiesStorage = new boolean[100][100];//[]=id,[][]=property
    private boolean toBeDeleted = false;
    private int x;
    private int y;
    private int z;
    private int rotation;
    private int id;
    private int walkingcycle;
    private Image image;

    public BabaObjects(){
    }

    public static void moveYouLeft(){
        int[][][][] maybe= Engine.memoryEater.pullLatestState();
        for (int i = 0; i < Engine.getxTiles(); i++) {
            for (int j = 1; j < Engine.getyTiles(); j++) {
                for (int k = 0; k < maybe[i][j].length ; k++) {
                    if (maybe[i][j][k][0]!=0&&maybe[i][j][k][3]<1) {
                        //Put something here to find an open z position
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
        for (int i = 0; i < Engine.getxTiles(); i++) {
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

    public boolean amYou(int x){
        return this.propertiesStorage[x][0];
    }
    public void setYou(boolean x, int id){
        this.propertiesStorage[id][0]=x;
    }
    public boolean checkIfDead(){
        return false;/*
        for (int i = 0; i < z; i++) {
            if (Engine.levelStoragePush[x][y].get(i).checkProperty(3)==true){//Check if defeat
                return true;
            }
        }
        return false;*/
    }
    public boolean checkIfDeleted(){
        return this.toBeDeleted;
    }
    public boolean checkProperty(int id, int property){
        return this.propertiesStorage[id][property];
    }
    public void setProperty(int id, int prop,boolean sign){
        this.propertiesStorage[id][prop]=sign;
    }
    public void deleteMe(){
        this.toBeDeleted=true;
    }
    public void hideImage(){

    }
    public void moveProperty(){
        int[][][][] maybe= Engine.levelStoragePush;
        for (int i = 0; i <= maybe.length-1; i++) {
            for (int j = 0; j <= maybe[i].length-1; j++) {
                for (int k = 0; k < maybe[i][j].length ; k++) {
                    if (maybe[i][j][k][0]!=0) {
                        if(Engine.properties.checkProperty(Engine.levelStoragePush[i][j][k][0],4)&&Engine.levelStoragePush[i][j][k][4]<1){
                            Engine.BabaEngine.moveLeft(i,j,k);
                            System.out.println("hio");
                        }}else{/*Engine.levelStoragePush[i][j][k][4]--;*/}
                }
            }
        }
    }
}
