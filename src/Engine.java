import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
/*
This class is responsible for holding the previous state from memoryController, whether that be the starting state or previously calculated state is irrelevant.
When it recieves an input, if it is a direct game input (IE up down left right wait), it moves (or doesnt) the player, and calculates one game cycle (if the player isnt dead) (Game cycle = playgame)
It then outputs that resulting new state back into memoryController, as a new state. The next game cycle works off that state just made.
It also directly talks with Babaframe, in add and remove images.
*/
public class Engine {
    //public static memoryController memoryEater = new memoryController();
    public newmemoryController newmemoryEater;
    public BabaFrameSimple babakey;

    public Engine() throws IOException, ClassNotFoundException, InterruptedException {
        Thread.sleep(100);
        newmemoryEater = new newmemoryController(this);//every engine NEEDS a memorycontroller
        babakey = new BabaFrameSimple(20,20);
        babakey.setEngine(this);//I kinda guessed you could just pass along (this) but Im glad that it works
        //^^ also not every frame needs an engine so its changeable (this isnt just inconsistent coding)

    }

    //it should basically never crash but its easier to solve the try catch problems here



    public int[][][][] levelStoragePush = new int[40][40][4][5];
    //public static BabaObjects properties = new BabaObjects();

    private static int xTiles = 20;
    private static int yTiles = 20;

    public static int getxTiles(){
        return xTiles;
    }
    public static int getyTiles(){
        return yTiles;
    }

    public void moveWait(){
        playGame();
    }

    public void moveUndoNew() {
        if (newmemoryEater.getSize() > 1) {
            newmemoryEater.pop();
            this.levelStoragePush = newmemoryController.deepCopy(newmemoryEater.peek());
        }
    }




    public void resetLevel(){
        newmemoryEater.reset();
        int[][][][] x  = newmemoryEater.peek();
        newmemoryEater.allOut00();
        this.levelStoragePush= x;
        System.out.println(levelStoragePush[0][0][0][0]);
        babakey.clear();


    }





    private int getOpenIndex(int i, int j,int[][][][] maybe) {
        for (int k = 0; k < maybe[i][j].length; k++) {
            if (levelStoragePush[i][j][k][0] == 0) {
                return k;
            }
        }
        return -1; // No open index found
    }
    public static int[][][][] expandZTile(int x, int y, int[][][][] maybe){//I stole this from levelCreator
        int currentDepth = maybe[x][y].length;
        int[][] newArray = new int[currentDepth + 1][5];
        for (int z = 0; z < currentDepth; z++) {
            for (int i = 0; i < 5; i++) {
                newArray[z][i] = maybe[x][y][z][i];
            }
        }
        for (int i = 0; i < 5; i++) {
            newArray[currentDepth][i] = 0;
        }
        maybe[x][y] = newArray;//above this just expands
        return maybe;
    }



    //Special
    public void youProperty(int d){
        int vertical=0;
        int horizontal=0;
        for (int i = 0; i < levelStoragePush.length; i++) {
            for (int j = 0; j < levelStoragePush[i].length; j++) {
                for (int k = 0; k < levelStoragePush[i][j].length ; k++) {
                    if (levelStoragePush[i][j][k][0]!=0) {

                        if((this.newmemoryEater.checkProperty(levelStoragePush[i][j][k][0],0)>0)&&levelStoragePush[i][j][k][3]<1){

                            switch(d){//This converts rotation from its udlr to H/V movements.
                                case 0:
                                case 4:
                                    vertical=1;
                                    System.out.println("vertitcal1");
                                    break;
                                case 1:
                                case 5:
                                    horizontal=-1;
                                    System.out.println("horizontal-1");

                                    break;
                                case 3:
                                case 7:
                                    horizontal=1;
                                    System.out.println("horizontal1");
                                    break;
                                case 2:
                                case 6:
                                    vertical=-1;
                                    System.out.println("vertitcal-1");
                                    break;
                            }
                            System.out.println(horizontal+"horizontal");
                            System.out.println(horizontal+"horizontal");
                            System.out.println(horizontal+"horizontal");
                            System.out.println(vertical+"vertical");
                            System.out.println(vertical+"vertical");
                            System.out.println(vertical+"vertical");
                            if (levelStoragePush[i][j][k][1]>=4){levelStoragePush[i][j][k][1] =(d%4)+4;//rotation}else {
                                levelStoragePush[i][j][k][1] = d%4;//rotation
                            }
                            if (i+horizontal!=-1&&i+horizontal!=getxTiles()&&j+vertical!=-1&&j+vertical!=getyTiles()) {//If we are not going out of bounds,
                                int [] tempe = {i,j,horizontal,vertical};
                                if(ifTileIsMoveableTo(tempe)) {

                                    int temp = getOpenIndex(i + horizontal, j + vertical, levelStoragePush);
                                    System.out.println(temp);
                                    while (temp == -1) {//this should never run more than once but its nice to have it freeze when something goes wrong
                                        levelStoragePush = expandZTile(i + horizontal, j + vertical, levelStoragePush);
                                        temp = getOpenIndex(i + horizontal, j + vertical, levelStoragePush);
                                        System.out.println(temp);
                                    }//above here is the code to find an open Z position. Working :)


                                    levelStoragePush[i + horizontal][j + vertical][temp][0] = levelStoragePush[i][j][k][0];//Copy ID                                levelStoragePush[i+1][j][temp][1]=3;
                                    levelStoragePush[i + horizontal][j + vertical][temp][1] = levelStoragePush[i][j][k][1];//rotation

                                    if (levelStoragePush[i][j][k][2] != 0) {
                                        levelStoragePush[i + horizontal][j + vertical][temp][2] = levelStoragePush[i][j][k][2] + 1;
                                    }//walkingcycle
                                    else {
                                        levelStoragePush[i + horizontal][j + vertical][temp][2] = 0;
                                    }
                                    levelStoragePush[i + horizontal][j + vertical][temp][3]++;//hasbeenmoved


                                    if (levelStoragePush[i + horizontal][j + vertical][temp][2] > 4) {
                                        levelStoragePush[i + horizontal][j + vertical][temp][2] = 1;
                                    }
                                    levelStoragePush[i][j][k][0] = 0;
                                    levelStoragePush[i][j][k][1] = 0;
                                    levelStoragePush[i][j][k][2] = 0;
                                    levelStoragePush[i][j][k][3] = 0;
                                    levelStoragePush[i][j][k][4] = 0;
                                    System.out.println(i + " " + j + " " + k);
                                    //babakey.removeImage(i,j,k); (This doesnt actually do anything)
                                }
                            }
                        }}
                }
            }
        }
        playGame();
    }
    public void playGame(){
        updateOrder();
        for (int i = 0; i < levelStoragePush.length; i++) {
            for (int j = 0; j < levelStoragePush[i].length; j++) {
                for (int k = 0; k < levelStoragePush[i][j].length ; k++) {
                    if (levelStoragePush[i][j][k][0]!=0||levelStoragePush[i][j][k]!=null){
                        levelStoragePush[i][j][k][3]=0;
                        levelStoragePush[i][j][k][4]=0;
                    }}}}
        newmemoryEater.push(newmemoryController.deepCopy(levelStoragePush));
        System.out.println(levelStoragePush[0][0][0][0]+"play");
        babakey.ParserDisplay();
        levelStoragePush= newmemoryController.deepCopy(newmemoryEater.peek());

    }
    //Controller of update order
    public void updateOrder(){
        //moveProperty();
        defeatProperty();
        winProperty();
        System.out.println(newmemoryEater.getSize()+"sizetotal");
        newmemoryEater.allOut00();
    }
    //Active properties (run on game cycle, IE shift&move, defeat& win, theyre all in UpdateOrder)
    public void moveProperty(){//Unused, probably, because there isnt any move in World one.
        int rotation = 0;
        int vertical=0;
        int horizontal=0;
        for (int i = 0; i < levelStoragePush.length; i++) {
            for (int j = 0; j < levelStoragePush[i].length; j++) {
                for (int k = 0; k < levelStoragePush[i][j].length ; k++) {
                    if (levelStoragePush[i][j][k][0]!=0) {

                        if((newmemoryEater.checkProperty(levelStoragePush[i][j][k][0],4)>0)&&levelStoragePush[i][j][k][4]<1){
                            rotation = levelStoragePush[i][j][k][1];
                            System.out.println(rotation+"rotation");
                            System.out.println(rotation+"rotation");
                            System.out.println(rotation+"rotation");
                            switch(rotation){
                                case 0:
                                case 4:
                                    vertical=1;
                                    break;
                                case 1:
                                case 5:
                                    horizontal=-1;
                                    break;
                                case 3:
                                case 7:
                                    horizontal=1;
                                    break;
                                case 2:
                                case 6:
                                    vertical=-1;
                                    break;
                            }
                            System.out.println(horizontal+"horizontal");
                            System.out.println(horizontal+"horizontal");
                            System.out.println(horizontal+"horizontal");
                            System.out.println(vertical+"vertical");
                            System.out.println(vertical+"vertical");
                            System.out.println(vertical+"vertical");

                            if (i+horizontal!=-1&&i+horizontal!=getxTiles()&&j+vertical!=-1&&j+vertical!=getyTiles()) {


                                int temp =getOpenIndex(i+horizontal,j+vertical,levelStoragePush);
                                System.out.println(temp);
                                while (temp==-1){//this should never run more than once but its nice to have it freeze when something goes wrong
                                    levelStoragePush=expandZTile(i+horizontal,j+vertical,levelStoragePush);
                                    temp = getOpenIndex(i+horizontal,j+vertical,levelStoragePush);
                                    System.out.println(temp);
                                }//above here is the code to find an open Z position. Working :)



                                levelStoragePush[i+horizontal][j+vertical][temp][0] = levelStoragePush[i][j][k][0];
                                levelStoragePush[i+horizontal][j+vertical][temp][1] =levelStoragePush[i][j][k][1];
                                levelStoragePush[i+horizontal][j+vertical][temp][2] = levelStoragePush[i][j][k][2] + 1;
                                levelStoragePush[i+horizontal][j+vertical][temp][4]++;
                                if (levelStoragePush[i+horizontal][j+vertical][temp][2] > 4) {
                                    levelStoragePush[i+horizontal][j+vertical][temp][2] = 1;
                                }


                                levelStoragePush[i][j][k][0] = 0;
                                levelStoragePush[i][j][k][1] = 0;
                                levelStoragePush[i][j][k][2] = 0;
                                levelStoragePush[i][j][k][3] = 0;
                                levelStoragePush[i][j][k][4] = 0;
                                System.out.println(i + " " + j + " " + k);
                                //babakey.removeImage(i, j, k);(Not doing anything)
                            }
                        }}
                }
            }
        }
    }
    public void defeatProperty(){}
    public void winProperty(){}
    //Reactive properties (stop, float, push, they are not in UpdateOrder)
    public boolean checkPushProperty(int[] prop){//Takes an x,y, horizontal, and vertical movement. Tries to move from that place. Returns true if and when the tile is moveable to.

        return true;
    }
    public boolean checkStopProperty(int[] coordinates){//takes an x,y, finds if anything there is stop. If it is, returns true.
        for (int i = 0; i < levelStoragePush[coordinates[0]][coordinates[1]].length; i++) {
            if (newmemoryEater.checkProperty(levelStoragePush[coordinates[0]][coordinates[1]][i][0],4)>0){
                return true;
            }//If trying to move into something that is stop, stop immediately.
        }
        return false;
    }
    //Misc
    public boolean ifTileIsMoveableTo(int[] properties){//Takes an x,y, horizontal and vertical movement. Tries to push from that place. Returns true if the tile is available to move to.
        //WE ASSUME THAT WE ARE NOT PUSHING ONTO BORDERS, at least for first check

        int[] temp = {properties[0]+properties[2],properties[1]+properties[3]};
        if (checkStopProperty(temp)){return false;}//If the tile you are moving into is stop, give up.
        return true;}
}
