/*
 * Copyright (C) 2015 The Dirty Unicorns project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.android.settings.dnd.fragments;

import android.content.ContentResolver;
import android.content.res.Resources;
import android.database.ContentObserver;
import android.os.Bundle;
import android.os.Handler;
import android.os.UserHandle;
import android.support.v7.preference.ListPreference;
import android.support.v7.preference.Preference;
import android.support.v7.preference.PreferenceScreen;
import android.support.v7.preference.PreferenceCategory;
import android.support.v7.preference.Preference.OnPreferenceChangeListener;
import android.support.v14.preference.SwitchPreference;
import android.provider.Settings;
import android.provider.Settings.SettingNotFoundException;

import com.android.settings.R;
import com.android.internal.logging.MetricsLogger;
import com.android.settings.SettingsPreferenceFragment;
import com.android.settings.Utils;

import com.android.internal.logging.MetricsProto.MetricsEvent;

import com.android.settings.dnd.Preferences.ColorPickerPreference;

public class Logo extends SettingsPreferenceFragment implements OnPreferenceChangeListener {

    public static final String TAG = "Logo";

    private static final String KEY_DND_LOGO_COLOR = "status_bar_dnd_logo_color";
    private static final String KEY_DND_LOGO_STYLE = "status_bar_dnd_logo_style";

    private ColorPickerPreference mdndLogoColor;
    private ListPreference mdndLogoStyle;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        addPreferencesFromResource(R.xml.dnd_logo);

        PreferenceScreen prefSet = getPreferenceScreen();

        int intColor;
        String hexColor;

        	// DND logo color
        	mdndLogoColor =
            (ColorPickerPreference) prefSet.findPreference(KEY_DND_LOGO_COLOR);
        	mdndLogoColor.setOnPreferenceChangeListener(this);
            intColor = Settings.System.getInt(getContentResolver(),
                Settings.System.STATUS_BAR_DND_LOGO_COLOR, 0xffffffff);
       		hexColor = String.format("#%08x", (0xffffffff & intColor));
            mdndLogoColor.setSummary(hexColor);
            mdndLogoColor.setNewPreviewColor(intColor);

            mdndLogoStyle = (ListPreference) findPreference(KEY_DND_LOGO_STYLE);
            int dndLogoStyle = Settings.System.getIntForUser(getContentResolver(),
                    Settings.System.STATUS_BAR_DND_LOGO_STYLE, 0,
                    UserHandle.USER_CURRENT);
            mdndLogoStyle.setValue(String.valueOf(dndLogoStyle));
            mdndLogoStyle.setSummary(mdndLogoStyle.getEntry());
            mdndLogoStyle.setOnPreferenceChangeListener(this);
    }

	@Override
    public boolean onPreferenceChange(Preference preference, Object newValue) {
        if (preference == mdndLogoColor) {
            String hex = ColorPickerPreference.convertToARGB(
                    Integer.valueOf(String.valueOf(newValue)));
            preference.setSummary(hex);
            int intHex = ColorPickerPreference.convertToColorInt(hex);
            Settings.System.putInt(getContentResolver(),
                    Settings.System.STATUS_BAR_DND_LOGO_COLOR, intHex);
            return true;
        } else if (preference == mdndLogoStyle) {
                int dndLogoStyle = Integer.valueOf((String) newValue);
                int index = mdndLogoStyle.findIndexOfValue((String) newValue);
                Settings.System.putIntForUser(
                        getContentResolver(), Settings.System.STATUS_BAR_DND_LOGO_STYLE, dndLogoStyle,
                        UserHandle.USER_CURRENT);
                mdndLogoStyle.setSummary(
                        mdndLogoStyle.getEntries()[index]);
                return true;
        }
        return false;
    }


    @Override
    protected int getMetricsCategory() {
        return MetricsEvent.REDEFINITION;
    }

    @Override
    public void onResume() {
        super.onResume();
    }

}
