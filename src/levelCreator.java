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
        add(6,7,7);
        add(5,7,7);
        add(4,7,7);
        add(3,7,7);
        add(6,2,7);

        add(5,5,1);
        add(6,5,2);
        add(5,6,3);
        add(6,6,4);
        add(2,0,5);
        add(3,0,6);
        add(4,0,7);
        add(5,0,8);
        add(6,0,9);
        add(7,0,10);
        add(8,2,11);
        add(9,1,12);
        add(1,2,13);
        add(7,3,14);
        add(7,4,15);
        add(7,5,16);
        add(7,6,17);
        add(7,7,18);
        add(7,8,19);
        add(7,9,20);



        add(10,10,4);
        add(11,10,4);
        add(10,11,4);
        add(11,11,4);
        add(11,12,4);





        ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream("levels/level1.data"));
        oos.writeObject(level);
        oos.flush();
        oos.close();
        System.out.println(level[6][7][0][0]);
    }
}
