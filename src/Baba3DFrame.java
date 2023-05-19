import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.File;
import java.io.IOException;
import java.util.LinkedList;
import java.util.ListIterator;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class Baba3DFrame extends JFrame implements KeyListener {

    public static BabaFrameSimple babakey;

    static {
        babakey = new BabaFrameSimple(24,41,41);
    }
    private int cellSize = 24;

    private JPanel[][] contentPanels;
    private LinkedList<JLabel>[][] imageLabels;
    private LinkedList<ImageIcon>[][] imageIcons;
    private LinkedList<Integer>[][] zPositions;

    public Baba3DFrame() {
        try {
            Image image = ImageIO.read(new File("Sprites/baba_0_1.png"));
            setIconImage(image);
        } catch (IOException e) {
            e.printStackTrace();
        }

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
        LinkedList<JLabel> imageStack = null;
        try {
            Image image = ImageIO.read(new File(imagePath));
            ImageIcon imageIcon = new ImageIcon(image);
            JLabel imageLabel = new JLabel(imageIcon);
            imageStack = imageLabels[row][col];
            if (imageStack.isEmpty()) {
                imageStack.addFirst(imageLabel);
            } else {
                ListIterator<JLabel> iterator = imageStack.listIterator();
                boolean replaced = false;
                while (iterator.hasNext()) {
                    JLabel currentLabel = iterator.next();
                    int currentZPos = (int) currentLabel.getClientProperty("zPos");
                    if (currentZPos == zPos) {
                        iterator.set(imageLabel);
                        replaced = true;
                        break;
                    }
                }
                if (!replaced) {
                    imageStack.addFirst(imageLabel);
                }
            }
            imageLabel.putClientProperty("zPos", zPos);
            contentPanels[row][col].removeAll();
            ListIterator<JLabel> iterator = imageStack.listIterator(imageStack.size());
            while (iterator.hasPrevious()) {
                JLabel currentLabel = iterator.previous();
                contentPanels[row][col].add(currentLabel);
            }
            contentPanels[row][col].revalidate();
            contentPanels[row][col].repaint();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (NullPointerException e) {
            // Handle null pointer exception here
            System.out.println("Caught NullPointerException: " + e.getMessage());
            JLabel currentLabel = new JLabel();
            contentPanels[row][col].removeAll();
            ListIterator<JLabel> iterator = imageStack.listIterator(imageStack.size());
            while (iterator.hasPrevious()) {
                currentLabel = iterator.previous();
                if (currentLabel != null) {
                    contentPanels[row][col].add(currentLabel);
                }
            }
            contentPanels[row][col].revalidate();
            contentPanels[row][col].repaint();
        }
    }    public void removeImage(int row, int col, int zPos) {
        try {
            Thread.sleep(175);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        // Get the linked list for the current tile
        LinkedList<JLabel> imageStack = imageLabels[row][col];

        // Iterate through the stack to find the image with the specified z-position
        ListIterator<JLabel> iterator = imageStack.listIterator();
        while (iterator.hasNext()) {
            JLabel currentLabel = iterator.next();
            int currentZPos = (int) currentLabel.getClientProperty("zPos");
            if (currentZPos == zPos) {
                // Remove the image label from the stack
                iterator.remove();

                // Repaint the tile's content panel after removing the image label
                JPanel contentPanel = contentPanels[row][col];
                contentPanel.removeAll();
                for (JLabel label : imageStack) {
                    contentPanel.add(label);
                }
                contentPanel.revalidate();
                contentPanel.repaint();

                break;
            }
        }
    }
    public void showFrame() {
        setVisible(true);
    }

    public void removeallImage() {
        try {
            Thread.sleep(175);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        // Iterate through all tiles and their image stacks
        for (int row = 0; row < imageLabels.length; row++) {
            for (int col = 0; col < imageLabels[row].length; col++) {
                LinkedList<JLabel> imageStack = imageLabels[row][col];

                // Iterate through the stack to remove all image labels
                while (!imageStack.isEmpty()) {
                    JLabel currentLabel = imageStack.getLast();

                    // Remove the image label from the stack
                    imageStack.removeLast();

                    // Remove the image label from the tile's content panel
                    JPanel contentPanel = contentPanels[row][col];
                    contentPanel.remove(currentLabel);
                }

                // Repaint the tile's content panel after removing all image labels
                JPanel contentPanel = contentPanels[row][col];
                contentPanel.revalidate();
                contentPanel.repaint();
            }
        }
    }

    public void keyReleased(KeyEvent e) {
        // Do nothing
    }

    public void keyTyped(KeyEvent e) {
        // Do nothing
    }

    public void keyPressed(KeyEvent e) {
        int keyCode = e.getKeyCode();

        switch (keyCode) {
            case KeyEvent.VK_UP:
                Engine.moveYouUp();
                break;
            case KeyEvent.VK_DOWN:
                Engine.moveYouDown();
                break;
            case KeyEvent.VK_LEFT:
                Engine.moveYouLeft();
                break;
            case KeyEvent.VK_RIGHT:
                Engine.moveYouRight();
                break;
            case KeyEvent.VK_SPACE:
                Engine.BabaEngine.moveWait();
                Engine.setProperty(6, 4, 1);
                System.out.println("Keke is movE!");
                break;
            case KeyEvent.VK_R:
                System.out.println("R Pressed");
                Engine.resetLevel();
                break;
            case KeyEvent.VK_Z:
                System.out.println("Z Pressed");
                Engine.BabaEngine.moveUndoNew();
                break;
            case KeyEvent.VK_O:
                System.out.println("O Pressed");
                Engine.newmemoryEater.allOut00();
                break;
            case KeyEvent.VK_B:
                System.out.println("B Pressed");
                Engine.newmemoryEater.allOut00();
                break;
        }
    }

    public static void main(String[] args) throws IOException {
       // babakey.setImagetemp("Sprites/wall_9_2.png",1,2,0);
       // babakey.setImagetemp("Sprites/baba_0_1.png",1,2,1);

    }
}