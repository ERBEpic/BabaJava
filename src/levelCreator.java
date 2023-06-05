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
        for (int i = 0; i < 7; i++) {
            add(8+i,3,1);
        }
        for (int i = 0; i < 7; i++) {
            add(8+i,11,1);
        }
        for (int i = 0; i < 7; i++) {
            add(8+i,17,1);
        }
        for (int i = 0; i < 15; i++) {
            add(15,3+i,1);
        }
        for (int i = 0; i < 5; i++) {
            add(8,3+i,1);
        }
        for (int i = 0; i < 6; i++) {
            add(8,11+i,1);
        }
        for (int i = 0; i < 4; i++) {
            add(4+i,7,1);
        }
        for (int i = 0; i < 4; i++) {
            add(4+i,11,1);
        }
        for (int i = 0; i < 3; i++) {
            add(4,8+i,1);
        }
        for (int i = 0; i < 4; i++) {
            add(5+i,8,9);
        }
        for (int i = 0; i < 4; i++) {
            add(5+i,10,9);
        }
        add(5,9,9);
        add(7,9,9);
        add(8,9,9);


        add(10,5,12);
        add(13,7,14);

        add(10,9,5);

        add(6,9,25);



        add(5,13,27);
        add(5,14,12);
        add(5,15,13);
        add(10,13,21);
        add(10,14,12);
        add(10,15,17);
        add(13,14,7);




        add(19,13,10);
        add(11,1,10);
        add(5,2,10);
        add(4,1,10);

        add(18,5,10);
        add(19,5,10);


        add(5,19,10);
        add(5,18,10);
        add(4,19,10);

        add(1,13,10);




        int levelnumber = 2;


        ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream("levels/level"+levelnumber+".data"));
        oos.writeObject(level);
        oos.flush();
        oos.close();
    }
}
