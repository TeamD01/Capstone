package com.skombie.utilities;
import com.skombie.model.House;
import com.skombie.app.SkombieApp;
import com.skombie.model.Skombie;
import java.util.TimerTask;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class TaskHandler extends TimerTask {
    House house = new House();
    Runnable rb1 = new SkombieApp(house);
    Runnable skombie = new Skombie(house);
    ScheduledThreadPoolExecutor tp = new ScheduledThreadPoolExecutor(2);

    // House parameter
    @Override
    public void run() {
        tp.setContinueExistingPeriodicTasksAfterShutdownPolicy(true); //not sure about this one...
        tp.scheduleWithFixedDelay(rb1, 0, 10, TimeUnit.SECONDS);
        //set to 10000 to avoid many skombies while testing.
            tp.scheduleAtFixedRate(skombie, 180, 120, TimeUnit.SECONDS);
    }

}







