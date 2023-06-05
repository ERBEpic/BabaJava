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

        add(4,3,27);
        add(5,3,12);
        add(6,3,13);

        add(12,3,25);
        add(13,3,12);
        add(14,3,14);
        for (int i = 0; i < 11; i++) {
            add(4+i,5,1);
        }
        add(9,6,6);
        for (int i = 0; i < 5; i++) {
            add(4+i,6,9);
        }
        for (int i = 0; i < 5; i++) {
            add(10+i,6,9);
        }

        add(4,7,9);
        add(5,7,7);
        add(6,7,9);
        add(7,7,9);
        add(8,7,9);
        add(9,7,6);
        add(10,7,9);
        add(11,7,9);
        add(12,7,9);
        add(13,7,5);
        add(14,7,9);
        for (int i = 0; i < 5; i++) {
            add(4+i,8,9);
        }
        add(9,8,6);
        for (int i = 0; i < 5; i++) {
            add(10+i,8,9);
        }
        for (int i = 0; i < 11; i++) {
            add(4+i,9,1);
        }

        add(4,11,21);
        add(5,11,12);
        add(6,11,17);

        add(12,11,26);
        add(13,11,12);
        add(14,11,16);



        int levelnumber = 1;


        ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream("levels/level"+levelnumber+".data"));
        oos.writeObject(level);
        oos.flush();
        oos.close();
    }
}
