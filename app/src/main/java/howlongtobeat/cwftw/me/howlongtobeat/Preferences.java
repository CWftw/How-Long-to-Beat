package howlongtobeat.cwftw.me.howlongtobeat;

import android.app.Activity;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

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
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.activity_main, container, false);
    }

    @Override
    public void onDetach()
    {
        super.onDetach();
    }
}