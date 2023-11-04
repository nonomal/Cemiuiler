package com.sevtinge.cemiuiler.module.hook.personalassistant

import android.content.res.Configuration
import com.github.kyuubiran.ezxhelper.ClassUtils.loadClass
import com.sevtinge.cemiuiler.module.base.BaseHook
import com.sevtinge.cemiuiler.utils.callMethod
import com.sevtinge.cemiuiler.utils.getIntField
import com.sevtinge.cemiuiler.utils.hookBeforeAllMethods
import com.sevtinge.cemiuiler.utils.hookBeforeMethod
import com.sevtinge.cemiuiler.utils.new
import com.sevtinge.cemiuiler.utils.replaceMethod
import com.sevtinge.cemiuiler.utils.setObjectField

object BlurPersonalAssistantBackGround : BaseHook() {
    private val deviceAdapter = loadClass("com.miui.personalassistant.device.DeviceAdapter")
    private val foldableDeviceAdapter by lazy {
        loadClass("com.miui.personalassistant.device.FoldableDeviceAdapter")
    }

    override fun init() {
        deviceAdapter.hookBeforeAllMethods("create") {
            it.result = foldableDeviceAdapter.new(it.args[0])
        }
        try {
            foldableDeviceAdapter.hookBeforeMethod("onEnter", Boolean::class.java) {
                it.thisObject.setObjectField("mScreenSize", 3)
            }
        } catch (e: ClassNotFoundException) {
            foldableDeviceAdapter.hookBeforeMethod("onOpened") {
                it.thisObject.setObjectField("mScreenSize", 3)
            }
        }
        foldableDeviceAdapter.hookBeforeMethod("onConfigurationChanged", Configuration::class.java) {
            it.thisObject.setObjectField("mScreenSize", 3)
        }
        foldableDeviceAdapter.replaceMethod("onScroll", Float::class.java) {
            val f = it.args[0] as Float
            val i = (f * 100.0f).toInt()
            val mCurrentBlurRadius: Int = it.thisObject.getIntField("mCurrentBlurRadius")
            if (mCurrentBlurRadius != i) {
                if (mCurrentBlurRadius <= 0 || i >= 0) {
                    it.thisObject.setObjectField("mCurrentBlurRadius", i)
                } else {
                    it.thisObject.setObjectField("mCurrentBlurRadius", 0)
                }
                it.thisObject.callMethod("blurOverlayWindow", mCurrentBlurRadius)
            }
        }
    }
}
