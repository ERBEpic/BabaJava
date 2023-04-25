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
        ArrayList<Integer>[][] array1 = new ArrayList[Engine.BabaEngine.getxTiles()][Engine.BabaEngine.getyTiles()];
        array1[0][0] = new ArrayList<Integer>();
        array1[0][0].add(3);
        array1[0][0].add(2);
        array1[0][0].add(3);
        array1[3][3] = new ArrayList<Integer>();

        Collections.addAll(array1[3][3],5,5,0);


        Engine.memoryEater.pushNewState(array1);
    GraphicsController temp = new GraphicsController();

        System.out.println(temp.FileFinder(3,2,2));

    }
}