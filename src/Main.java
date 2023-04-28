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


        int[][][][] arraynew = new int[40][40][3][3];

        for (int i = 0; i < 2; i++) {
            arraynew[3][4][1][i] = 3;
        }

        Engine.memoryEater.pushNewState(arraynew);
    GraphicsController temp = new GraphicsController();

        System.out.println(temp.FileFinder(3,2,2));
        Engine.levelStoragePush=Engine.memoryEater.pullLatestState().clone();
        Engine.BabaEngine.playGame();

    }
}