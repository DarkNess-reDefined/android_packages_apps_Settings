package com.android.settings.dnd.fragments;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.SystemProperties;
import android.support.v7.preference.ListPreference;
import android.support.v7.preference.Preference;
import android.support.v7.preference.PreferenceScreen;
import android.support.v7.preference.Preference.OnPreferenceChangeListener;
import android.support.v14.preference.SwitchPreference;
import android.provider.Settings;

import android.hardware.fingerprint.FingerprintManager;
import com.android.internal.logging.MetricsProto.MetricsEvent;
import com.android.settings.DevelopmentSettings;
import com.android.settings.R;
import com.android.settings.SettingsPreferenceFragment;

public class MiscSettings extends SettingsPreferenceFragment
        implements Preference.OnPreferenceChangeListener {

    private ListPreference mScreenshotType;

        private FingerprintManager mFingerprintManager;
        private SwitchPreference mFingerprintVib;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        addPreferencesFromResource(R.xml.dnd_misc_tab);
            PreferenceScreen prefSet = getPreferenceScreen();

            // Fingerprint vibration
            mFingerprintManager = (FingerprintManager) getActivity().getSystemService(Context.FINGERPRINT_SERVICE);
            mFingerprintVib = (SwitchPreference) prefSet.findPreference("fingerprint_success_vib");
            if (!mFingerprintManager.isHardwareDetected()){
                prefSet.removePreference(mFingerprintVib);
            }

    @Override
    protected int getMetricsCategory() {
        return MetricsEvent.APPLICATION;
    }
}
