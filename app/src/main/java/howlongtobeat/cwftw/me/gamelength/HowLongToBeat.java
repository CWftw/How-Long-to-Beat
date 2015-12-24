/*
 * Colin Willson & Matt Allen
 * Final Project, PROG3210
 * December 13, 2015
 *
 */

package howlongtobeat.cwftw.me.gamelength;

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

        for (ActivityManager.RunningServiceInfo service :
                manager.getRunningServices(Integer.MAX_VALUE)) {
            if ("gamelength.cwftw.me.gamelength.UpdateService".equals(
                    service.service.getClassName())) {
                isRunning = true;
            }
        }

        if (!isRunning) {
            startService(new Intent(this, UpdateService.class));
        }
    }
}
