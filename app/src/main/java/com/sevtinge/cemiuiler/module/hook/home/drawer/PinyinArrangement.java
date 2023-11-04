package com.sevtinge.cemiuiler.module.hook.home.drawer;

import android.app.Activity;

import com.github.promeg.pinyinhelper.Pinyin;
import com.sevtinge.cemiuiler.module.base.BaseHook;

import java.util.Locale;

import de.robv.android.xposed.XposedHelpers;

public class PinyinArrangement extends BaseHook {
    Locale locale;
    Activity activity;

    @Override
    public void init() {
        findAndHookMethod("com.miui.home.launcher.compat.AlphabeticIndexCompat",
            "computeSectionName", CharSequence.class, new MethodHook() {
                @Override
                protected void before(MethodHookParam param) {
                    CharSequence charSequence = (CharSequence) param.args[0];
                    String bucketLabel;
                    String trim = (String) XposedHelpers.callStaticMethod(findClass("com.miui.home.launcher.common.Utilities"), "trim", charSequence);
                    if (charSequence.length() > 0 && Pinyin.isChinese(charSequence.charAt(0))) {
                        bucketLabel = String.valueOf(Pinyin.toPinyin(charSequence.toString(), "").charAt(0));
                    } else {
                        Object o = XposedHelpers.getObjectField(param.thisObject, "mBaseIndex");
                        bucketLabel = (String) XposedHelpers.callMethod(o, "getBucketLabel", XposedHelpers.callMethod(o, "getBucketIndex", trim));
                    }
                    // logE("bucketLabel: " + bucketLabel + " trim1: " + trim);
                    String trim2 = (String) XposedHelpers.callStaticMethod(findClass("com.miui.home.launcher.common.Utilities"), "trim", bucketLabel);
                    // logE("trim2: " + trim2);
                    if (!trim2.isEmpty() || trim.length() <= 0) {
                        param.setResult(bucketLabel);
                        return;
                    }
                    int codePointAt = trim.codePointAt(0);
                    param.setResult(Character.isDigit(codePointAt) ? "…" : Character.isLetter(codePointAt) ? (String) XposedHelpers.getObjectField(param.thisObject, "mDefaultMiscLabel") : "∙");
                }
            }
        );

        hookAllMethods("com.miui.home.launcher.allapps.BaseAlphabeticalAppsList",
            "onAppsUpdated", new MethodHook() {
                @Override
                protected void before(MethodHookParam param) {
                    activity = (Activity) XposedHelpers.getObjectField(param.thisObject, "mLauncher");
                    locale = activity.getResources().getConfiguration().locale;
                    activity.getResources().getConfiguration().setLocale(Locale.SIMPLIFIED_CHINESE);
                }

                @Override
                protected void after(MethodHookParam param) {
                    activity.getResources().getConfiguration().setLocale(locale);
                }
            }
        );

    }
}
