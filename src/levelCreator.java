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
    public static void main(String[] args) throws IOException {
        BufferedReader br= new BufferedReader(new InputStreamReader(System.in));
        System.out.println("What level are you making? (Int)");
        boolean done;
        int levelnumber = 0;
        while(true){
            done=true;
            try{
                levelnumber = Integer.valueOf(br.readLine());
            }
            catch (Exception e){done = false;
                System.out.println("Invalid input");}
            if(done){break;}
        }
        System.out.println("Type the level out in, x, space, y, space, ID for each tile");//Keep in mind this *is not* meant to be run by the user, and is only for developers. Code Safety (not crash)>Input Efficiency>User convenience
        System.out.printf("Type anything which doesnt fit x, y, ID to end.");
        while(true){
            String[] split = br.readLine().split(" ");
            if(split.length!=3){
                break;
            }
            try{add(Integer.valueOf(split[0]),Integer.valueOf(split[1]),Integer.valueOf(split[2]));}
            catch(Exception e){break;}
            System.out.println("Accepted value");
        }
        System.out.println("Writing to file.");

        ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream("levels/level"+levelnumber+".data"));
        oos.writeObject(level);
        oos.flush();
        oos.close();
    }
}
