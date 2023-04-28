import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

public class BabaFrame extends JFrame implements KeyListener {
    public static BabaFrame babakey;

    static {
        try {
            babakey = new BabaFrame();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public JPanel[][] contentPanels;
    public JLabel[][] imageLabels;
    public ImageIcon[][] imageIcons;
    private int cellSize = 24;

    public BabaFrame() throws IOException {
        super("Baba is Me!");
        Image image = ImageIO.read(new File("C:/Users/Joespeh/IdeaProjects/Baba Is Me/Sprites/baba_0_1.png"));
        setIconImage(image);
        setSize(Engine.BabaEngine.getxTiles()* cellSize, Engine.BabaEngine.getyTiles() * cellSize);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        contentPanels = new JPanel[Engine.BabaEngine.getxTiles()][Engine.BabaEngine.getyTiles()];
        imageLabels = new JLabel[Engine.BabaEngine.getxTiles()][Engine.BabaEngine.getyTiles()];
        imageIcons = new ImageIcon[Engine.BabaEngine.getxTiles()][Engine.BabaEngine.getyTiles()];

        Container pane = getContentPane();
        pane.setLayout(new GridLayout(Engine.BabaEngine.getxTiles(), Engine.BabaEngine.getyTiles()));

        for (int row = 0; row < Engine.BabaEngine.getxTiles(); row++) {
            for (int col = 0; col < Engine.BabaEngine.getyTiles(); col++) {
                contentPanels[row][col] = new JPanel();
                contentPanels[row][col].setLayout(new BorderLayout());
                contentPanels[row][col].setBackground(Color.black);

                imageIcons[row][col] = new ImageIcon();
                imageLabels[row][col] = new JLabel();
                contentPanels[row][col].add(imageLabels[row][col], BorderLayout.CENTER);

                pane.add(contentPanels[row][col]);
            }
        }

        addKeyListener(this);
        setFocusable(true);
        setFocusTraversalKeysEnabled(false);

        setVisible(true);
    }


    public void setImage(String imagePath, int row, int col) {
        try {
            Image image = ImageIO.read(new File(imagePath));
            imageIcons[row][col].setImage(image);
            imageLabels[row][col].setIcon(imageIcons[row][col]);
        } catch (IOException ex) {
            System.out.println("uh oh");
        }

    }
    public void clearTFrame() {
    repaint();
    }


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
            case KeyEvent.VK_D:
                System.out.println("Right key pressed");
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

    public void showFrame() {
        setVisible(true);
    }

    public void hideFrame() {
        setVisible(false);
    }

    public void setCellBackground(int row, int col, Color backgroundColor) {
        contentPanels[col][row].setBackground(backgroundColor);
    }

    public static void main(String[] args) throws IOException {
        BabaFrame keyListener = new BabaFrame();
        keyListener.showFrame();
    }
}
