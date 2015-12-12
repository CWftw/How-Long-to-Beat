package howlongtobeat.cwftw.me.howlongtobeat;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

/**
 * Created by Matt on 12/11/2015.
 */
public class BootReceiver extends BroadcastReceiver
{
    @Override
    public void onReceive(Context context, Intent intent)
    {
        Log.d("How Long to Beat", "Booted");
        context.startService(new Intent(context, UpdateService.class));
    }
}
