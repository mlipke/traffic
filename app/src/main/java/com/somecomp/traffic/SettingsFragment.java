package com.somecomp.traffic;

import android.os.Bundle;
import android.support.v4.preference.PreferenceFragment;

/**
 * Created by matt on 6/6/15.
 */
public class SettingsFragment extends PreferenceFragment {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Load the preferences from an XML resource
        addPreferencesFromResource(R.xml.preferences);
    }
}
