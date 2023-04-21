import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class JPanelFrame implements KeyListener {
    public static JPanelFrame PanelFrame = new JPanelFrame();
    public void keyPressed(KeyEvent e) {
        int keyCode = e.getKeyCode();
        switch(keyCode) {
            case KeyEvent.VK_UP:
                System.out.println("Up arrow key pressed");
                Magenta();
                break;
            case KeyEvent.VK_DOWN:
                System.out.println("Down arrow key pressed");
                GREY();
                break;
            case KeyEvent.VK_LEFT:
                System.out.println("Left arrow key pressed");
                break;
            case KeyEvent.VK_RIGHT:
                System.out.println("Right arrow key pressed");
                break;
        }
    }


    public void keyReleased(KeyEvent e) {
        // Do nothing
    }

    public void keyTyped(KeyEvent e) {
        // Do nothing
    }
    public JPanelFrame(){
        mainFrame = new JFrame("Baba is Me");
        mainFrame.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent windowEvent){
                System.exit(0);
            }
        });
        mainFrame.setSize(Engine.BabaEngine.getxTiles(), Engine.BabaEngine.getyTiles());
        mainFrame.setLayout(new GridLayout(Engine.BabaEngine.getxTiles()*24, Engine.BabaEngine.getyTiles()*24));
        mainFrame.addKeyListener(this);
        mainFrame.setFocusable(true);
        mainFrame.setFocusTraversalKeysEnabled(false);
        contentPane = new JPanel();
        mainFrame.setContentPane(contentPane);
    }
    public void setScreenSize(){
        mainFrame.setSize(Engine.BabaEngine.getxTiles(), Engine.BabaEngine.getyTiles());
        mainFrame.setLayout(new GridLayout(Engine.BabaEngine.getxTiles()*24, Engine.BabaEngine.getyTiles()*24));
    }
    public void setScreenSize(int x, int y){
        mainFrame.setSize(x, y);
        mainFrame.setLayout(new GridLayout(x*24, y*24));
    }
    private JFrame mainFrame;
    private JLabel headerLabel;
    private JLabel statusLabel;
    private JPanel controlPanel;
    private JPanel contentPane;
    private JLabel msglabel;

    public void showFrame(){
        mainFrame.setVisible(true);
    }

    public void Magenta(){
        contentPane.setBackground(Color.MAGENTA);
    }
    public void GREY(){
        contentPane.setBackground(Color.LIGHT_GRAY);
    }
}
