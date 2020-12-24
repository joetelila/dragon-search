package sample;

import java.io.File;

/**
 * Created by john on 12/23/15.
 */
public class inputsearch implements Runnable {

    String filename;
    File baseDir = new File("/");

    public   inputsearch(String filename){
        this.filename = filename;
    }

    public void run() {
           dragonGUI.table.setVisible(true);

        Main ff = new Main(filename, baseDir, 6);
        final File f = ff.find();

    }
}
