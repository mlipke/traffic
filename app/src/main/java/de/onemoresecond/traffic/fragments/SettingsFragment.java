package de.onemoresecond.traffic.fragments;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceFragment;
import android.preference.PreferenceManager;

import de.onemoresecond.traffic.R;
import de.onemoresecond.traffic.util.TrafficServiceControl;

/**
 * Created by matt on 6/6/15.
 */
public class SettingsFragment extends PreferenceFragment implements
        SharedPreferences.OnSharedPreferenceChangeListener {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        PreferenceManager.getDefaultSharedPreferences(getActivity())
                .registerOnSharedPreferenceChangeListener(this);

        addPreferencesFromResource(R.xml.preferences);
    }

    @Override
    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
        if (key.equals("pref_use_service")) {
            if (!sharedPreferences.getBoolean("pref_use_service", false)) {
                TrafficServiceControl.start(getActivity());
            } else {
                TrafficServiceControl.stop(getActivity());
            }
        }
    }
}
