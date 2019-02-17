package pl.com.tutorials.foodrecipes;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;

public class AppExecutor {

    private static AppExecutor instance;
    private final ScheduledExecutorService networkIO = Executors.newScheduledThreadPool(3);

    public static AppExecutor getInstance() {
        if (instance == null){
            instance = new AppExecutor();
        }
        return instance;
    }

    private AppExecutor(){}

    public ScheduledExecutorService networkIO() {
        return networkIO;
    }
}
