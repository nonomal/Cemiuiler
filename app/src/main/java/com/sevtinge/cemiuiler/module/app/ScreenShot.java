package com.sevtinge.cemiuiler.module.app;

import android.text.TextUtils;

import com.sevtinge.cemiuiler.module.base.BaseModule;
import com.sevtinge.cemiuiler.module.base.CloseHostDir;
import com.sevtinge.cemiuiler.module.base.LoadHostDir;
import com.sevtinge.cemiuiler.module.hook.screenshot.DeviceShellCustomize;
import com.sevtinge.cemiuiler.module.hook.screenshot.SaveToPictures;
import com.sevtinge.cemiuiler.module.hook.screenshot.UnlockMinimumCropLimit;
import com.sevtinge.cemiuiler.module.hook.screenshot.UnlockPrivacyMarking;
import com.sevtinge.cemiuiler.module.hook.various.UnlockSuperClipboard;

public class ScreenShot extends BaseModule {

    @Override
    public void handleLoadPackage() {
        // dexKit load
        initHook(LoadHostDir.INSTANCE);
        initHook(UnlockMinimumCropLimit.INSTANCE, mPrefsMap.getBoolean("screenshot_unlock_minimum_crop_limit"));
        initHook(SaveToPictures.INSTANCE, mPrefsMap.getBoolean("screenshot_save_to_pictures"));
        initHook(DeviceShellCustomize.INSTANCE, !TextUtils.isEmpty(mPrefsMap.getString("screenshot_device_customize", "")));
        initHook(UnlockPrivacyMarking.INSTANCE, mPrefsMap.getBoolean("screenshot_unlock_privacy_marking"));
        // 超级剪切板
        initHook(UnlockSuperClipboard.INSTANCE, mPrefsMap.getBoolean("various_super_clipboard_enable"));
        // dexKit finish
        initHook(CloseHostDir.INSTANCE);
    }
}


