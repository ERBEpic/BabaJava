import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class JPanelFrame extends JFrame implements KeyListener {
    private JFrame mainFrame;
    private JPanel contentPane;
    private JLabel imageLabel;
    private ImageIcon imageIcon;
    public void keyPressed(KeyEvent e) {
        int keyCode = e.getKeyCode();
        switch(keyCode) {
            case KeyEvent.VK_UP:
                System.out.println("Up arrow key pressed");
                Engine.BabaEngine.moveUp();
                break;
            case KeyEvent.VK_DOWN:
                System.out.println("Down arrow key pressed");
                Engine.BabaEngine.moveDown();
                break;
            case KeyEvent.VK_LEFT:
                System.out.println("Left arrow key pressed");
                Engine.BabaEngine.moveLeft();
                break;
            case KeyEvent.VK_RIGHT:
                System.out.println("Right arrow key pressed");
                Engine.BabaEngine.moveRight();
                break;
            case KeyEvent.VK_W:
                System.out.println("W key pressed");
                Engine.BabaEngine.moveUp();
                break;
            case KeyEvent.VK_S:
                System.out.println("S key pressed");
                Engine.BabaEngine.moveDown();
                break;
            case KeyEvent.VK_A:
                System.out.println("A key pressed");
                Engine.BabaEngine.moveLeft();
                break;
            case KeyEvent.VK_D:
                System.out.println("D key pressed");
                Engine.BabaEngine.moveRight();
                break;
            case KeyEvent.VK_SPACE:
                System.out.println("Spacebar Pressed");
                Engine.BabaEngine.moveWait();
                break;
            case KeyEvent.VK_R:
                System.out.println("R Pressed");
                Engine.BabaEngine.resetLevel();
                break;
            case KeyEvent.VK_Z:
                System.out.println("Z Pressed");
                Engine.BabaEngine.moveUndo();
                break;
        }//Isnt this a masterpiece of code right here. Truly a magnum opus of cleanliness
    }


    public void keyReleased(KeyEvent e) {
        // Do nothing
    }

    public void keyTyped(KeyEvent e) {
        // Do nothing
    }
    public JPanelFrame(){
        super("Baba is Me");
        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent windowEvent){
                System.exit(0);
            }
        });
        setLocationRelativeTo(null);
        setSize(Engine.BabaEngine.getxTiles(), Engine.BabaEngine.getyTiles());
        //mainFrame.setLayout(new GridLayout(Engine.BabaEngine.getxTiles()*24, Engine.BabaEngine.getyTiles()*24));
        addKeyListener(this);
        setFocusable(true);
        setFocusTraversalKeysEnabled(false);

        contentPane = new JPanel();
        contentPane.setLayout(new BorderLayout());
        setContentPane(contentPane);

        imageIcon = new ImageIcon();
        imageLabel = new JLabel();
        contentPane.add(imageLabel, BorderLayout.CENTER);
    }
    public void setScreenSize(){
        setSize(Engine.BabaEngine.getxTiles()*24, Engine.BabaEngine.getyTiles()*24);
        setLayout(new GridLayout(Engine.BabaEngine.getxTiles(), Engine.BabaEngine.getyTiles()));
    }
    public void setScreenSize(int x, int y){
        setSize(x, y);
        setLayout(new GridLayout(x*24, y*24));
    }



    public void showFrame(){
        setVisible(true);
    }

    public void Magenta(){
        setBackground(Color.MAGENTA);
    }
    public void setImage(String imagePath) {
        try {
            Image image = ImageIO.read(new File(imagePath));
            imageIcon.setImage(image);
            imageLabel.setIcon(imageIcon);
        } catch (IOException ex) {
            System.out.println("Could not load image");
        }
    }
    public void GREY(){
        contentPane.setBackground(Color.LIGHT_GRAY);
    }
}
