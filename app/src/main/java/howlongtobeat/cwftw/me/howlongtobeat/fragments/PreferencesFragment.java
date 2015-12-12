package howlongtobeat.cwftw.me.howlongtobeat.fragments;

import android.os.AsyncTask;
import android.os.Bundle;
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