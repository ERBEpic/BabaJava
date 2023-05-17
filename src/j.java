import java.awt.*;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.*;

public class j extends JFrame {
    private int tileSize;
    private int numTilesX;
    private int numTilesY;
    private Image[][][] tileMap;

    public j(int tileSize, int numTilesX, int numTilesY) {
        this.setUndecorated(true);
        this.setBackground(new Color(0, 0, 0, 0)); // frame is transparent
        this.getRootPane().setOpaque(false); // frame content (rootpane) is transparent...
        this.setOpacity(0.0f); // or maybe this achieves the same thing
        this.tileSize = tileSize;
        this.numTilesX = numTilesX;
        this.numTilesY = numTilesY;
        this.tileMap = new Image[numTilesX][numTilesY][2];

        setSize(numTilesX * tileSize, numTilesY * tileSize);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
    }

    public void setImage(int x, int y, int z, Image image) {
        tileMap[x][y][z] = image;
        repaint();
    }

    public void paint(Graphics g) {
        for (int x = 0; x < numTilesX; x++) {
            for (int y = 0; y < numTilesY; y++) {
                Image bottomImage = tileMap[x][y][0];
                Image topImage = tileMap[x][y][1];
                if (bottomImage != null) {
                    g.drawImage(bottomImage, x * tileSize, y * tileSize, tileSize, tileSize, null);
                }
                if (topImage != null) {
                    g.drawImage(topImage, x * tileSize, y * tileSize, tileSize, tileSize, null);
                }
            }
        }
    }

    public static void main(String[] args) throws IOException {
        j grid = new j(24, 5, 5);
        grid.setImage(2, 2, 0, ImageIO.read(new File("Sprites/wall_9_2.png")));
        grid.setImage(1, 3, 0, ImageIO.read(new File("Sprites/wall_9_2.png")));
        grid.setImage(1, 2, 0, ImageIO.read(new File("Sprites/wall_9_2.png")));
        grid.setImage(1, 2, 1, ImageIO.read(new File("Sprites/algae_0_1.png")));

    }
}