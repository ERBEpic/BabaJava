import java.io.*;
import java.lang.*;
//This is NOT code meant for the user to run. This is DEVELOPER code meant for level creation.
public class levelCreator {
    static int[][][][] level = new int[20][20][0][5];
    public static void add(int x, int y, int id, int rotation){
        int z = expandZTile(x,y);
        level[x][y][z][0]=id;
        level[x][y][z][1]=rotation;
        level[x][y][z][2]=0;
        level[x][y][z][3]=0;
        level[x][y][z][4]=0;
        switch (id){
            case 5:
                level[x][y][z][1]+=4;
                break;
        }
    }
    public static void add(int x, int y, int id){
        int z = expandZTile(x,y);
        level[x][y][z][0]=id;
        level[x][y][z][1]=0;
        level[x][y][z][2]=0;
        level[x][y][z][3]=0;
        level[x][y][z][4]=0;
        switch (id){//Unrotatable
            case 1:
            case 2:
            case 3:
            case 5:
            case 6:
            case 9:
            case 10:
            case 11:
                level[x][y][z][1]+=4;
                break;
        }
        switch (id){//has a walking cycle
            case 7:
                level[x][y][z][2]=1;
        }
    }
    public static int findValidAndExpand(int x, int y){
        int z = 0;
        x--;
        y--;
        while(true){
            int[] i;
                try{i = level[x][y][z];
                    if (i == null) {
                        Exception ArrayBad = new Exception();
                        throw (ArrayBad);
                    }}
                catch (Exception a){
                    System.out.println("bye");
                    int[][][][] tempArray = new int[20][20][z+1][5];
                    System.arraycopy(level,0, tempArray,0,z);
                    level = tempArray;
                    return z;
                }
                z++;
            System.out.println("hai");
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
        add(6,7,7);
        add(2,4,6);
        add(5,4,6);
        add(0,0,6);
        add(19,19,6);
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
        add(7,2,1);
        add(7,1,1);
        add(7,2,1);
        add(7,3,1);
        add(7,4,1);
        add(7,5,1);
        add(7,6,1);
        add(7,7,1);
        add(7,8,1);







        ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(new File("level1.data")));
        oos.writeObject(level);
        oos.flush();
        oos.close();
        System.out.println(level[6][7][0][0]);
    }
}
