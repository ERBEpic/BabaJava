import java.io.IOException;
import java.io.ObjectOutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Map;

/*
This class is responsible for holding the previous state from memoryController, whether that be the starting state or previously calculated state is irrelevant.
When it recieves an input, if it is a direct game input (IE up down left right wait), it moves (or doesnt) the player, and calculates one game cycle (if the player isnt dead) (Game cycle = playgame)
It then outputs that resulting new state back into memoryController, as a new state. The next game cycle works off that state just made.
It also directly talks with Babaframe, in add and remove images.
*/
public class Engine {
    public MemoryController newmemoryEater;
    public BabaFrame babakey;
    public int[][] propertiesStorageTemp;

    public Engine() throws IOException, ClassNotFoundException, InterruptedException {
        Thread.sleep(100);
        newmemoryEater = new MemoryController(this);//every engine NEEDS a memorycontroller
        //^^ also not every frame needs an engine so its changeable (this isnt just inconsistent coding)
        for (int i = 0; i < levelStoragePush.length; i++) {
            for (int j = 0; j < levelStoragePush[i].length; j++) {
                for (int k = 0; k < levelStoragePush[i][j].length ; k++) {
                    if (levelStoragePush[i][j][k][0]!=0||levelStoragePush[i][j][k]!=null){
                        levelStoragePush[i][j][k][3]=0;
                        levelStoragePush[i][j][k][4]=0;

                        //adding in here because its already a nested for loop the code to refresh the rules
                        if (levelStoragePush[i][j][k][0]==12){//Check around it for anything that makes a sentence, add that to properties storage, push yada yada
                            //NO TEXT STACKING, so I dont have to worry about multiple nouns or verb or both or some stacked.
                            int noun = 0;//if this isnt a noun :(
                            int verb = 0;
                            for (int l = 0; l < levelStoragePush[i-1][j].length; l++) {
                                if (levelStoragePush[i - 1][j][l][0] >= 21) {//21 is the start of object words/nouns
                                    noun = levelStoragePush[i - 1][j][l][0];
                                    l=102109;
                                }
                            }
                            for (int l = 0; l < levelStoragePush[i+1][j].length; l++) {
                                if (levelStoragePush[i+1][j][l][0] >=13&&levelStoragePush[i+1][j][l][0]<=20) {//12 is is, doesnt count. 20 is sink. 21 is wall.
                                    verb = levelStoragePush[i+1][j][l][0];
                                    l=102109;
                                }
                            }
                            try{setProperty(noun-20,verb-13,1);}catch (ArrayIndexOutOfBoundsException e){}//This is MEANT to fail. If it fails, it does NOT set the property.


                            noun = 0; verb = 0;

                            for (int l = 0; l < levelStoragePush[i][j-1].length; l++) {
                                if(levelStoragePush[i][j-1][l][0]>=21){
                                    noun=levelStoragePush[i][j-1][l][0];
                                    l=102109;
                                }
                            }
                            for (int l = 0; l < levelStoragePush[i][j+1].length; l++) {
                                if (levelStoragePush[i][j+1][l][0] >=13&&levelStoragePush[i][j+1][l][0]<=20) {//12 is is, doesnt count. 20 is sink. 21 is wall.
                                    verb = levelStoragePush[i][j+1][l][0];
                                    l=102109;
                                }
                            }
                            try{setProperty(noun-20,verb-13,1);}catch (Exception e){}
                        }

                    }}}}
        for (int i = 0; i < 20; i++) {
            setProperty(12+i,3,1);
        }

    }

    //it should basically never crash but its easier to solve the try catch problems here


    public int[][][][] levelStoragePush = new int[40][40][4][5];
    private int xTiles = 20;
    private int yTiles = 20;
    private static int level = 0;//this IS static, as the level should be global. Even if for some reason another instance of Engine is made, it should be synced up with this one.
    private static int currentlevel = 6;

    public int getxTiles(){
        return xTiles;
    }
    public int getyTiles(){
        return yTiles;
    }

    public void moveWait(){
        playGame();
    }

    public void moveUndoNew() {
        if (newmemoryEater.getSize() > 1) {
            newmemoryEater.pop();
            this.levelStoragePush = MemoryController.deepCopy(newmemoryEater.peek());
            newmemoryEater.popreties();
            propertiesStorage= MemoryController.deepCopy(newmemoryEater.peekreties());
        }else{
            resetLevel();
        }
        try {
            Protocol.messageSendingProtocolServer(1,levelStoragePush,0);
        } catch (IOException e) {}
    }

    public void resetLevel(){
        newmemoryEater.reset();
        this.levelStoragePush= newmemoryEater.getFirstState();

        try {
            Protocol.messageSendingProtocolServer(1,levelStoragePush,0);
        } catch (IOException e) {}
        System.out.println("Problem?");

    }

    public void moveToNextLevel(){
        level = currentlevel + 1;
        if(!Files.exists(Path.of("levels/level" + level + ".data"))) {//This is basically a try catch block but better
            System.out.println("That is the end of the tutorial. If you want to play more, go buy the actual game, called Baba Is You.");
            for (Map.Entry<Integer, ObjectOutputStream> entry : NetworkServer.clientsMap.entrySet()) {
                    Message message = new Message(-1,null,entry.getKey());
                try {
                    NetworkServer.clientsMap.get(entry.getKey()).writeObject(message);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }

            }
            System.exit(2);//2 for game ended.
        }
            propertiesStorage = new int[100][100];//Start it fresh
            newmemoryEater.newLevel(level);
            this.levelStoragePush = newmemoryEater.peek();
            currentlevel = level;
            newmemoryEater.pop();
            newmemoryEater.pop();//Cleanup!
        try {
            Protocol.messageSendingProtocolServer(1,levelStoragePush,0);
        } catch (IOException e) {}
    }



    private int getOpenIndex(int i, int j,int[][][][] maybe) {
        for (int k = 0; k < maybe[i][j].length; k++) {
            if (levelStoragePush[i][j][k][0] == 0) {
                return k;
            }
        }
        return -1; // No open index found
    }
    public static int[][][][] expandZTile(int x, int y, int[][][][] maybe){//I stole this code from LevelCreator so it should be identical to there
        int currentDepth = maybe[x][y].length;
        int[][] newArray = new int[currentDepth + 1][5];
        for (int z = 0; z < currentDepth; z++) {
            System.arraycopy(maybe[x][y][z], 0, newArray[z], 0, 5);
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
        boolean defeated = false;
        boolean won = false;
        boolean moved = false;
        for (int i = 0; i < levelStoragePush.length; i++) {
            for (int j = 0; j < levelStoragePush[i].length; j++) {
                for (int k = 0; k < levelStoragePush[i][j].length ; k++) {
                    if (levelStoragePush[i][j][k][0]!=0) {

                        if((this.checkProperty(levelStoragePush[i][j][k][0],0)>0)&&levelStoragePush[i][j][k][3]<1){//If the object is you, and the object has not been moved already

                            //Also, "Enhanced"(Intellijs name not mine) switch statement. Cool!
                            switch (d) {//This converts rotation from its udlr to H/V movements.
                                case 0, 4 -> vertical = 1;
                                case 1, 5 -> horizontal = -1;
                                case 3, 7 -> horizontal = 1;
                                case 2, 6 -> vertical = -1;
                            }

                            levelStoragePush[i][j][k][1] =(d%4);//rotation
                            if (i+horizontal!=-1&&i+horizontal!=getxTiles()&&j+vertical!=-1&&j+vertical!=getyTiles()) {//If we are not going out of bounds,

                                int [] tempe = {i,j,horizontal,vertical};
                                if(ifTileIsMoveableTo(tempe)) {
                                    moved=true;

                                    int temp = getOpenIndex(i + horizontal, j + vertical, levelStoragePush);
                                    while (temp == -1) {//this should never run more than once but its nice to have it freeze when something goes wrong
                                        expandZTile(i + horizontal, j + vertical, levelStoragePush);
                                        temp = getOpenIndex(i + horizontal, j + vertical, levelStoragePush);
                                    }//above here is the code to find an open Z position. Working :)

                                    levelStoragePush[i+horizontal][j+vertical][temp][0] = levelStoragePush[i][j][k][0];//Copy ID

                                    for (int l = 0; l < levelStoragePush[i+horizontal][j+vertical].length; l++) {
                                        //Check if ANYTHING is defeat. Including yourself.
                                        if(checkProperty(levelStoragePush[i+horizontal][j+vertical][l][0],2)>0){
                                            defeated=true;
                                        }//If something is defeated, dont move to the next tile, only remove from previous.
                                        if(checkProperty(levelStoragePush[i+horizontal][j+vertical][l][0],1)>0){
                                            won=true;
                                        }

                                    }


                                    if (!defeated) {
                                        levelStoragePush[i + horizontal][j + vertical][temp][1] = levelStoragePush[i][j][k][1];//rotation




                                        levelStoragePush[i + horizontal][j + vertical][temp][3]++;//hasbeenmoved

                                        levelStoragePush[i + horizontal][j + vertical][temp][2] = levelStoragePush[i][j][k][2] + 1;
                                        if (levelStoragePush[i + horizontal][j + vertical][temp][2] > 3) {//walkingcycle
                                            levelStoragePush[i + horizontal][j + vertical][temp][2] = 0;
                                        }

                                    }else{//if you HAVE been defeated
                                        levelStoragePush[i+horizontal][j+vertical][temp][0] = 0;
                                        //fix the ID, that was used to check defeat. It should be set to 0.
                                        won=false;
                                    }
                                    for (int l = 0; l < 5; l++) {//Delete old version of the object before it moves, as it just moved.
                                        levelStoragePush[i][j][k][l]=0;
                                    }
                                    defeated=false;

                                    if(won){
                                        moveToNextLevel();
                                    }
                                }
                            }
                        }}
                }

            }
        }
        if(moved) {//If you die, only need to undo once
            playGame();
        }
    }


    public void playGame(){
        //moveProperty(); Unused. Uncomment if updated+used
        propertiesStorageTemp = new int[100][100];
        for (int i = 0; i < levelStoragePush.length; i++) {
            for (int j = 0; j < levelStoragePush[i].length; j++) {
                for (int k = 0; k < levelStoragePush[i][j].length ; k++) {
                    if (levelStoragePush[i][j][k][0]!=0||levelStoragePush[i][j][k]!=null){
                        levelStoragePush[i][j][k][3]=0;
                        levelStoragePush[i][j][k][4]=0;
//CODE FOR SINK
                        if(checkProperty(levelStoragePush[i][j][k][0],7)>0&&levelStoragePush[i][j].length>1){//If something is sink, and theres other things on the tile
                            for (int l = 0; l < levelStoragePush[i][j].length; l++) {
                                levelStoragePush[i][j][l][0] = 0;
                                levelStoragePush[i][j][l][1] = 0;
                                levelStoragePush[i][j][l][2] = 0;
                                levelStoragePush[i][j][l][3] = 0;
                                levelStoragePush[i][j][l][4] = 0;
                            }
                        }
//CODE FOR HOT/MELT
                        if(checkProperty(levelStoragePush[i][j][k][0],5)>0){//If something is hot, look for other things that are melt (length>1 isnt needed, but saves processing time)
                            for (int l = 0; l < levelStoragePush[i][j].length; l++) {
                                if(checkProperty(levelStoragePush[i][j][l][0],6)>0){//If its melt, delete it
                                    levelStoragePush[i][j][l][0] = 0;
                                levelStoragePush[i][j][l][1] = 0;
                                levelStoragePush[i][j][l][2] = 0;
                                levelStoragePush[i][j][l][3] = 0;
                                levelStoragePush[i][j][l][4] = 0;
                            }}
                        }
//below here is code for re checking the rules of the game.
                        //adding in here because its already a nested for loop the code to refresh the rules
                        if (levelStoragePush[i][j][k][0]==12){//Check around it for anything that makes a sentence, add that to properties storage, push yada yada
                           //NO TEXT STACKING, so I dont have to worry about multiple nouns or verb or both or some stacked.
                            int noun = 0;//if this isnt a noun :(
                            int verb = 0;
                            try{for (int l = 0; l < levelStoragePush[i-1][j].length; l++) {
                                if (levelStoragePush[i - 1][j][l][0] >= 21) {//21 is the start of object words/nouns
                                    noun = levelStoragePush[i - 1][j][l][0];
                                    l=102109;
                                }
                            }}catch(Exception e){}

                            try{for (int l = 0; l < levelStoragePush[i+1][j].length; l++) {
                                if (levelStoragePush[i+1][j][l][0] >=13) {//12 is is, doesnt count. 20 is sink. 21 is wall. Why isnt wall ignored? Because we can simplify a bit, by making setProperty also transform
                                    verb = levelStoragePush[i+1][j][l][0];
                                    l=102109;
                                }
                            }}catch(Exception e){}
                            try{setProperty(noun-20,verb-13,1);}catch (Exception e){}


                            noun = 0; verb = 0;

                            try{for (int l = 0; l < levelStoragePush[i][j-1].length; l++) {
                                if(levelStoragePush[i][j-1][l][0]>=21){
                                    noun=levelStoragePush[i][j-1][l][0];
                                    l=102109;
                                }
                            }}catch(Exception e){}
                            try{for (int l = 0; l < levelStoragePush[i][j+1].length; l++) {
                                if (levelStoragePush[i][j+1][l][0] >=13) {
                                    verb = levelStoragePush[i][j+1][l][0];
                                    l=102109;
                                }
                            }}catch(Exception e){}
                            try{setProperty(noun-20,verb-13,1);}catch (Exception e){}
                        }
//above here
                    }}}}
        for (int i = 0; i < 20; i++) {
            setProperty(12+i,3,1);
        }
        propertiesStorage=propertiesStorageTemp;

        newmemoryEater.push(MemoryController.deepCopy(levelStoragePush));
        levelStoragePush= MemoryController.deepCopy(newmemoryEater.peek());
        //and AFTER everything is done

        newmemoryEater.pushreties(MemoryController.deepCopy(propertiesStorage));
        try {
            Protocol.messageSendingProtocolServer(1,levelStoragePush,0);
        } catch (IOException e) {}
    }


    public void moveProperty(){//Unused, because there isnt any move in World one.
        int rotation;
        int vertical=0;
        int horizontal=0;
        for (int i = 0; i < levelStoragePush.length; i++) {
            for (int j = 0; j < levelStoragePush[i].length; j++) {
                for (int k = 0; k < levelStoragePush[i][j].length ; k++) {
                    if (levelStoragePush[i][j][k][0]!=0) {

                        if((checkProperty(levelStoragePush[i][j][k][0],8)>0)&&levelStoragePush[i][j][k][4]<1){
                            rotation = levelStoragePush[i][j][k][1];

                            switch (rotation) {
                                case 0, 4 -> vertical = 1;
                                case 1, 5 -> horizontal = -1;
                                case 3, 7 -> horizontal = 1;
                                case 2, 6 -> vertical = -1;
                            }


                            if (i+horizontal!=-1&&i+horizontal!=getxTiles()&&j+vertical!=-1&&j+vertical!=getyTiles()) {
                                //Checkifsink

                                int temp =getOpenIndex(i+horizontal,j+vertical,levelStoragePush);
                                while (temp==-1){//this should never run more than once but its nice to have it freeze when something goes wrong
                                    expandZTile(i + horizontal, j + vertical, levelStoragePush);
                                    temp = getOpenIndex(i+horizontal,j+vertical,levelStoragePush);
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

                            }
                        }}
                }
            }
        }
    }

    public boolean checkPushProperty(int[] properties){
        //Takes an x,y, horizontal, and vertical movement. Tries to move from that place.
        // Returns true if and when the tile is moveable to.
        //This should run recursively in a recursive search.
        int x = properties[0];
        int y = properties[1];
        int horizontal = properties[2];
        int vertical = properties[3];
        boolean thereispush = false;
        int[] temp = {x+horizontal,y+vertical};
        if (x+horizontal!=-1&&x+horizontal!=getxTiles()&&y+vertical!=-1&&y+vertical!=getyTiles()){//If it goes out of bounds, go to else below, false.
        if (checkStopProperty(temp)){return false;}//Is there something that is stop?
            for (int i = 0; i < levelStoragePush[properties[0]+properties[2]][properties[1]+properties[3]].length; i++) {
            if(checkProperty(levelStoragePush[properties[0]+properties[2]][properties[1]+properties[3]][i][0],3)>0){
                thereispush=true;
                int[] temp2 = {x+horizontal,y+vertical,horizontal,vertical};
                if(!checkPushProperty(temp2)){return false;}//If false, push false all the way up out the chain
            }
        }
            //This cant be directly after ^^ because then when you push two stacked push objects, it would push one, and not the other

            if (!thereispush){//This is actually two things. Are there any objects that are push? and is there an open space?
                //^ reads "If there are no objects that are push". However, this is also not a reachable statement if the objects that are push cannot be pushed, as it would have returned false earlier on, at !checkpushproperty.
                return true;
            }else{
                //This is essentially "If ALL !checkPushProperty are true.
                //Now, we push everything that is push, as theyre all able to be pushed. We dont want to split a stack.

                for (int r = 0; r < levelStoragePush[x+horizontal][y+vertical].length; r++) {//Im using r because im reusing code that used i
                    if(checkProperty((levelStoragePush[x+horizontal][y+vertical][r][0]),3)>0){//^^If the thing is push, and only reachable if pushable.
                        //MOVE!
                        int temp3 = getOpenIndex(x + horizontal+horizontal, y + vertical+ vertical, levelStoragePush);//Im really creative when it comes to variable names
                        while (temp3 == -1) {//this should never run more than once but its nice to have it freeze when something goes wrong
                            expandZTile(x + horizontal+horizontal, y + vertical+ vertical, levelStoragePush);
                            temp3 = getOpenIndex(x+ horizontal+horizontal, y + vertical+ vertical, levelStoragePush);
                        }//above here is the code to find an open Z position. Working :)


                        levelStoragePush[x+horizontal+horizontal][y+vertical+ vertical][temp3][0] = levelStoragePush[x+horizontal][y+vertical][r][0];//Copy ID
                        levelStoragePush[x+horizontal+horizontal][y+vertical+ vertical][temp3][1] = levelStoragePush[x+horizontal][y+vertical][r][1];//rotation

                        if (levelStoragePush[x+horizontal][y+vertical][r][2] != 0) {
                            levelStoragePush[x + horizontal+horizontal][y + vertical+ vertical][temp3][2] = levelStoragePush[x+horizontal][y+vertical][r][2] + 1;
                            if(levelStoragePush[x+horizontal+horizontal][y+vertical+ vertical][temp3][2]==5){levelStoragePush[x+horizontal][x+vertical][temp3][2]=1;}
                        }//walkingcycle
                        else {
                            levelStoragePush[x + horizontal+horizontal][y + vertical+ vertical][temp3][2] = 0;
                        }
                        levelStoragePush[x + horizontal+horizontal][y + vertical+ vertical][temp3][3]++;//hasbeenmoved


                        if (levelStoragePush[x + horizontal+horizontal][y + vertical+ vertical][temp3][2] > 4) {
                            levelStoragePush[x + horizontal+horizontal][y + vertical+ vertical][temp3][2] = 1;
                        }
                        levelStoragePush[x+horizontal][y+vertical][r][0] = 0;
                        levelStoragePush[x+horizontal][y+vertical][r][1] = 0;
                        levelStoragePush[x+horizontal][y+vertical][r][2] = 0;
                        levelStoragePush[x+horizontal][y+vertical][r][3] = 0;
                        levelStoragePush[x+horizontal][y+vertical][r][4] = 0;

                        //done moving


                    }
                }
                return true;
            }

        }
        else{//This is
            return false;
        }
    }
    public boolean checkStopProperty(int[] coordinates){//takes an x,y, finds if anything there is stop. If it is, returns true.
        for (int i = 0; i < levelStoragePush[coordinates[0]][coordinates[1]].length; i++) {
            if (checkProperty(levelStoragePush[coordinates[0]][coordinates[1]][i][0],4)>0){
                return true;
            }//If trying to move into something that is stop, stop immediately.
        }
        return false;
    }
    //Misc
    public boolean ifTileIsMoveableTo(int[] properties){
        //Takes an x,y, horizontal and vertical movement. Tries to push from that place. Returns true if the tile is available to move to.
        //WE ASSUME THAT WE ARE NOT PUSHING ONTO BORDERS, at least for first check

        int[] temp = {properties[0]+properties[2],properties[1]+properties[3]};
        if (checkStopProperty(temp)){return false;}//If the tile you are moving into is stop, give up.
        //If the tile isnt stop...Is it push and pushable?
        return checkPushProperty(properties);
    }
    private int[][] propertiesStorage = new int[32][9];//Why is this an int? Because when move was properly implemented, you could do (noun A) is move, (noun A) is move, and it would move twice. Also, if i decided to go down the path of using a relative storage for rules, where each cycle checks the differences from the previous, it would be much easier to do it this way, as if you were to make two (noun) is (verb)s, and removed one, if it were a boolean, you would have to check every single tile all over again. Thinking about it now, its probably best that I went with integers, as if I went with booleans, i would have to check through the whole game board every time I break a sentence anyways.
    public void clearProperties(){
        propertiesStorage = new int[32][9];
    }
    public int checkProperty(int id, int property){
        return propertiesStorage[id][property];
    }
    public void setProperty(int id, int prop, int sign){
        if (prop>7){//If its not actually a property and instead is a noun->noun transformation
            for (int i = 0; i < levelStoragePush.length; i++) {
                for (int j = 0; j < levelStoragePush[i].length; j++) {
                    for (int k = 0; k < levelStoragePush[i][j].length ; k++) {
                        if (levelStoragePush[i][j][k][0]==id){levelStoragePush[i][j][k][0]=prop-7;}
                    }}}
        }else{
            if(propertiesStorageTemp==null){propertiesStorage[id][prop]=sign;}
            else{propertiesStorageTemp[id][prop]=sign;}//This feels like it should not be allowed
    }}
}
