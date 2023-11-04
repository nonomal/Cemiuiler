package com.sevtinge.cemiuiler;

import android.content.Context;
import android.os.Build;

import com.sevtinge.cemiuiler.utils.PrefsUtils;

public class Application extends android.app.Application {

    @Override
    protected void attachBaseContext(Context base) {
        PrefsUtils.mSharedPreferences = PrefsUtils.getSharedPrefs(base);
        super.attachBaseContext(base);
    }

    @Override
    public void onCreate() {
        super.onCreate();
    }
}
