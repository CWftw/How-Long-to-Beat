package howlongtobeat.cwftw.me.howlongtobeat;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.preference.PreferenceFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;

public class Preferences extends PreferenceFragment
{
    public static Preferences newInstance()
    {
        Preferences fragment = new Preferences();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    public Preferences()
    {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.fragment_preferences);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
    {
        final CheckBox updateCheck = (CheckBox) container.findViewById(R.id.updateCheck);

        updateCheck.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CheckBox pluggedInCheck = (CheckBox) v.findViewById(R.id.pluggedInCheck);

                if (updateCheck.isChecked())
                {
                    pluggedInCheck.setEnabled(true);
                    getActivity().startService(
                            new Intent(getActivity(), NotificationsService.class));
                }
                else
                {
                    pluggedInCheck.setEnabled(false);
                    getActivity().stopService(
                            new Intent(getActivity(), NotificationsService.class));
                }
            }
        });

        return inflater.inflate(R.layout.activity_main, container, false);
    }

    @Override
    public void onDetach()
    {
        super.onDetach();
    }
}