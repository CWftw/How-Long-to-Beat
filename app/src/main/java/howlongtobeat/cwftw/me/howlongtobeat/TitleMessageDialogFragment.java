/*
 * Colin Willson & Matt Allen
 * Final Project, PROG3210
 * December 13, 2015
 *
 */

package howlongtobeat.cwftw.me.howlongtobeat;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;

public class TitleMessageDialogFragment extends DialogFragment {

    public static TitleMessageDialogFragment newInstance(int title, int message) {
        TitleMessageDialogFragment frag = null;
        try {
            frag = new TitleMessageDialogFragment();
            Bundle args = new Bundle();
            args.putInt("title", title);
            args.putInt("message", message);
            frag.setArguments(args);
            frag.setCancelable(false);
        } catch (Exception e) {
            Log.e("TitleMessageDialogFragm", "newInstance");
        }
        return frag;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        Dialog dialog = null;
        try {
            int title = getArguments().getInt("title");
            int message = getArguments().getInt("message");

            dialog = new AlertDialog.Builder(getActivity())
                    .setMessage(message)
                    .setTitle(title)
                    .setPositiveButton(R.string.alert_dialog_ok,
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int whichButton) {
                                    // Close dialog
                                }
                            }
                    )
                    .create();
        } catch (Exception e) {
            Log.e("TitleMessageDialogFragm", "onCreateDialog");
        }
        return dialog;
    }
}