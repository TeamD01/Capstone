package com.skombie.utilities;
import com.skombie.app.SkombieApp;
import com.skombie.Skombie;
import java.util.TimerTask;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class TaskHandler extends TimerTask {
    Runnable rb1 = new SkombieApp();
    Runnable skombie = new Skombie();
    ScheduledThreadPoolExecutor tp = new ScheduledThreadPoolExecutor(2);

    @Override
    public void run() {
        tp.setContinueExistingPeriodicTasksAfterShutdownPolicy(true); //not sure about this one...
        tp.scheduleWithFixedDelay(rb1, 0, 10, TimeUnit.SECONDS);
        //set to 10000 to avoid many skombies while testing.
        tp.scheduleAtFixedRate(skombie, 10000, 10, TimeUnit.SECONDS);
    }

}







