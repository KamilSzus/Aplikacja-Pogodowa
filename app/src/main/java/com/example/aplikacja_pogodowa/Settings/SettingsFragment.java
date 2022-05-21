package com.example.aplikacja_pogodowa.Settings;

import android.os.Bundle;

import androidx.preference.PreferenceFragmentCompat;

import com.example.aplikacja_pogodowa.R;

public class SettingsFragment extends PreferenceFragmentCompat {

    @Override
    public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {
        setPreferencesFromResource(R.xml.root_preferences, rootKey);
    }
}