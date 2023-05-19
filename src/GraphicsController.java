import javax.imageio.ImageIO;
import java.awt.*;
import java.io.File;
import java.io.IOException;

//Talks between Engine and JPanelFrame
public class GraphicsController {//todo i need to color objects (keke is not white), and it has to happen here.
    private static int counter = 9234521;
    public GraphicsController() throws InterruptedException, IOException {
            walker();
    }
    public void walker() throws InterruptedException, IOException {
        while(true){
            Thread.sleep(175);
            counter++;
            if (counter>3){counter=1;}
            ParserDisplay();
        }
    }
    public static int getCounter(){
        return counter;
    }
    public void ParserDisplay() throws IOException {
        int[][][][] temp = Engine.newmemoryEater.peek();
        if (temp!=null){//To solve NullPointerExceptions. Cheaper(runtime wise) try/catch
        for (int i = 0; i < temp.length; i++) {
            for (int j = 0; j < temp[i].length; j++) {
                for (int k = 0; k < temp[i][j].length; k++) {
                        if (temp[i][j][k][0]!=0){
                            Baba3DFrame.babakey.addImage(i,j,ImageIO.read(new File(FileFinder(temp[i][j][k]))),k);
                    }
                }
            }
        }
    }}


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
            case 6: first = "keke";break;
        }
        third = GraphicsController.getCounter();
        if (rotation == 5){
            second = walkingcycle;
        }else {second = rotation*8+walkingcycle;
        }
        return "Sprites/"+first+'_'+second+'_'+third+".png";
    }
    }

