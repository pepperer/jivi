import java.util.concurrent.ExecutorService;
import java.util.concurrent.SynchronousQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class HiyExecutorService {

    private static class Host {
        static ThreadPoolExecutor hiyThreadPoolExecutor = new ThreadPoolExecutor(4, 100, 60L, TimeUnit.SECONDS, new SynchronousQueue<Runnable>());
    }

    public static ThreadPoolExecutor getThreadPoolExecutor() {
        return Host.hiyThreadPoolExecutor;
    }
}
