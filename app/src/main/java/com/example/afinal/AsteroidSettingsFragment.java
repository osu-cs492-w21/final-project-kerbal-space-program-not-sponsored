package com.example.afinal;

import android.os.Bundle;

import androidx.preference.PreferenceFragmentCompat;

public class AsteroidSettingsFragment extends PreferenceFragmentCompat {

    @Override
    public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {
        addPreferencesFromResource(R.xml.prefs);

    }
}
