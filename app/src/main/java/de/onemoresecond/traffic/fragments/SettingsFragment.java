package de.onemoresecond.traffic.fragments;

import android.os.Bundle;
import android.support.v7.preference.PreferenceFragmentCompat;

import de.onemoresecond.traffic.R;

/**
 * Created by matt on 6/6/15.
 */
public class SettingsFragment extends PreferenceFragmentCompat {
    @Override
    public void onCreatePreferences(Bundle bundle, String s) {
        addPreferencesFromResource(R.xml.preferences);
    }
}
