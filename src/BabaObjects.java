public class BabaObjects {
    private boolean isYou = false;
    private int x;
    private int y;
    private int rotation;
    private int id;
    private int walkingcycle;
    public BabaObjects(int id, int rotation, int walkingcycle){

        this.id=id;
        this.rotation=rotation;
        this.walkingcycle=walkingcycle;
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
    public void moveYouRight(int x, int y, int z){
        if(this.amYou()==true){
            System.out.println("Im moving Right!");
            this.walkingcycle++;
        if (this.walkingcycle>3){this.walkingcycle=0;}
        this.rotation = 0;

    }}
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
        return this.isYou;
    }
    public void setYou(boolean x){
        this.isYou=x;
    }
}
