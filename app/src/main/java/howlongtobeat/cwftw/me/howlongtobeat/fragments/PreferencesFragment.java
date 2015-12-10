package howlongtobeat.cwftw.me.howlongtobeat.fragments;

import android.app.PendingIntent;
import android.content.Intent;
import android.os.Bundle;
import android.preference.PreferenceFragment;
import android.support.v4.app.NotificationBuilderWithBuilderAccessor;
import android.support.v4.app.NotificationCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;

import howlongtobeat.cwftw.me.howlongtobeat.UpdateService;
import howlongtobeat.cwftw.me.howlongtobeat.R;

public class PreferencesFragment extends PreferenceFragment
{
    public static PreferencesFragment newInstance()
    {
        PreferencesFragment fragment = new PreferencesFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    public PreferencesFragment()
    {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.fragment_preferences);
    }

//    @Override
//    public View onCreateView(LayoutInflater inflater, ViewGroup container,
//                             Bundle savedInstanceState)
//    {
////        final CheckBox updateCheck = (CheckBox) container.findViewById(R.id.updateCheck);
////        final CheckBox notificationsCheck =
////                (CheckBox) container.findViewById(R.id.notificationsCheck);
////
////        updateCheck.setOnClickListener(new View.OnClickListener() {
////            @Override
////            public void onClick(View v) {
////                CheckBox pluggedInCheck = (CheckBox) v.findViewById(R.id.pluggedInCheck);
////
////                if (updateCheck.isChecked())
////                {
////                    pluggedInCheck.setEnabled(true);
////                }
////                else
////                {
////                    pluggedInCheck.setEnabled(false);
////                }
////            }
////        });
////
////        notificationsCheck.setOnClickListener(new View.OnClickListener() {
////            @Override
////            public void onClick(View v) {
////                if (updateCheck.isChecked())
////                {
////                    getActivity().startService(
////                            new Intent(getActivity(), UpdateService.class));
////                }
////                else
////                {
////                    getActivity().stopService(
////                            new Intent(getActivity(), UpdateService.class));
////                }
////            }
////        });
//
////        return inflater.inflate(R.layout.activity_main, container, false);
//    }

    @Override
    public void onDetach()
    {
        super.onDetach();
    }
}