package com.sevtinge.cemiuiler.module.hook.systemframework

import com.github.kyuubiran.ezxhelper.ClassUtils.loadClass
import com.github.kyuubiran.ezxhelper.HookFactory.`-Static`.createHook
import com.github.kyuubiran.ezxhelper.finders.MethodFinder.`-Static`.methodFinder
import com.sevtinge.cemiuiler.module.base.BaseHook
import com.sevtinge.cemiuiler.utils.log.XposedLogUtils

object MultiFreeFormSupported : BaseHook() {
    override fun init() {
        runCatching {
            if (!mPrefsMap.getBoolean("system_framework_freeform_recents_to_small_freeform")) {
                loadClass("android.util.MiuiMultiWindowUtils").methodFinder().first {
                    name == "multiFreeFormSupported"
                }.createHook {
                    before {
                        val ex = Throwable()
                        val stackTrace = ex.stackTrace
                        var mResult = true
                        for (i in stackTrace) {
                            if (i.className == "com.android.server.wm.MiuiFreeFormGestureController\$FreeFormReceiver") {
                                mResult = false
                                break
                            }
                        }
                        it.result = mResult
                    }
                }
                XposedLogUtils.logI("Hook with recents_to_small_freeform success!")
            } else {
                loadClass("android.util.MiuiMultiWindowUtils").methodFinder().first {
                    name == "multiFreeFormSupported"
                }.createHook {
                    returnConstant(true)
                }
                XposedLogUtils.logI("Hook success!")
            }
        }
    }

}
