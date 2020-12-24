package sample;

import java.io.File;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.atomic.AtomicLong;


public class RunnableDirSearch implements Runnable {
    private final BlockingQueue<File> dirQueue;
    private final BlockingQueue<File>   fileQueue;
    private final AtomicLong count;
    private final int                   num;

    public RunnableDirSearch(final BlockingQueue<File> dirQueue, final BlockingQueue<File> fileQueue, final AtomicLong count, final int num) {
        this.dirQueue = dirQueue;
        this.fileQueue = fileQueue;
        this.count = count;
        this.num = num;
    }

    @Override
    public void run() {
        try {
            File dir = dirQueue.take();
            while (dir != Main.POISONPILL) {
                final File[] fi = dir.listFiles();
                if (fi != null) {
                    for (final File element : fi) {
                        if (element.isDirectory()) {

                              if (dir.getName().matches("")){

                                   }

                            count.incrementAndGet();
                            dirQueue.put(element);
                        } else {
                            fileQueue.put(element);
                        }
                    }
                }
                final long c = count.decrementAndGet();
                if (c == 0) {
                    end();
                }
                dir = dirQueue.take();
            }
        } catch (final InterruptedException ie) {
            // file found or error
        }
    }

    private final void end() {
        try {
            fileQueue.put(Main.POISONPILL);
        } catch (final InterruptedException e) {
            // empty
        }
        for (int i = 0; i < num; i++) {
            try {
                dirQueue.put(Main.POISONPILL);
            } catch (final InterruptedException e) {
                // empty
            }
        }
    }
}