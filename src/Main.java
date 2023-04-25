import javax.swing.*;
import java.awt.*;
import java.awt.*;
import java.awt.event.*;
import java.io.IOException;
import javax.swing.*;

import static java.lang.Thread.sleep;

//Display is multiples of 24
//Each tile is 24 wide
public class Main {
    public int Level = 0;//0 = level seelct
    public static void main(String[] args) throws InterruptedException, IOException {

        //JPanelFrame.showFrame();
        //Engine EngineG = new Engine();
        JPanelFrame.PanelFrame.showFrame();
        JPanelFrame.PanelFrame.setScreenSize(2000,2000);
        JPanelFrame.PanelFrame.displayImage(1,1,1);

    }
}