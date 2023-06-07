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

        add(0,0,27);         add(0,1,12);         add(0,2,13);         add(1,0,21);         add(1,1,12);         add(1,2,17);         for (int i = 0; i < 4; i++) {             add(2,i,1);         }         add(0,3,1);         add(1,3,1);         for (int i = 0; i < 6; i++) {             add(8+i,2,9);         }         add(8,3,9);         add(9,3,7);         add(10,3,9);         add(11,3,9);         add(12,3,6);         add(13,3,9);          for (int i = 0; i < 6; i++) {             add(8+i,4,9);         }         for (int i = 0; i < 4; i++) {             add(8+i,5,9);         }         add(12,5,6);         add(13,5,9);         for (int i = 0; i < 6; i++) {             add(8+i,6,9);         }                  for (int i = 0; i < 7; i++) {             add(7,1+i,1);         }         for (int i = 0; i < 6; i++) {             add(8+i,1,1);         }         for (int i = 0; i < 7; i++) {             add(14,1+i,1);         }          for (int i = 0; i < 4; i++) {             add(4+i,7,1);         }         for (int i = 0; i < 8; i++) {             add(4,7+i,1);         }         for (int i = 0; i < 12; i++) {             add(5+i,14,1);         }         for (int i = 0; i < 3; i++) {             add(11+i,7,1);         }         for (int i = 0; i < 4; i++) {             add(11,7+i,1);         }         for (int i = 0; i < 3; i++) {             add(15+i,7,1);         }         for (int i = 0; i < 8; i++) {             add(17,7+i,1);         }         for (int i = 0; i < 2; i++) {             add(11,12+i,1);         }         add(9,11,1);         for (int i = 0; i < 3; i++) {             add(5+i,11,3);         }         for (int i = 0; i < 3; i++) {             add(5+i,12,3);         }         add(5,13,10);         for (int i = 0; i < 2; i++) {             add(6+i,13,3);         }          add(6,4,23);         add(6,5,12);         add(6,6,20);         add(13,9,26);         add(14,9,12);         add(15,9,16);         add(13,12,30);         add(14,12,12);         add(15,12,14);          for (int i = 0; i < 3; i++) {             add(8+i,7,3);         }
        int levelnumber = 3;


        ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream("levels/level"+levelnumber+".data"));
        oos.writeObject(level);
        oos.flush();
        oos.close();
    }
}
