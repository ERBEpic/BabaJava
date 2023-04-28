import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

//Talks between Engine and JPanelFrame
public class GraphicsController {
    private static int counter = 9234521;
    public GraphicsController() throws InterruptedException {
        while(true){
            counter++;
            if (counter>3){counter=1;}
            ParserDisplay();
            Thread.sleep(175);
        }
    }
    public static int getCounter(){
        return counter;
    }
    public void ParserDisplay() {
        BabaFrame.babakey.repaint();
        int[][][][] temp = Engine.memoryEater.pullLatestState();

        for (int i = 0; i < Engine.BabaEngine.getxTiles(); i++) {
            for (int j = 0; j < Engine.BabaEngine.getyTiles(); j++) {
                for (int k = 0; k < (Engine.memoryEater.getPointer()); k++) {
                        if (temp[i][j][k][0]!=0){
                    BabaFrame.babakey.setImage(FileFinder(temp[i][j][k]),i,j);

                    }
                }
            }
        }
    }

    public String FileFinder(int id, int rotation, int walking){//Four thigns matter. These 3, and counter.
        String first = null;
         int second;
         int third;
        switch (id){
            case 0: first = "algae";break;
            case 1: first = "arm";break;
            case 2: first = "arrow";break;
            case 3: first = "baba";break;
            case 4: first = "text_you";break;
            case 5: first = "wall";break;//This needs to be something special. Wall is WEIRD.
        }if (first==null){first="baba";}
        third = getCounter();
        if (rotation == 5){
            second = walking;
        }else {second = rotation*8+walking;
        }
        return "C:/Users/Joespeh/IdeaProjects/Baba Is Me/Sprites/"+first+'_'+second+'_'+third+".png";
    }
    public String FileFinder(int[] x){//Four thigns matter. These 3, and counter.
        int id = x[0];
        int rotation = x[1];
        int walkingcycle = x[2];
        String first = null;
        int second;
        int third;
        switch (id){
            case 0: first = "algae";break;
            case 1: first = "arm";break;
            case 2: first = "arrow";break;
            case 3: first = "baba";break;
            case 4: first = "text_you";break;
            case 5: first = "wall";break;//This needs to be something special. Wall is WEIRD.

        }
        third = GraphicsController.getCounter();
        if (rotation == 5){
            second = walkingcycle;
        }else {second = rotation*8+walkingcycle;
        }
        return "C:/Users/Joespeh/IdeaProjects/Baba Is Me/Sprites/"+first+'_'+second+'_'+third+".png";
    }
    }

