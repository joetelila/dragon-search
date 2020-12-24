package sample;

import java.io.*;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.Callable;
import javafx.scene.control.ListView;

/**
 * Created by john on 12/21/15.
 */
  public class CallableFileSearch implements Callable<File> {
        static BlockingQueue<File> dirQueue;
    private final BlockingQueue<File>   fileQueue;
    private final String                name;
    private final int                   num;
    static boolean status=true;


    static ListView<String> selectedItems = new ListView<>();






    public CallableFileSearch(final BlockingQueue<File> dirQueue, final BlockingQueue<File> fileQueue, final String name, final int num) {
        CallableFileSearch.dirQueue = dirQueue;
        this.fileQueue = fileQueue;
        this.name = name;
        this.num = num;
    }

    @Override
    public File call() throws Exception {
        File file = fileQueue.take();

          int count= 0;
        int length = selectedItems.getItems().size();    // Gets how many file extension is selected
        while (file != Main.POISONPILL) {
            final String filename = file.getName().toLowerCase();
            final String lf = name.toLowerCase();

            if (filename.equalsIgnoreCase(name) || filename.startsWith(lf) || filename.endsWith(lf)) {

                if(count==10)
                    return null;

                if (length!=0) {

                          for (int temp=0;temp<length;temp++){
                          if (file.getName().endsWith(String.valueOf(selectedItems.getItems().get(temp)))) {
                          dragonGUI.data.add(new TableProps(file.getName(), file.getAbsolutePath()));
                            count++;

                          if (count == 10) {
                              return null;
                          }
                          }
                      }


                  }else{

                        if(count==10)
                              return null;

                      dragonGUI.data.add(new TableProps(file.getName(), file.getAbsolutePath()));
                      count++;

                  }




            }
            file = fileQueue.take();
        }
        return null;
    }

    private final void end() {
        for (int i = 0; i < num; i++) {
            try {
                dirQueue.put(Main.POISONPILL);
            } catch (final InterruptedException e) {
                // empty
            }
        }
    }
}