package de.onemoresecond.traffic.fragments;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.preference.PreferenceFragmentCompat;

import de.onemoresecond.traffic.R;
import de.onemoresecond.traffic.util.TrafficServiceControl;

/**
 * Created by matt on 6/6/15.
 */
public class SettingsFragment extends PreferenceFragmentCompat implements
        SharedPreferences.OnSharedPreferenceChangeListener {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        PreferenceManager.getDefaultSharedPreferences(getContext())
                .registerOnSharedPreferenceChangeListener(this);
    }

    @Override
    public void onCreatePreferences(Bundle bundle, String s) {
        addPreferencesFromResource(R.xml.preferences);
    }

    @Override
    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
        if (key.equals("pref_use_service")) {
            if (!sharedPreferences.getBoolean("pref_use_service", false)) {
                TrafficServiceControl.start(getContext());
            } else {
                TrafficServiceControl.stop(getContext());
            }
        }
    }
}
