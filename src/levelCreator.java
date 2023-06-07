import java.awt.event.KeyEvent;
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

        add(0,0,26);         add(1,0,12);         add(2,0,17);                   add(0,2,28);         add(1,2,12);         add(2,2,15);                  add(0,4,30);         add(1,4,12);         add(2,4,14);         for (int i = 0; i < 6; i++) {             add(3,i,6);         }         add(0,5,6);         add(1,5,6);         add(2,5,6);             for (int i = 0; i < 4; i++) {             add(1,7+i,1);             add(1+i,7,1);             add(1+i,11,1);             add(5,7-i,1);             add(5,11+i,1);             add(6+i,14,1);             add(10,14-i,1);         }         add(7,12,21);         add(8,12,12);         add(9,12,17);           add(3,13,27);         add(3,14,12);         add(3,15,13);          add(3,9,7);         add(5,7,1);         add(5,8,1);         add(5,10,1);         add(5,11,1);          add(6,4,1);          for (int i = 0; i < 11; i++) {             add(7,i,8);         }         for (int i = 0; i < 13; i++) {             add(7+i,10,8);         }           for (int i = 0; i < 7; i++) {             add(8+i,4,1);         }          for (int i = 0; i < 4; i++) {             add(14,5+i,1);             add(10+i,8,1);         }         add(10,7,1);         add(10,5,1);         add(12,6,10);         for (int i = 0; i < 3; i++) {             add(11+i,5,9);             add(11+i,7,9);         }         add(11,6,9);         add(13,6,9);
        add(8,9,1);add(9,9,1);add(10,9,1);
        add(11,9,4);
        add(12,9,4);
        add(11,11,4);
        add(12,11,4);
        add(13,11,4);

        add(15,15,11);
        add(18,14,11);
        add(19,0,5);
        add(18,0,5);
        add(17,0,5);
        add(19,1,5);
        add(18,1,5);
        add(19,2,5);
        add(0,19,5);
        add(1,19,5);
        add(0,18,5);
        add(0,17,5);
        add(1,18,5);


        int levelnumber = 6;


        ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream("levels/level"+levelnumber+".data"));
        oos.writeObject(level);
        oos.flush();
        oos.close();
    }
}
