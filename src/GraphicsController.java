import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

//Talks between Engine and JPanelFrame
public class GraphicsController {
    private static int counter = 1;
    public GraphicsController() throws InterruptedException {
        ParserDisplay();
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
    public void ParserDisplay(){
        BabaFrame.babakey.repaint();
        ArrayList<Integer>[][] temp = Engine.memoryEater.pullLatestState();

        for (int i = 0; i < Engine.BabaEngine.getxTiles(); i++) {
            for (int j = 0; j < Engine.BabaEngine.getyTiles()-1; j++) {
                if (temp[i][j]!=null){
                    for (int k = 0; k < ((temp[i][j].size())/3); k++) {
                    BabaFrame.babakey.setImage(FileFinder(temp[i][j].get(k*3+0),temp[i][j].get(k*3+1),temp[i][j].get(k*3+2)),i,j);
                    }}
            }
        }

    }
    private String first;
    private int second;
    private int third;
    public String FileFinder(int id, int rotation, int walking){//Four thigns matter. These 3, and counter.
        switch (id){
            case 0: first = "algae";break;
            case 1: first = "arm";break;
            case 2: first = "arrow";break;
            case 3: first = "baba";break;
            case 4: first = "text_you";break;
            case 5: first = "wall";break;//This needs to be something special. Wall is WEIRD.

        }
        third = getCounter();
        if (rotation == 5){
            second = walking;
        }else {second = rotation*8+walking;
        }
        return "C:/Users/Joespeh/IdeaProjects/Baba Is Me/Sprites/"+first+'_'+second+'_'+third+".png";
    }

    }

