import java.io.*;
import java.lang.*;
//This is NOT code meant for the user to run. This is DEVELOPER code meant for level creation.
public class levelCreator {
    static int[][][][] level = new int[20][20][0][5];
    public static void add(int x, int y, int id){
        int z = expandZTile(y,x);
        level[y][x][z][0]=id;
        level[y][x][z][1]=0;
        level[y][x][z][2]=0;
        level[y][x][z][3]=0;
        level[y][x][z][4]=0;
        if(id==8){//Skulls face DOWN by default
            level[y][x][z][1]=3;
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
    public static void main(String[] args) throws IOException {//TODO Change grass to be a connecting type


        add(0,0,21);
        add(1,0,12);
        add(2,0,17);

        add(3,0,2);
        for (int i = 0; i < 4; i++) {
            add(i,1,2);
        }

        for (int i = 0; i < 8; i++) {
            add(5,i,1);
        }
        add(6,4,1);
        add(10,4,1);
        add(7,3,27);
        add(8,3,12);
        add(9,3,13);
        add(11,6,1);
        add(11,5,6);
        add(11,0,1);
        add(11,2,1);
        add(11,3,1);
        add(11,4,1);

        add(6,7,26);
        add(7,7,1);
        add(6,8,12);
        add(6,9,16);


        for (int i = 0; i < 4; i++) {
            add(i,12,1);
            add(i,16,1);
            add(0,12+i,1);
            add(2,12+i,1);
        }
        add(1,13,22);
        add(1,14,12);
        add(1,15,18);
        add(3,13,27);
        add(3,14,12);
        add(3,15,19);

        for (int i = 0; i < 4; i++) {
            add(i+11,3,1);
            add(15,i,1);
        }
        add(12,1,7);

        for (int i = 0; i < 6; i++) {
            add(5+i,6,1);
        }

        add(8,10,22);




        add(17,19,30);
        add(18,19,12);
        add(19,19,14);

        add(18,17,10);

        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 2; j++) {
                add(10+i,10+j,2);
                add(10+j,10+i,2);
            }
        }

        int levelnumber = 5;


        ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream("levels/level"+levelnumber+".data"));
        oos.writeObject(level);
        oos.flush();
        oos.close();
    }
}
