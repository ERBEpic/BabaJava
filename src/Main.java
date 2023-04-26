import java.awt.*;
import java.util.ArrayList;
import java.util.Collections;

//Display is multiples of 24
//Each tile is 24 wide
public class Main {
    public int Level = 0;//0 = level seelct
    public static void main(String[] args) throws InterruptedException {

        //JPanelFrame.showFrame();
        //Engine EngineG = new Engine();
        BabaFrame.babakey.showFrame();
        //babakey.setScreenSize(2000,2000);
        BabaFrame.babakey.setImage("C:/Users/Joespeh/IdeaProjects/Baba Is Me/Sprites/baba_0_1.png",2,4);
        BabaFrame.babakey.setBackground(Color.black);
        ArrayList<BabaObjects>[][] array1 = new ArrayList[Engine.BabaEngine.getxTiles()][Engine.BabaEngine.getyTiles()];
        array1[0][0] = new ArrayList<BabaObjects>();
        BabaObjects baba = new BabaObjects(3,3,3);
        array1[0][0].add(baba);

        array1[3][3] = new ArrayList<BabaObjects>();
        array1[3][3].add(baba);
        for (int i = 0; i < 5; i++) {
            BabaObjects newBabaObject = new BabaObjects(3,true,true);
            array1[5][i] = new ArrayList<BabaObjects>();
            if (i==3){newBabaObject.setYou(true);}
            array1[5][i].add(newBabaObject);
        }


        Engine.memoryEater.pushNewState(array1);
    GraphicsController temp = new GraphicsController();

        System.out.println(temp.FileFinder(3,2,2));

    }
}