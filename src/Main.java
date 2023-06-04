import java.awt.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;

//All things between classes will now run through main. This is to control where classes talk to eachother, and stop everything from being static.
public class Main {
    public Engine BabaEngine;
    private static double scaler = 0.5;

    public Main() {
        try {
            BabaEngine = new Engine();
        } catch (IOException | ClassNotFoundException | InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    public static void main(String[] args) throws InterruptedException, IOException, ClassNotFoundException {
        Thread.sleep(100);
        Main main = new Main(); //the fact that this is needed really annoys me but I guess thats Java.
        main.BabaEngine.babakey.setSize(20*24,20*24);
        Thread.sleep(10);

        //main.BabaEngine.babakey.setDelay((int) (main.BabaEngine.newmemoryEater.firstState.toString().length()*(scaler+1)));
        //main.BabaEngine.babakey.setDelay(100);
        //System.out.println(main.BabaEngine.newmemoryEater.firstState.toString().length()*(scaler+1));
    }
}

