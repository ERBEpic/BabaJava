import java.io.*;
import java.lang.*;
//This is NOT code meant for the user to run. This is DEVELOPER code meant for level creation.
public class levelCreator {
    static int[][][][] level = new int[20][20][0][5];
    public static void add(int x, int y, int id){

        int z = expandZTile(y,x);
        level[y][x][z][0]=id;
        level[y][x][z][1]=4;
        level[y][x][z][2]=0;
        level[y][x][z][3]=0;
        level[y][x][z][4]=0;
        switch (id){//Rotatable
            case 4:
            case 7:
            case 8:
                level[y][x][z][1]-=4;
                break;
        }
        switch (id){//has a walking cycle
            case 7:
                level[y][x][z][2]=1;
        }
    }
    public static int expandZTile(int x, int y){
        int currentDepth = level[x][y].length;
        int[][] newArray = new int[currentDepth + 1][5];
        for (int z = 0; z < currentDepth; z++) {
            for (int i = 0; i < 5; i++) {
                newArray[z][i] = level[x][y][z][i];
            }
        }
        for (int i = 0; i < 5; i++) {
            newArray[currentDepth][i] = 0;
        }
        level[x][y] = newArray;//above this just expands
        return currentDepth;
    }
    public static void main(String[] args) throws IOException {
        add(0,0,6);//Why is this required?



        int levelnumber = 2;


        ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream("levels/level"+levelnumber+".data"));
        oos.writeObject(level);
        oos.flush();
        oos.close();
    }
}
