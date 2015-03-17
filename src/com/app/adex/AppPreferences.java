package com.app.adex;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;

public class AppPreferences {
    private static final String APP_SHARED_PREFS = "Adex";

    private SharedPreferences shared_preferences;
    private SharedPreferences.Editor shared_preferences_editor;

    public AppPreferences(Context context) {
        this.shared_preferences = context.getSharedPreferences(APP_SHARED_PREFS, Activity.MODE_PRIVATE);
        this.shared_preferences_editor = shared_preferences.edit();
    }

    public void setFirstRun(boolean isFirstRun) {
        shared_preferences_editor.putBoolean("FirstRun", isFirstRun);
        shared_preferences_editor.commit();
}

    public boolean getFirstRun() {
        return shared_preferences.getBoolean("FirstRun", true);
    }

    public void setSettingVal(int settingVal) {
        shared_preferences_editor.putInt("SettingVal", settingVal);
        shared_preferences_editor.commit();
    }

    public int getSettingVal() {
        return shared_preferences.getInt("SettingVal", 0);
    }
}
