
import java.io.File;
import java.io.IOException;
import java.util.*;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileInputStream;
public class filenotfound {
    public static void main(String[] args) {

        FileOutputStream outputfile = null;

        try {


            File givenfile = new File("C:\\Users\\braun\\IdeaProjects\\BabaJava\\PageFile.txt");


            outputfile = new FileOutputStream(givenfile);

        }catch(FileNotFoundException ex) {
            ex.printStackTrace();

        }finally {

            try {
                if(outputfile != null) {
                    outputfile.close();
                }
            }catch(IOException ex) {
                ex.printStackTrace();
            }
        }
    }
}