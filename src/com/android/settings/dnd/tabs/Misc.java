package com.android.settings.dnd.tabs;

import com.android.internal.logging.MetricsProto.MetricsEvent;

import android.os.Bundle;
import com.android.settings.R;

import com.android.settings.SettingsPreferenceFragment;

public class Misc extends SettingsPreferenceFragment {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        addPreferencesFromResource(R.xml.dnd_misc_tab);
    }

    @Override
    protected int getMetricsCategory() {
        return MetricsEvent.REDEFINITION;
    }
}
