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
        add(6,5,1);
        add(5,6,1);
        add(6,6,1);
        add(2,0,1);
        add(3,0,1);
        add(4,0,1);
        add(5,0,1);
        add(6,0,1);
        add(7,0,1);
        add(8,2,1);
        add(9,1,1);
        add(1,2,1);
        add(7,3,1);
        add(7,4,1);
        add(7,5,1);
        add(7,6,1);
        add(7,7,1);
        add(7,8,1);
        add(7,9,2);



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
