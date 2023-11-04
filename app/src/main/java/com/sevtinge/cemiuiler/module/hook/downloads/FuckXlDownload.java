package com.sevtinge.cemiuiler.module.hook.downloads;

import android.os.Environment;

import com.sevtinge.cemiuiler.module.base.BaseHook;
import com.sevtinge.cemiuiler.utils.log.XposedLogUtils;

import java.io.File;
import java.io.FileNotFoundException;

import de.robv.android.xposed.XC_MethodHook;
import de.robv.android.xposed.XposedHelpers;

public class FuckXlDownload extends BaseHook {
    private static final String TARGET_PACKAGE = "com.android.providers.downloads";
    private static final File TARGET_PATH = new File(Environment.getExternalStorageDirectory(), ".xlDownload").getAbsoluteFile();

    @Override
    public void init() {
        if (!TARGET_PACKAGE.equals(lpparam.packageName)) return;
        XposedLogUtils.logI("Target path = " + TARGET_PATH);
        XposedHelpers.findAndHookMethod(File.class, "mkdirs", new XC_MethodHook() {
            @Override
            protected void beforeHookedMethod(MethodHookParam param) {
                final boolean isXlDownload = ((File) param.thisObject).getAbsoluteFile().equals(TARGET_PATH);
                if (isXlDownload) {
                    XposedLogUtils.logI("blocked");
                    param.setThrowable(new FileNotFoundException("blocked"));
                }
            }
        });
    }
}
