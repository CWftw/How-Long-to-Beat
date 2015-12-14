/*
 * Colin Willson & Matt Allen
 * Final Project, PROG3210
 * December 13, 2015
 *
 */

package howlongtobeat.cwftw.me.howlongtobeat;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.IBinder;
import android.preference.PreferenceManager;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import java.io.IOException;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import howlongtobeat.cwftw.me.howlongtobeat.activities.MainActivity;
import howlongtobeat.cwftw.me.howlongtobeat.models.Game;

import static howlongtobeat.cwftw.me.howlongtobeat.Utils.isPluggedIn;

public class UpdateService extends Service {
    private Timer timer;

    @Override
    public void onCreate() {
        super.onCreate();
        startTimer();
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        stopTimer();
    }

    private void startTimer() {
        final SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);

        TimerTask task = new TimerTask() {
            @Override
            public void run() {
            Log.d("Update", "Timer started");
            boolean backUpdate = preferences.getBoolean("pref_backUpdate", false);
            boolean pluggedIn = preferences.getBoolean("pref_pluggedIn", false);
            boolean notifications = preferences.getBoolean("pref_notifications", false);
            DatabaseHelper db = DatabaseHelper.getInstance(getApplicationContext());
            List<Game> games = db.selectGames("");

            for (Game game : games)
            {
                HLTBSearcher webDb = new HLTBSearcher();

                try
                {
                    Game webGame = webDb.getGame(game.getTitle(), game.getId());

                    if (game.getMainHours() != webGame.getMainHours() ||
                        game.getMainExtraHours() != webGame.getMainExtraHours() ||
                        game.getCompletionistHours() != webGame.getCompletionistHours() ||
                        game.getCombinedHours() != webGame.getCombinedHours() ||
                        game.getPolled() != webGame.getPolled() ||
                        game.getRatedPercent() != webGame.getRatedPercent() ||
                        game.getBacklogCount() != webGame.getBacklogCount() ||
                        game.getPlaying() != webGame.getPlaying() ||
                        game.getRetired() != webGame.getRetired())
                    {
                        if(backUpdate && (!pluggedIn ||
                                (pluggedIn && isPluggedIn(getApplicationContext()))))
                        {
                            db.updateGame(game.getTitle(), game.getId());
                        }

                        if (notifications)
                        {
                            sendNotification(game.getTitle() + " has been updated!");
                        }
                    }
                }
                catch(IOException e)
                {
                    Log.i("How Long to Beat", e.getMessage());
                }
            }
            }
        };

        timer = new Timer(true);
        int delay = 60000;
        int interval = 600000;
        timer.schedule(task, delay, interval);
    }

    private void stopTimer() {
        if (timer != null) {
            timer.cancel();
        }
    }

    private void sendNotification(String text) {
        NotificationCompat.Builder nBuilder = new
                NotificationCompat.Builder(getApplicationContext());

        Intent favoritesIntent = new Intent(getApplicationContext(),
                MainActivity.class).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

        favoritesIntent.putExtra("isNotification", true);

        PendingIntent pIntent = PendingIntent.getActivity(
                getApplicationContext(), 0, favoritesIntent,
                PendingIntent.FLAG_UPDATE_CURRENT);

        nBuilder.setSmallIcon(R.mipmap.full_star);
        nBuilder.setContentTitle("How Long to Beat");
        nBuilder.setContentText(text);
        nBuilder.setContentIntent(pIntent);

        Notification notification = nBuilder.build();

        NotificationManager manager =
                (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        manager.notify(1, notification);
    }
}