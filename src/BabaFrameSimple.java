import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import javax.imageio.ImageIO;
import javax.swing.*;

public class BabaFrameSimple extends JFrame implements KeyListener {
    private Engine EngineReference;
    private int tileSize;
    private int walkingcycle = 0;
    private int numTilesX;
    private int numTilesY;
    private LinkedList<ImageLayer>[] tileMap;
    private Image offScreenImage;
    private Graphics offScreenGraphics;

    {
        try {
            walker();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void setEngine(Engine engine) {
        EngineReference = engine;
    }

    public BabaFrameSimple(int tileSize, int numTilesX, int numTilesY) throws IOException {
        this.tileSize = tileSize;
        this.numTilesX = numTilesX;
        this.numTilesY = numTilesY;
        this.tileMap = new LinkedList[numTilesX * numTilesY];
        for (int i = 0; i < numTilesX * numTilesY; i++) {
            tileMap[i] = new LinkedList<ImageLayer>();
        }
        setSize(numTilesX * tileSize, numTilesY * tileSize);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBackground(Color.BLACK);
        addKeyListener(this);
        setFocusable(true);
        setFocusTraversalKeysEnabled(false);
        setVisible(true);

        offScreenImage = createImage(getWidth(), getHeight());
        offScreenGraphics = offScreenImage.getGraphics();//No idea why this doesnt have to be called constantly. Guess its also a reference?
        counterUpdater counteryay = new counterUpdater();

    }

    public void addImage(int y, int x, Image image, int layer) {
        tileMap[x * numTilesX + y].add(new ImageLayer(image, layer));
        repaint();
    }

    public void removeImage(int y, int x, int layer) {
        LinkedList<ImageLayer> layers = tileMap[x * numTilesX + y];
        for (int i = 0; i < layers.size(); i++) {
            if (layers.get(i).layer == layer) {
                layers.remove(i);
                repaint();
                return;
            }
        }
    }

    public void clear() {
        for (int i = 0; i < numTilesX * numTilesY; i++) {
            tileMap[i].clear();
        }
        repaint();
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);
        this.walkingcycle++;

        offScreenGraphics.clearRect(0, 0, getWidth(), getHeight());

        for (int i = 0; i < numTilesX * numTilesY; i++) {
            int y = i % numTilesX;
            int x = i / numTilesX;
            LinkedList<ImageLayer> layers = new LinkedList<>(tileMap[i]);
            for (ImageLayer layer : layers) {
                offScreenGraphics.drawImage(layer.image, x * tileSize, y * tileSize, tileSize, tileSize, null);
            }
        }

        g.drawImage(offScreenImage, 0, 0, null);

    }

    @Override
    public void keyTyped(KeyEvent e) {
    }

    public void keyPressed(KeyEvent e) {
        int keyCode = e.getKeyCode();

        switch (keyCode) {
            case KeyEvent.VK_T:
                this.clear();
                break;
            case KeyEvent.VK_I:
                this.sdisplay();
                break;
            case KeyEvent.VK_UP:
                EngineReference.moveYouUp();
                break;
            case KeyEvent.VK_DOWN:
                EngineReference.moveYouDown();
                break;
            case KeyEvent.VK_LEFT:
                EngineReference.moveYouLeft();
                break;
            case KeyEvent.VK_RIGHT:
                EngineReference.moveYouRight();
                break;
            case KeyEvent.VK_SPACE:
                EngineReference.moveWait();
                EngineReference.setProperty(6, 4, 1);
                System.out.println("Keke is movE!");
                break;
            case KeyEvent.VK_R:
                System.out.println("R Pressed");
                EngineReference.resetLevel();
                break;
            case KeyEvent.VK_Z:
                System.out.println("Z Pressed");
                EngineReference.moveUndoNew();
                break;
            case KeyEvent.VK_O:
                System.out.println("O Pressed");
                break;
            case KeyEvent.VK_B:
                System.out.println("B Pressed");
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
    }

    public void sdisplay() {
        display = !display;
    }

    public int getCounter() {
        return counter;
    }

    public boolean display() {
        return display;
    }

    public void walker() throws InterruptedException, IOException {
        Thread.sleep(175);
        counter++;
        if (counter > 3) {
            counter = 1;
        }
    }

    public void ParserDisplay() throws IOException {
        clear();
        int[][][][] temp = EngineReference.newmemoryEater.peek();
        if (temp != null && EngineReference != null) {
            for (int i = 0; i < temp.length; i++) {
                for (int j = 0; j < temp[i].length; j++) {
                    for (int k = 0; k < temp[i][j].length; k++) {
                        if (temp[i][j][k][0] != 0 && display) {
                            addImage(i, j, ImageIO.read(new File(FileFinder(temp[i][j][k]))), k);
                        }
                    }
                }
            }
        }
    }

    public String FileFinder(int[] x) {
        int id = x[0];
        int rotation = x[1];
        int walkingcycle = x[2];
        String first = null;
        int second;
        int third;
        switch (id) {
            case 0:
                first = "algae";
                break;
            case 1:
                first = "arm";
                break;
            case 2:
                first = "arrow";
                break;
            case 3:
                first = "baba";
                break;
            case 4:
                first = "text_you";
                break;
            case 5:
                first = "wall";
                break;
            case 6:
                first = "keke";
                break;
        }
        third = counter;
        if (rotation == 5) {
            second = walkingcycle;
        } else {
            second = rotation * 8 + walkingcycle;
        }

        return "Sprites/" + first + '_' + second + '_' + third + ".png";
    }
    public int counter =1;
    public boolean display = true;






    public class counterUpdater {
        private ScheduledExecutorService executor;

        public counterUpdater() {
            executor = Executors.newSingleThreadScheduledExecutor();
            scheduleTask();
        }

        private void scheduleTask() {
            executor.scheduleAtFixedRate(new running(), 0, 175, TimeUnit.MILLISECONDS);
        }

        private class running implements Runnable {
            @Override
            public void run() {
                counter++;
                if (counter > 3) {
                    counter = 1;
                }
                try {
                    ParserDisplay();
                } catch (IOException e) {
                }
                repaint();
            }
        }

    }
}