import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import java.util.LinkedList;
import java.util.ListIterator;

public class Baba3DFrame extends JFrame implements KeyListener {
    public static Baba3DFrame babakey;

    static {
        try {
            babakey = new Baba3DFrame();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public JPanel[][] contentPanels;
    public LinkedList[][] imageLabels;
    public LinkedList[][] imageIcons;
    public LinkedList[][] zPositions;
    private int cellSize = 24;

    public Baba3DFrame() throws IOException {
        super("Baba is Me!");
        Image image = ImageIO.read(new File("Sprites/baba_0_1.png"));
        setIconImage(image);
        setSize(Engine.getxTiles() * cellSize, Engine.getyTiles() * cellSize);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        contentPanels = new JPanel[Engine.getxTiles()][Engine.getyTiles()];
        imageLabels = new LinkedList[Engine.getxTiles()][Engine.getyTiles()];
        imageIcons = new LinkedList[Engine.getxTiles()][Engine.getyTiles()];
        zPositions = new LinkedList[Engine.getxTiles()][Engine.getyTiles()];

        Container pane = getContentPane();
        pane.setLayout(new GridLayout(Engine.getxTiles(), Engine.getyTiles()));

        for (int row = 0; row < Engine.getxTiles(); row++) {
            for (int col = 0; col < Engine.getyTiles(); col++) {
                contentPanels[row][col] = new JPanel();
                contentPanels[row][col].setLayout(new BorderLayout());
                contentPanels[row][col].setBackground(Color.black);

                imageLabels[row][col] = new LinkedList<JLabel>();
                imageIcons[row][col] = new LinkedList<ImageIcon>();
                zPositions[row][col] = new LinkedList<Integer>();

                pane.add(contentPanels[row][col]);
            }
        }

        addKeyListener(this);
        setFocusable(true);
        setFocusTraversalKeysEnabled(false);

        setVisible(true);
    }

    public void setImagetemp(String imagePath, int row, int col, int zPos) {
        try {//fixme This...doesnt actually stack images. zPos is just a priority system. Im gonna replace this with an actual thing that works
            Image image = ImageIO.read(new File(imagePath));
            ImageIcon imageIcon = new ImageIcon(image);
            JLabel imageLabel = new JLabel(imageIcon);

            LinkedList<JLabel> imageStack = imageLabels[row][col];

            if (imageStack.isEmpty()) {
                imageStack.addFirst(imageLabel);
            } else {
                ListIterator<JLabel> iterator = imageStack.listIterator();
                boolean replaced = false;
                while (iterator.hasNext()) {//if theres anything in the stack
                    JLabel currentLabel = iterator.next();
                    int currentZPos = (int) currentLabel.getClientProperty("zPos");

                    //replace whats at zposition if same
                    if (currentZPos == zPos) {
                        iterator.set(imageLabel);
                        replaced = true;
                        break;
                    }
                    // If the z-position of the current label is greater than the new z-position,
                    // insert the new label before the current label
                    else if (currentZPos > zPos) {
                        iterator.previous();
                        iterator.add(imageLabel);
                        replaced = true;
                        break;
                    }
                }

                // If the new label has not been replaced or inserted, add it to the end of the stack
                if (!replaced) {
                    imageStack.addLast(imageLabel);
                }
            }

            // Add the image label to the tile's content panel
            JPanel contentPanel = contentPanels[row][col];
            contentPanel.removeAll();
            for (JLabel label : imageStack) {
                contentPanel.add(label);
            }
            contentPanel.revalidate();
            contentPanel.repaint();

            // Set the z-position property of the image label
            imageLabel.putClientProperty("zPos", zPos);
        } catch (IOException ex) {
            System.out.println("Error loading image: " + imagePath);
        }
    }
    public void removeImage(int row, int col, int zPos) {
        try {
            Thread.sleep(175);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        // Get the linked list for the current tile
        LinkedList<JLabel> imageStack = imageLabels[row][col];
        // Iterate through the stack to find the image with the specified z-position
        ListIterator<JLabel> iterator = imageStack.listIterator();
        while (iterator.hasNext()) {//If theres anything left in the list
            JLabel currentLabel = iterator.next();
            int currentZPos = (int) currentLabel.getClientProperty("zPos");
            if (currentZPos == zPos) {
                // Remove the image label from the stack
                iterator.remove();
                // Remove the image label from the tile's content panel
                JPanel contentPanel = contentPanels[row][col];
                contentPanel.remove(currentLabel);
                contentPanel.revalidate();
                contentPanel.repaint();
                break;
            }
        }
    }





    public void clearTFrame() {
        repaint();
    }

    public void keyPressed(KeyEvent e) {
        int keyCode = e.getKeyCode();
        switch(keyCode) {
            case KeyEvent.VK_UP:
            case KeyEvent.VK_W:
                System.out.println("Up key pressed");
                Engine.moveYouUp();
                break;
            case KeyEvent.VK_DOWN:
            case KeyEvent.VK_S:
                System.out.println("Down key pressed");
                Engine.moveYouDown();
                break;
            case KeyEvent.VK_A:
            case KeyEvent.VK_LEFT:
                System.out.println("Left key pressed");
                Engine.moveYouLeft();
                break;
            case KeyEvent.VK_RIGHT:
            case KeyEvent.VK_D:
                System.out.println("Right key pressed");
                Engine.moveYouRight();
                break;
            case KeyEvent.VK_SPACE:
                System.out.println("Spacebar Pressed");
                Engine.BabaEngine.moveWait();
                Engine.setProperty(6,4,1);
                System.out.println("Keke is movE!");
                break;
            case KeyEvent.VK_R:
                System.out.println("R Pressed");
                Engine.BabaEngine.resetLevel();
                break;
            case KeyEvent.VK_Z:
                System.out.println("Z Pressed");
                Engine.BabaEngine.moveUndo();
                break;
            case KeyEvent.VK_O:
                System.out.println("O Pressed");
                Engine.memoryEater.allOut00();
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

    public void setCellBackground(int row, int col, Color backgroundColor, int zPos) {
        JPanel panel = contentPanels[row][col];
        panel.setBackground(backgroundColor);
        panel.setComponentZOrder(panel, zPos);
    }


}