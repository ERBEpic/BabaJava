import java.io.IOException;

//This doesnt *really* do much, because Engine handles most of the heavy lifting. Main doesnt really have a role except to be the thing which sets the code in motion.
public class Main {
    public Engine BabaEngine;
    public Main() {
        try {
            BabaEngine = new Engine();
        } catch (IOException | ClassNotFoundException | InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    public static void main(String[] args){

        Main main = new Main(); //the fact that this is needed really annoys me but I guess thats Java.
        main.BabaEngine.babakey.setSize(20*24,20*24);//20(x&y tiles) * 24(sprite size) = total width/height
    }
}

