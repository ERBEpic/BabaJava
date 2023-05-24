import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.LinkedList;
import javax.imageio.ImageIO;
import javax.swing.*;

public class BabaFrameSimple extends JFrame implements KeyListener {

    private int tileSize;
    private int walkingcycle = 0;
    private int numTilesX;
    private int numTilesY;
    private LinkedList<ImageLayer>[] tileMap;

    public BabaFrameSimple(int tileSize, int numTilesX, int numTilesY) {
        //this.setUndecorated(true);
        //this.setBackground(new Color(182, 42, 42)); // frame is transparent
        this.getRootPane().setOpaque(true); // frame content (rootpane) is transparent...
        //this.setOpacity(0.0f); // or maybe this achieves the same thing
        this.tileSize = tileSize;
        this.numTilesX = numTilesX;
        this.numTilesY = numTilesY;
        this.tileMap = new LinkedList[numTilesX * numTilesY];
        for (int i = 0; i < numTilesX * numTilesY; i++) {
            tileMap[i] = new LinkedList<ImageLayer>();
        }
        setSize(numTilesX * tileSize, numTilesY * tileSize);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.getContentPane().setBackground(Color.BLACK);
        addKeyListener(this);
        setFocusable(true);
        setFocusTraversalKeysEnabled(false);
        setVisible(true);

    }

    public void addImage(int x, int y, Image image, int layer) {
        tileMap[y * numTilesX + x].add(new ImageLayer(image, layer));
        repaint();
    }
    /*public void addImage(int x, int y, String name, int layer) {
        String[] splitted = new String[2];
        splitted =name.split("_");
        tileMap[y * numTilesX + x].add(new ImageLayer(image, layer));
        repaint();
    }*/

    public void removeImage(int x, int y, int layer) {
        LinkedList<ImageLayer> layers = tileMap[y * numTilesX + x];
        for (int i = 0; i < layers.size(); i++) {
            if (layers.get(i).layer == layer) {
                layers.remove(i);
                repaint();
                return;
            }
        }
    }
    public void clear(){
        Arrays.stream(tileMap).forEach(x->x.remove());
    }

    public void paint(Graphics g) {
        super.paint(g);
        this.walkingcycle++;
        for (int i = 0; i < numTilesX * numTilesY; i++) {
            int x = i % numTilesX;
            int y = i / numTilesX;
            LinkedList<ImageLayer> layers = new LinkedList<>(tileMap[i]); // Create a copy of the LinkedList
            for (ImageLayer layer : layers) {

                g.drawImage(layer.image, x * tileSize, y * tileSize, tileSize, tileSize, null);
            }
        }
    }

    @Override
    public void keyTyped(KeyEvent e) {

    }


    public void keyPressed(KeyEvent e) {
        int keyCode = e.getKeyCode();

        switch (keyCode) {
            case KeyEvent.VK_I:
                GraphicsController.sdisplay();
                break;
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

    @Override
    public void keyReleased(KeyEvent e) {

    }


    private class ImageLayer {
        public Image image;
        public int layer;
        public ImageLayer(Image image, int layer) {
            this.image = image;
            this.layer = layer;
        }
    }

    public static void main(String[] args) throws IOException {
        BabaFrameSimple grid = new BabaFrameSimple(24, 21, 21);
        grid.addImage(2, 2, ImageIO.read(new File("Sprites/wall_9_2.png")), 0);
        grid.addImage(1, 3, ImageIO.read(new File("Sprites/wall_9_2.png")), 0);
        grid.addImage(1, 2, ImageIO.read(new File("Sprites/wall_9_2.png")), 0);
        grid.addImage(1, 2, ImageIO.read(new File("Sprites/keke_0_1.png")), 100);

    }
}