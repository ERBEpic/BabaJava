import javax.imageio.ImageIO;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class BabaObjects {
    private boolean[] propertiesStorage = new boolean[100];
    private boolean toBeDeleted = false;
    private int x;
    private int y;
    private int z;
    private int rotation;
    private int id;
    private int walkingcycle;
    private Image image;

    public BabaObjects(int id, int rotation, int walkingcycle){
        this.id=id;
        this.rotation=rotation;
        this.walkingcycle=walkingcycle;
        for (int i = 0; i < this.propertiesStorage.length; i++) {
            this.propertiesStorage[0]=false;
        }
        try {
            this.image = ImageIO.read(new File(getPath()));
        } catch (IOException e) {
            System.out.println("Uh Oh!");
        }
    }
    public BabaObjects(int id, boolean rotation, boolean walkingcycle){

        this.id=id;
        if (rotation=true){this.rotation=0;}else{this.rotation=5;}
        if (walkingcycle=true){this.walkingcycle=0;}else{this.walkingcycle=5;}
    }
    public String getPath(){//Four thigns matter. These 3, and counter.
        String first = null;
        int second;
        int third;
        switch (id){
            case 0: first = "algae";break;
            case 1: first = "arm";break;
            case 2: first = "arrow";break;
            case 3: first = "baba";break;
            case 4: first = "text_you";break;
            case 5: first = "wall";break;//This needs to be something special. Wall is WEIRD.

        }
        third = GraphicsController.getCounter();
        if (rotation == 5){
            second = walkingcycle;
        }else {second = rotation*8+walkingcycle;
        }
        return "C:/Users/Joespeh/IdeaProjects/Baba Is Me/Sprites/"+first+'_'+second+'_'+third+".png";
    }
    public void moveYouLeft(){
        if(this.amYou()==true){
        System.out.println("Im moving left!");
        this.walkingcycle++;
        if (this.walkingcycle>3){this.walkingcycle=0;}
        this.rotation=2;
    }}
    public void moveYouRight(int x,int y,int z){
        this.x=x;
        this.y=y;//These honestly might not be neccesary, but reliability is always good
        this.z=z;
        if(this.amYou()==true){
            System.out.println("Im moving Right!");
            this.walkingcycle++;
        if (this.walkingcycle>3){this.walkingcycle=0;}
        this.rotation = 0;
            if (this.checkIfDead()==false){
                this.y=this.y+1;
            Engine.levelStoragePush[this.x][this.y]=new ArrayList<BabaObjects>();
            BabaObjects bba = new BabaObjects(this.id,this.rotation,this.walkingcycle);
            bba.setYou(true);
            Engine.levelStoragePush[this.x][this.y].add(bba);
                this.deleteMe();
        }}}
    public void moveYouDown(){
        if(this.amYou()==true){
        System.out.println("Im moving down!");
        this.walkingcycle++;
        if (this.walkingcycle>3){this.walkingcycle=0;}
        this.rotation = 3;
    }}
    public void moveYouUp(){
        if(this.amYou()==true){
        System.out.println("Im moving up!");
        this.walkingcycle++;
        if (this.walkingcycle>3){this.walkingcycle=0;}
        this.rotation = 1;
    }}

    public boolean amYou(){
        return this.propertiesStorage[0];
    }
    public void setYou(boolean x){
        this.propertiesStorage[0]=x;
    }
    public boolean checkIfDead(){
        return false;/*
        for (int i = 0; i < z; i++) {
            if (Engine.levelStoragePush[x][y].get(i).checkProperty(3)==true){//Check if defeat
                return true;
            }
        }
        return false;*/
    }
    public boolean checkIfDeleted(){
        return this.toBeDeleted;
    }
    public boolean checkProperty(int id){
        return this.propertiesStorage[id];
    }
    public void setProperty(int id, boolean sign){
        this.propertiesStorage[id]=sign;
    }
    public void deleteMe(){
        this.toBeDeleted=true;
    }
    public void setImage(int row, int col) {
        try {
            BabaFrame.babakey.imageIcons[row][col].setImage(ImageIO.read(new File(getPath())));
        } catch (IOException e) {
            System.out.println("uh oh");
        }
        BabaFrame.babakey.imageLabels[row][col].setIcon(BabaFrame.babakey.imageIcons[row][col]);
    }
    public void hideImage(){

    }
}
