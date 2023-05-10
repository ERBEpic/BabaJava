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
        Baba3DFrame.babakey.showFrame();
        //babakey.setScreenSize(2000,2000);
        Baba3DFrame.babakey.setBackground(Color.black);


        int[][][][] arraynew = new int[40][40][6][6];

        for (int i = 0; i < 2; i++) {
            arraynew[5][4][1][i] = 3;
        }
        arraynew[10][11][1][0]=6;

        GraphicsController temp = new GraphicsController();





    }
}