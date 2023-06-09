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
    private Engine EngineReference;
    private int tileSize;
    private int numTilesX;
    private int numTilesY;
    boolean start = false;
    private LinkedList[] tileMap;//No idea why this is allowed? This feels SUPER wrong. LinkedList is usually declared like "LinkedList<String> linkedList = new LinkedList<>();"
    //Also linkedlist is faster than arraylist for this usage. You can replace LinkedList with ArrayList and the code works the same, but slower.
    private Image offScreenImage;
    private Graphics offScreenGraphics;
    public void setEngine(Engine engine) {
        EngineReference = engine;
    }
    public BabaFrameSimple(int numTilesX, int numTilesY) throws IOException {
        super("Baba is Me!");
        Image image = ImageIO.read(new File("Sprites/baba_0_1.png"));
        setIconImage(image);

        this.tileSize = 24;//How large each sprite is
        this.numTilesX = numTilesX;
        this.numTilesY = numTilesY;
        this.tileMap = new LinkedList[numTilesX * numTilesY];//I chose to do a [] with x&y compressed into one because doing a 2D array of Lists is really complicated and part of the reason why my original (first or second) revision of code didnt work. The slight complexity of working like this just means you have to instead of a nested fori where x=i,y=j, x=i/numXTiles,y=i%numYTiles. Other than that, it surprisingly acts much more normal than a 2d array of lists.

        for (int i = 0; i < numTilesX * numTilesY; i++) {
            tileMap[i] = new LinkedList<>();
        }
        setSize(numTilesX * tileSize, numTilesY * tileSize);

        setBackground(Color.BLACK);//Self Explanatory
        addKeyListener(this);//Listen for when keys are pressed
        setFocusable(true);//Let you be able to click to bring to front
        setUndecorated(true);//Remove titlebar and name and stuff (new java versions change how JFrame is implemented with weird insets and makes it not work decorated)
        setIgnoreRepaint(true);//JFrame has a behavior to automatically repaint whenever updated. This, however, is unneeded for me because It only has to update A. every 175ms, for the shaking animation, and B. Whenever the player inputs something. otherwise, the game doesnt need to be repainted.
        setVisible(true);//Self explanatory
        JPanel panel = new JPanel();
        JLabel label = new JLabel("<html>To play the game, press the arrow keys or wasd to move.<P> Z to undo, R to reset, escape to exit. You control whatever is you.<P> The goal is to reach whatever is win.<P> Press any key to start!<html>");
        //According to StackOverflow, using HTML is the easiest way to put line breaks into a JLabel
        panel.add(label);

        this.add(panel);
        while(!start){
            System.out.printf("");
        }
        this.remove(panel);
        offScreenImage = createImage(getWidth(), getHeight());//""Offscreen. Its not really off screen, more like in the void of the memory somewhere, but visualizing it off screen is how its usually imagined
        offScreenGraphics = offScreenImage.getGraphics();//Only need to do this once interestingly, as offScreenImage.getGraphics() returns an object, and in java, it actually returns a reference, not a whole object.
        //new counterUpdater();//You can just do this? Weird. I guess the reference to that new counterUpdater just gets sent into the void or something
        //^^i ended up using an actual referenced counter but either way

        counterCycler = new counterUpdater();
    }
    private counterUpdater counterCycler;

    public void addImage(int y, int x, Image image, int layer) {
        if(tileMap[x * numTilesX + y]==null){//If its null (problem!)
            tileMap[x * numTilesX + y] = new LinkedList<>();//Then fix the problem!
        }
        tileMap[x * numTilesX + y].add(new ImageLayer(image, layer));
        //repaint(); //Why...Did i ever have this here? Its no wonder the frame used to flicker. Left it in so you can see what the old flickering issue is. Justuncomment repaint();.
    }//Also^, it wasnt just as simple as remove repaint();, as i had to move repaint elsewhere and a few other things for some reason, but repaint(); here was the center of the problem.

    @Override
    public void paint(Graphics g) {
        offScreenGraphics.fillRect(0, 0, getWidth(), getHeight());

        for (int i = 0; i < numTilesX * numTilesY; i++) {
            int y = i % numTilesY;
            int x = i / numTilesX;
            LinkedList<ImageLayer> layers;
            try {
                layers = new LinkedList<>(tileMap[i]);
            } catch (ArrayIndexOutOfBoundsException | NullPointerException e) {
                tileMap[i] = new LinkedList<>();
                layers = new LinkedList<>(tileMap[i]);
            }
            if (layers != null) {
                for (ImageLayer layer : layers) {//For every layer in layers
                    if (layer != null) {
                        offScreenGraphics.drawImage(layer.image, x * tileSize, y * tileSize, tileSize, tileSize, null);//Draw it out
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
        if(start){

        int keyCode = e.getKeyCode();

        switch (keyCode) {
            case KeyEvent.VK_ESCAPE -> System.exit(1);//this is what ExitOnClose calls, so it works great. 1 for user decided to close
            case KeyEvent.VK_UP, KeyEvent.VK_W -> EngineReference.youProperty(1);
            case KeyEvent.VK_DOWN, KeyEvent.VK_S -> EngineReference.youProperty(3);
            case KeyEvent.VK_LEFT, KeyEvent.VK_A -> EngineReference.youProperty(2);
            case KeyEvent.VK_RIGHT, KeyEvent.VK_D -> EngineReference.youProperty(0);
            case KeyEvent.VK_SPACE -> EngineReference.moveWait();//This works, but it doesnt actually do anything because move doesnt work. No point in commenting it out though because it works fine.
            case KeyEvent.VK_R -> EngineReference.resetLevel();
            case KeyEvent.VK_Z -> EngineReference.moveUndoNew();
        }
        }else{
            start = true;
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {}//Required to implement KeyListener.

    private static class ImageLayer {//Same across every instance of BabaFrameSimple. Just a wrapper class of image and layer.
        public Image image;
        public int layer;

        public ImageLayer(Image image, int layer) {
            this.image = image;
            this.layer = layer;
        }
    }

    public void ParserDisplay() {
        int[][][][] temp = EngineReference.newmemoryEater.peek();
        if(temp==null){
            temp=EngineReference.newmemoryEater.getFirstState();
        }
        this.tileMap = new LinkedList[numTilesX * numTilesY];

        //if (temp==null){EngineReference.newmemoryEater.getFirstState();}
        if (EngineReference != null) {
            for (int i = 0; i < temp.length; i++) {
                for (int j = 0; j < temp[i].length; j++) {
                    for (int k = 0; k < temp[i][j].length; k++) {
                        if (temp[i][j][k][0] != 0) {
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
                                            around[0]=true;
                                            break;
                                        }
                                    }
                                }catch(Exception e){}//Failing is intentional. Failing means the tile its checking is out of bounds
                                try{
                                    for (int l = 0; l < temp[i-1][j].length; l++) {
                                        if(temp[i-1][j][l][0]==temp[i][j][k][0]){//Connects to up
                                            around[1]=true;
                                            break;
                                        }
                                    }
                                }catch(Exception e){}
                                try{
                                    for (int l = 0; l < temp[i][j-1].length; l++) {
                                        if(temp[i][j-1][l][0]==temp[i][j][k][0]){//Connects to the left
                                            around[2]=true;
                                            break;
                                        }
                                    }
                                }catch(Exception e){}
                                try{
                                    for (int l = 0; l < temp[i+1][j].length; l++) {
                                        if (temp[i + 1][j][l][0] == temp[i][j][k][0]) {//Connects to down
                                            around[3] = true;
                                            break;
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
            case 6 -> first = "rock";
            case 7 -> first = "baba";
            case 8 -> first = "skull";
            case 9 -> first = "tile";
            case 10 -> first = "flag";
            case 11 -> first = "flower";
            case 12 -> first = "text_is";
            case 13 -> first = "text_you";
            case 14 -> first = "text_win";
            case 15 -> first = "text_defeat";
            case 16 -> first = "text_push";
            case 17 -> first = "text_stop";
            case 18 -> first = "text_hot";
            case 19 -> first = "text_melt";
            case 20 -> first = "text_sink";
            case 21 -> first = "text_wall";
            case 22 -> first = "text_lava";
            case 23 -> first = "text_water";
            case 24 -> first = "text_brick";
            case 25 -> first = "text_grass";
            case 26 -> first = "text_rock";
            case 27 -> first = "text_baba";
            case 28 -> first = "text_skull";
            case 29 -> first = "text_tile";
            case 30 -> first = "text_flag";
            case 31 -> first = "text_flower";

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
            case 1 -> first = "wall";
            case 2 -> first = "cloud";
            case 3 -> first = "water";
            case 4 -> first = "brick";
            case 5 -> first = "grass";
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
        }public void end(){
            counterCycler = null;

        }

    }public void end(){
        this.counterCycler.end();
        super.paint(this.getGraphics());
        JPanel panel = new JPanel();
        JLabel label = new JLabel("<html>That is the end of the tutorial.<P>  If you want to play more, go buy the actual game, called Baba Is You.<html>");
        //According to StackOverflow, using HTML is the easiest way to put line breaks into a JLabel
        panel.add(label);
        add(panel);

        try {
            this.getGraphics().drawImage(ImageIO.read(new File("Sprites/EndingMessage.png")),0,0,null);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        try {
            Thread.sleep(10000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

}