/*
 * Colin Willson & Matt Allen
 * Final Project, PROG3210
 * December 13, 2015
 *
 */

package howlongtobeat.cwftw.me.howlongtobeat;

import android.app.ActivityManager;
import android.app.Application;
import android.content.Intent;
import android.preference.PreferenceManager;

public class HowLongToBeat extends Application {
    @Override
    public void onCreate() {
        super.onCreate();

        // Set default Preferences
        PreferenceManager.setDefaultValues(this, R.xml.fragment_preferences, false);

        ActivityManager manager = (ActivityManager) getSystemService(ACTIVITY_SERVICE);
        boolean isRunning = false;

        for (ActivityManager.RunningServiceInfo service : manager.getRunningServices(Integer.MAX_VALUE)) {
            // Check if update service is already running
            if ("howlongtobeat.cwftw.me.howlongtobeat.UpdateService".equals(service.service.getClassName())) {
                isRunning = true;
            }
        }

        if (!isRunning) {
            // If service is not running, start it
            startService(new Intent(this, UpdateService.class));
        }
    }
}
