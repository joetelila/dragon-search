
package sample;

import java.io.File;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.atomic.AtomicLong;

public class Main {

    public static File   POISONPILL  = new File("");

    private final String        filename;
    private final File          baseDir;
    private final int           concurrency;
    private final AtomicLong    count;

    public Main(final String filename, final File baseDir, final int concurrency) {
        this.filename = filename;
        this.baseDir = baseDir;
        this.concurrency = concurrency;
        count = new AtomicLong(0);
    }

    public File find() {

        final ExecutorService ex = Executors.newFixedThreadPool(concurrency + 1);
        final BlockingQueue<File> dirQueue = new LinkedBlockingQueue<File>();
        final BlockingQueue<File> fileQueue = new LinkedBlockingQueue<File>(10000);
        for (int i = 0; i < concurrency; i++) {
            ex.submit(new RunnableDirSearch(dirQueue, fileQueue, count, concurrency));
            if(Thread.interrupted()){
                break;
            }
        }
        count.incrementAndGet();
        dirQueue.add(baseDir);
        final Future<File> c = ex.submit(new CallableFileSearch(dirQueue, fileQueue, filename, concurrency));
        try {
            final File f = c.get();

            return f;
        } catch (final Exception e) {
            return null;
        } finally {
            ex.shutdownNow();
        }

    }


}