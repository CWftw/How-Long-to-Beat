package howlongtobeat.cwftw.me.howlongtobeat;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;

import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by Matt on 12/9/2015.
 */
public class UpdateService extends Service
{
    private Timer timer;

    @Override
    public IBinder onBind(Intent intent)
    {
        return null;
    }

    private void startTimer()
    {
        TimerTask task = new TimerTask()
        {
            @Override
            public void run()
            {
                Log.d("Update", "Timer started");
            }
        };

        timer = new Timer(true);
        int delay = 1000;
        int interval = 1000;
        timer.schedule(task, delay, interval);
    }

    private void stopTimer()
    {
        if(timer != null)
        {
            timer.cancel();
        }
    }
}