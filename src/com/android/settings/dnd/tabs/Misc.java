/*
 * Copyright (C) 2017 DarkNess reDefined
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

package com.android.settings.dnd.tabs;

import android.content.Context;
import android.content.ContentResolver;
import android.content.res.Resources;
import android.os.Bundle;
import android.os.SystemProperties;
import android.os.UserHandle;
import android.provider.Settings;
import android.support.v7.preference.PreferenceCategory;
import android.support.v7.preference.ListPreference;
import android.support.v7.preference.Preference;
import android.support.v7.preference.PreferenceScreen;
import android.support.v7.preference.Preference.OnPreferenceChangeListener;
import android.support.v14.preference.SwitchPreference;

import com.android.settings.R;
import com.android.settings.SettingsPreferenceFragment;

import com.android.internal.logging.MetricsProto.MetricsEvent;

import com.android.settings.Utils;

public class Misc extends SettingsPreferenceFragment implements
        Preference.OnPreferenceChangeListener {

        private static final String KEY_CAMERA_SOUNDS = "camera_sounds";
        private static final String PROP_CAMERA_SOUND = "persist.sys.camera-sound";

        private SwitchPreference mCameraSounds;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        addPreferencesFromResource(R.xml.dnd_misc_tab);
        ContentResolver resolver = getActivity().getContentResolver();

            mCameraSounds = (SwitchPreference) findPreference(KEY_CAMERA_SOUNDS);
            mCameraSounds.setChecked(SystemProperties.getBoolean(PROP_CAMERA_SOUND, true));
            mCameraSounds.setOnPreferenceChangeListener(this);

   }

    @Override
    protected int getMetricsCategory() {
        return MetricsEvent.REDEFINITION;
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
    }

     public boolean onPreferenceChange(Preference preference, Object objValue) {
       final String key = preference.getKey();
            if (preference == mCameraSounds) {
               if (KEY_CAMERA_SOUNDS.equals(key)) {
                   if ((Boolean) objValue) {
                       SystemProperties.set(PROP_CAMERA_SOUND, "1");
                   } else {
                       SystemProperties.set(PROP_CAMERA_SOUND, "0");
                   }
                }
       return true;
    }
       return false;
    }
}
