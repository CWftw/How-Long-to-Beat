package howlongtobeat.cwftw.me.howlongtobeat;

import android.app.ActivityManager;
import android.app.Application;
import android.content.Intent;
import android.preference.PreferenceManager;

/**
 * Created by colin on 2015-12-12.
 */
public class HowLongToBeat extends Application
{
    @Override
    public void onCreate()
    {
        super.onCreate();
        // Set default Preferences
        PreferenceManager.setDefaultValues(this, R.xml.fragment_preferences, false);

        ActivityManager manager = (ActivityManager) getSystemService(ACTIVITY_SERVICE);
        boolean isRunning = false;

        for (ActivityManager.RunningServiceInfo service :
                manager.getRunningServices(Integer.MAX_VALUE))
        {
            if("howlongtobeat.cwftw.me.howlongtobeat.UpdateService".equals(
                    service.service.getClassName()))
            {
                isRunning = true;
            }
        }

        if(!isRunning)
        {
            startService(new Intent(this, UpdateService.class));
        }
    }
}
