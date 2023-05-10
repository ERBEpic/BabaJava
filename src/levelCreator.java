import java.io.*;
import java.lang.*;
public class levelCreator {
    static int[][][][] level = new int[20][20][0][5];
    public static void add(int x, int y, int id, int rotation){
        int z = expandZTile(x,y);
        level[x][y][z][0]=id;
        level[x][y][z][1]=rotation;
        level[x][y][z][2]=0;
        level[x][y][z][3]=0;
        level[x][y][z][4]=0;
    }
    public static void add(int x, int y, int id){
        int z = expandZTile(x,y);
        level[x][y][z][0]=id;
        level[x][y][z][1]=0;
        level[x][y][z][2]=0;
        level[x][y][z][3]=0;
        level[x][y][z][4]=0;
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
        add(6,7,3);
        add(2,4,6);
        add(5,4,6);
        ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(new File("level1.data")));
        oos.writeObject(level);
        oos.flush();
        oos.close();
    }
}
