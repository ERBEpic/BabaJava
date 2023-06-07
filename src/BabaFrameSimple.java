import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.File;
import java.io.IOException;
import java.util.LinkedList;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import javax.imageio.ImageIO;
import javax.swing.*;
/*
This class is responsible for the Frame which is actually displayed to the player.
Its responsible for taking the inputs, and passing them through methods to Engine
Its responsible for displaying the output to the player through a JFrame.
Engine tells it, through add/remove image,
 */
public class BabaFrameSimple extends JFrame implements KeyListener {
    private int delay = 1;
    private Engine EngineReference;
    private int tileSize;
    private int walkingcycle = 0;
    private int numTilesX;
    private int numTilesY;
    private LinkedList<ImageLayer>[] tileMap;
    private Image offScreenImage;
    private Graphics offScreenGraphics;
    private boolean seizure = false;



    public void setEngine(Engine engine) {
        EngineReference = engine;
    }

    public BabaFrameSimple(int numTilesX, int numTilesY) throws IOException {
        super("Baba is Me!");
        Image image = ImageIO.read(new File("Sprites/baba_0_1.png"));
        setIconImage(image);
        this.tileSize = 24;
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
        setUndecorated(true);
        setFocusTraversalKeysEnabled(false);
        setIgnoreRepaint(true);
        setVisible(true);

        offScreenImage = createImage(getWidth(), getHeight());
        offScreenGraphics = offScreenImage.getGraphics();



    }

    public void addImage(int y, int x, Image image, int layer) {
        tileMap[x * numTilesX + y].add(new ImageLayer(image, layer));
        //repaint();//Why...Did i ever have this here? Its no wonder the frame used to flicker
    }



    public void clear() {
        for (int i = 0; i < tileMap.length; i++) {
            tileMap[i].clear();
        }
        ParserDisplay();
    }
    @Override
    public void paint(Graphics g) {
        offScreenGraphics.fillRect(0, 0, getWidth(), getHeight());

        this.walkingcycle++;

        for (int i = 0; i < numTilesX * numTilesY; i++) {
            int y = i % numTilesX;
            int x = i / numTilesX;
            LinkedList<ImageLayer> layers;
            try {
                layers = new LinkedList<>(tileMap[i]);
            } catch (ArrayIndexOutOfBoundsException e) {
                layers = null;
            }
            if (layers != null) {
                for (ImageLayer layer : layers) {//For every layer in layers
                    if (layer != null) {
                        offScreenGraphics.drawImage(layer.image, x * tileSize, y * tileSize, tileSize, tileSize, null);
                    }

                }
            }
        }
        try {
            Thread.sleep(10);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        g.drawImage(offScreenImage, 0, 0, null);
    }

    @Override
    public void keyTyped(KeyEvent e) {
    }

    public void keyPressed(KeyEvent e) {
        int keyCode = e.getKeyCode();

        switch (keyCode) {
            case KeyEvent.VK_ESCAPE:
                System.exit(0);//this is what ExitOnClose calls, so it works great
                break;
            case KeyEvent.VK_UP:
            case KeyEvent.VK_W:
                EngineReference.youProperty(1);
                break;
            case KeyEvent.VK_DOWN:
            case KeyEvent.VK_S:
                EngineReference.youProperty(3);
                break;
            case KeyEvent.VK_LEFT:
            case KeyEvent.VK_A:
                //EngineReference.moveYouLeft();
                EngineReference.youProperty(2);
                break;
            case KeyEvent.VK_RIGHT:
            case KeyEvent.VK_D:
                //EngineReference.moveYouRight();
                EngineReference.youProperty(0);
                break;
            case KeyEvent.VK_SPACE:
                EngineReference.moveWait();
                break;
            case KeyEvent.VK_R:
                EngineReference.resetLevel();
                break;
            case KeyEvent.VK_Z:
                EngineReference.moveUndoNew();
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



    public void sdisplay() {
        display = !display;
    }

    public int getCounter() {
        return counter;
    }

    public boolean display() {
        return display;
    }



    public void ParserDisplay() {
        int[][][][] temp = EngineReference.newmemoryEater.peek();
        if(temp==null){
            System.out.println("ha");
            temp=EngineReference.newmemoryEater.getFirstState();
            System.out.println(temp);
        }
        this.tileMap = new LinkedList[numTilesX * numTilesY];
        for (int i = 0; i < numTilesX * numTilesY; i++) {
            tileMap[i] = new LinkedList<ImageLayer>();
        }
        //if (temp==null){EngineReference.newmemoryEater.getFirstState();}
        if (EngineReference != null) {
            for (int i = 0; i < temp.length; i++) {
                for (int j = 0; j < temp[i].length; j++) {
                    for (int k = 0; k < temp[i][j].length; k++) {
                        if (temp[i][j][k][0] != 0 && display) {
                            if (temp[i][j][k][0]>5){
                                try {
                                    addImage(i, j, ImageIO.read(new File(FileFinder(temp[i][j][k]))), k);
                                } catch (IOException e) {
                                    System.out.println("1: Cant read file "+FileFinder(temp[i][j][k]));
                                    throw new RuntimeException(e);
                                }
                            }

                            //+1=connected right
                            //+2 = connected up
                            //+4 = connected left
                            //+8 = connected down
                            else{
                                boolean[] around = {false,false,false,false};
                                try{
                                    for (int l = 0; l < temp[i][j+1].length; l++) {
                                        if(temp[i][j+1][l][0]==temp[i][j][k][0]){//Connects to the right
                                            around[0]=true;//break;
                                        }
                                    }
                                }catch(Exception e){}


                                try{
                                    for (int l = 0; l < temp[i-1][j].length; l++) {
                                        if(temp[i-1][j][l][0]==temp[i][j][k][0]){//Connects to up
                                            around[1]=true;//break;
                                        }
                                    }
                                }catch(Exception e){}
                                try{
                                    for (int l = 0; l < temp[i][j-1].length; l++) {
                                        if(temp[i][j-1][l][0]==temp[i][j][k][0]){//Connects to the left
                                            around[2]=true;//break;
                                        }
                                    }
                                }catch(Exception e){}
                                try{
                                    for (int l = 0; l < temp[i+1][j].length; l++) {
                                        if(temp[i+1][j][l][0]==temp[i][j][k][0]){//Connects to down
                                            around[3]=true;//break;
                                        }
                                    }
                                }catch(Exception e){}


                                try {
                                    addImage(i, j, ImageIO.read(new File(FileFinder(temp[i][j][k],around))), k);
                                } catch (IOException e) {
                                    System.out.println("2: Cant read file "+FileFinder(temp[i][j][k], around));
                                    throw new RuntimeException(e);
                                }
                            }
                        }
                    }
                }
            }
        }
        repaint();
    }

    public String FileFinder(int[] x) {
        int id = x[0];
        int rotation = x[1]%4;
        int walkingcycle = x[2];
        String first = null;
        int second;
        int third;
        switch (id) {
            case 5:
                first = "flag";
                break;
            case 6:
                first = "rock";
                break;
            case 7:
                first = "baba";
                break;
            case 8:
                first = "skull";
                break;
            case 9:
                first = "tile";
                break;
            case 10:
                first = "flag";
                break;
            case 11:
                first = "flower";
                break;
            case 12:
                first = "text_is";
                break;
            case 13:
                first = "text_you";
                break;
            case 14:
                first = "text_win";
                break;
            case 15:
                first = "text_defeat";
                break;
            case 16:
                first = "text_push";
                break;
            case 17:
                first = "text_stop";
                break;
            case 18:
                first = "text_hot";
                break;
            case 19:
                first = "text_melt";
                break;
            case 20:
                first = "text_sink";
                break;
            case 21:
                first = "text_wall";
                break;
            case 22:
                first = "text_lava";
                break;
            case 23:
                first = "text_water";
                break;
            case 24:
                first = "text_brick";
                break;
            case 25:
                first = "text_grass";
                break;
            case 26:
                first = "text_rock";
                break;
            case 27:
                first = "text_baba";
                break;
            case 28:
                first = "text_skull";
                break;
            case 29:
                first = "text_tile";
                break;
            case 30:
                first = "text_flag";
                break;
            case 31:
                first = "text_flower";
                break;
                //I really do regret not asking GPT to type this all up for me
        }
        third = counter;

        if(id!=7){
            walkingcycle=0;
        }
        if(id==7||id==8){//If rotatable
            second = ((rotation%4) * 8) + (walkingcycle);
        }else{
            second = walkingcycle;
        }


        return "Sprites/" + first + '_' + second + '_' + third + ".png";
    }
    public String FileFinder(int[] x, boolean[] around) {
        int id = x[0];
        String first = null;
        int second = 0;
        int third;
        switch (id) {

            case 1:
                first = "wall";
                break;
            case 2:
                first = "cloud";//Lava is recolored water. If i recolor, fix.
                break;
            case 3:
                first = "water";
                break;
            case 4:
                first = "brick";
                break;
            case 5:
                first = "grass";
                break;
        }
        third = counter;
        //this is genius
        //+1=connected right
        //+2 = connected up
        //+4 = connected left
        //+8 = connected down
        if (around[0]){second++;}
        if (around[1]){second+=2;}
        if (around[2]){second+=4;}
        if (around[3]){second+=8;}

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
                ParserDisplay();
            }
        }

    }
}