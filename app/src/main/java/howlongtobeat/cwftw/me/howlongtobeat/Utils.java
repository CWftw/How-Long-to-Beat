/*
 * Colin Willson & Matt Allen
 * Final Project, PROG3210
 * December 13, 2015
 *
 */

package howlongtobeat.cwftw.me.howlongtobeat;

import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.BatteryManager;

public class Utils {
    public enum FormatTypes {
        HOURS, PERCENT
    }

    public static boolean isNetworkAvailable(Context context) {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }

    public static boolean isPluggedIn(Context context) {
        Intent intent = context.registerReceiver(null, new IntentFilter(Intent.ACTION_BATTERY_CHANGED));
        int plugged = intent.getIntExtra(BatteryManager.EXTRA_PLUGGED, -1);
        return plugged == BatteryManager.BATTERY_PLUGGED_AC || plugged == BatteryManager.BATTERY_PLUGGED_USB;
    }

    public static String formatData(double input, FormatTypes type, Context context) {
        String output = "";
        if (input == -1) {
            // 'empty'
            output += context.getResources().getString(R.string.empty);
        } else {
            output += Integer.toString((int) input);
            switch (type) {
                case HOURS:
                    if (!(input % 1 == 0)) {
                        // If number is not whole, it has a half
                        output += "½";
                    }
                    output += " " + context.getResources().getString(R.string.hours);
                    break;
                case PERCENT:
                    output += "%";
                    break;
            }
        }

        return output;
    }
}
