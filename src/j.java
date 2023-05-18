import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.util.LinkedList;
import javax.imageio.ImageIO;
import javax.swing.*;

public class j extends JFrame {

    private int tileSize;
    private int numTilesX;
    private int numTilesY;
    private LinkedList<ImageLayer>[] tileMap;

    public j(int tileSize, int numTilesX, int numTilesY) {
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
        setVisible(true);

    }

    public void addImage(int x, int y, Image image, int layer) {
        tileMap[y * numTilesX + x].add(new ImageLayer(image, layer));
        repaint();
    }

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

    public void paint(Graphics g) {
        super.paint(g);
        for (int i = 0; i < numTilesX * numTilesY; i++) {
            int x = i % numTilesX;
            int y = i / numTilesX;
            LinkedList<ImageLayer> layers = tileMap[i];
            for (ImageLayer layer : layers) {
                g.drawImage(layer.image, x * tileSize, y * tileSize, tileSize, tileSize, null);
            }
        }
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
        j grid = new j(24, 21, 21);
        grid.addImage(2, 2, ImageIO.read(new File("Sprites/wall_9_2.png")), 0);
        grid.addImage(1, 3, ImageIO.read(new File("Sprites/wall_9_2.png")), 0);
        grid.addImage(1, 2, ImageIO.read(new File("Sprites/wall_9_2.png")), 0);
        grid.addImage(1, 2, ImageIO.read(new File("Sprites/algae_0_1.png")), 1);
        grid.addImage(1, 2, ImageIO.read(new File("Sprites/algae_0_1.png")), 4);

    }
}