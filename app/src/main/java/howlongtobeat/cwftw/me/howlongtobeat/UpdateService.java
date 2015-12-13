package howlongtobeat.cwftw.me.howlongtobeat;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.IBinder;
import android.preference.CheckBoxPreference;
import android.preference.PreferenceManager;
import android.support.v4.app.NotificationCompat;
import android.util.Log;
import java.util.Timer;
import java.util.TimerTask;

import howlongtobeat.cwftw.me.howlongtobeat.fragments.FavoriteFragment;

/*
 * Created by Matt on 12/9/2015.
 */
public class UpdateService extends Service
{
    private Timer timer;

    @Override
    public void onCreate()
    {
        super.onCreate();
        startTimer();
    }

    @Override
    public IBinder onBind(Intent intent)
    {
        return null;
    }

    @Override
    public void onDestroy()
    {
        super.onDestroy();
        stopTimer();
    }

    private void startTimer()
    {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
        final boolean backUpdate = preferences.getBoolean("pref_backUpdate", false);

        TimerTask task = new TimerTask()
        {
            @Override
            public void run()
            {
                Log.d("Update", "Timer started");

                if(backUpdate)
                {
                    sendNotification("One of your favorite games has been updated!");
                }
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

    private void sendNotification(String text)
    {
        NotificationCompat.Builder nBuilder = new
                NotificationCompat.Builder(getApplicationContext());

        Intent favoritesIntent = new Intent(getApplicationContext(),
                MainActivity.class).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

        favoritesIntent.putExtra("isNotification", true);

        PendingIntent pIntent = PendingIntent.getActivity(
                getApplicationContext(), 0, favoritesIntent,
                PendingIntent.FLAG_UPDATE_CURRENT);

        nBuilder.setSmallIcon(R.mipmap.ic_launcher);
        nBuilder.setContentTitle("How Long to Beat");
        nBuilder.setContentText(text);
        nBuilder.setContentIntent(pIntent);

        Notification notification = nBuilder.build();

        NotificationManager manager =
                (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        manager.notify(1, notification);
    }
}