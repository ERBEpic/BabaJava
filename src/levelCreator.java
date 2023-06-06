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
        for (int i = 0; i < 7; i++) {
            add(8+i,3,5);
        }
        for (int i = 0; i < 7; i++) {
            add(8+i,11,5);
        }
        for (int i = 0; i < 7; i++) {
            add(8+i,17,5);
        }
        for (int i = 0; i < 15; i++) {
            add(15,3+i,5);
        }
        for (int i = 0; i < 5; i++) {
            add(8,3+i,5);
        }
        for (int i = 0; i < 6; i++) {
            add(8,11+i,5);
        }
        for (int i = 0; i < 4; i++) {
            add(4+i,7,5);
        }
        for (int i = 0; i < 4; i++) {
            add(4+i,11,5);
        }
        for (int i = 0; i < 3; i++) {
            add(4,8+i,5);
        }
        for (int i = 0; i < 4; i++) {
            add(5+i,8,10);
        }
        for (int i = 0; i < 4; i++) {
            add(5+i,10,10);
        }
        add(5,9,10);
        add(6,9,27);
        add(7,9,10);
        add(8,9,10);


        add(10,5,12);
        add(13,7,14);





        add(5,13,21);
        add(5,14,12);
        add(5,15,13);
        add(10,13,25);//right
        add(10,14,12);
        add(10,15,17);
        add(13,14,1);




        add(19,13,9);
        add(11,1,9);
        add(5,2,9);
        add(4,1,9);

        add(18,5,9);
        add(19,5,9);


        add(5,19,9);
        add(5,18,9);
        add(4,19,9);

        add(1,13,9);







        int levelnumber = 2;


        ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream("levels/level"+levelnumber+".data"));
        oos.writeObject(level);
        oos.flush();
        oos.close();
    }
}
