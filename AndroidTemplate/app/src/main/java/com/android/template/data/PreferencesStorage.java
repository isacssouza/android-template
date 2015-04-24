package com.android.template.data;

import android.content.SharedPreferences;

import javax.inject.Inject;

/**
 * A storage that uses the SharedPreferences.
 * Created by isacssouza on 4/18/15.
 */
public class PreferencesStorage {
    private static final String KEY_REG_ID = "KEY_REG_ID";
    private static final String KEY_APP_VERSION = "KEY_APP_VERSION";
    private static final String PREF_USER_LEARNED_DRAWER = "navigation_drawer_learned";

    private SharedPreferences mSharedPreferences;

    @Inject
    public PreferencesStorage(SharedPreferences sharedPreferences) {
        mSharedPreferences = sharedPreferences;
    }

    public String getRegId() {
        return mSharedPreferences.getString(KEY_REG_ID, null);
    }
    public void setRegId(String newRegId) {
        mSharedPreferences.edit()
                .putString(KEY_REG_ID, newRegId)
                .apply();
    }

    public int getAppVersion() {
        return mSharedPreferences.getInt(KEY_APP_VERSION, Integer.MIN_VALUE);
    }
    public void setAppVersion(int newAppVersion) {
        mSharedPreferences.edit()
                .putInt(KEY_APP_VERSION, newAppVersion)
                .apply();
    }

    public boolean getUserLearnedDrawer() {
        return mSharedPreferences.getBoolean(PREF_USER_LEARNED_DRAWER, false);
    }
    public void setUserLearnedDrawer(boolean value) {
        mSharedPreferences.edit()
                .putBoolean(PREF_USER_LEARNED_DRAWER, value)
                .apply();
    }
}
