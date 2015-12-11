package howlongtobeat.cwftw.me.howlongtobeat.fragments;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.CheckBoxPreference;
import android.preference.Preference;
import android.preference.PreferenceFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
    {
        final CheckBoxPreference updateCheck =
                (CheckBoxPreference) getPreferenceManager().findPreference("pref_update");
        final CheckBoxPreference pluggedInCheck =
                (CheckBoxPreference) getPreferenceManager().findPreference("pref_pluggedIn");
        final CheckBoxPreference notificationsCheck =
                (CheckBoxPreference) getPreferenceManager().findPreference("pref_notifications");

        updateCheck.setOnPreferenceChangeListener(new Preference.OnPreferenceChangeListener()
        {
            @Override
            public boolean onPreferenceChange(Preference preference, Object newValue)
            {
                if (updateCheck.isChecked())
                {
                    return true;
                }
                else
                {
                    return false;
                }
            }
        });

        pluggedInCheck.setOnPreferenceChangeListener(new Preference.OnPreferenceChangeListener()
        {
            @Override
            public boolean onPreferenceChange(Preference preference, Object newValue)
            {
                if (pluggedInCheck.isChecked())
                {
                    return true;
                }
                else
                {
                    return false;
                }
            }
        });

        notificationsCheck.setOnPreferenceChangeListener(new Preference.OnPreferenceChangeListener()
        {
            @Override
            public boolean onPreferenceChange(Preference preference, Object newValue)
            {
                if (notificationsCheck.isChecked())
                {
                    return true;
                }
                else
                {
                    return false;
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

    class updateGames extends AsyncTask<Void, Void, Void>
    {
        @Override
        protected Void doInBackground(Void... params)
        {
            return null;
        }
    }
}