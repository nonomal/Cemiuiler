package com.sevtinge.cemiuiler.module.hook.home.recent

import android.annotation.SuppressLint
import android.app.ActivityManager
import android.content.Context
import android.text.format.Formatter
import android.widget.TextView
import com.github.kyuubiran.ezxhelper.ClassUtils.loadClass
import com.github.kyuubiran.ezxhelper.HookFactory.`-Static`.createHook
import com.github.kyuubiran.ezxhelper.finders.ConstructorFinder.`-Static`.constructorFinder
import com.github.kyuubiran.ezxhelper.finders.MethodFinder.`-Static`.methodFinder
import com.sevtinge.cemiuiler.module.base.BaseHook
import com.sevtinge.cemiuiler.utils.api.IS_TABLET
import com.sevtinge.cemiuiler.utils.api.isPad
import com.sevtinge.cemiuiler.utils.getObjectField

// @SuppressLint("StaticFieldLeak")
object RealMemory : BaseHook() {
    @SuppressLint("DiscouragedApi")
    override fun init() {
        lateinit var context: Context
        var memoryInfo1StringId: Int? = null
        var memoryInfo2StringId: Int? = null

        fun Any.formatSize(): String = Formatter.formatFileSize(context, this as Long)

        val recentContainerClass = loadClass(
            when (IS_TABLET) {
                false -> "com.miui.home.recents.views.RecentsContainer"
                true -> "com.miui.home.recents.views.RecentsDecorations"
            }
        )

        recentContainerClass.declaredConstructors.constructorFinder()
            .filterByParamCount(2)
            .first().createHook {
                after {
                    context = it.args[0] as Context
                    memoryInfo1StringId = context.resources.getIdentifier(
                        "status_bar_recent_memory_info1",
                        "string",
                        "com.miui.home"
                    )
                    memoryInfo2StringId = context.resources.getIdentifier(
                        "status_bar_recent_memory_info2",
                        "string",
                        "com.miui.home"
                    )
                }
            }

        recentContainerClass.methodFinder()
            .filterByName("refreshMemoryInfo")
            .first().createHook {
                before {
                    it.result = null
                    val memoryInfo = ActivityManager.MemoryInfo()
                    val activityManager =
                        context.getSystemService(Context.ACTIVITY_SERVICE) as ActivityManager
                    activityManager.getMemoryInfo(memoryInfo)
                    val totalMem = memoryInfo.totalMem.formatSize()
                    val availMem = memoryInfo.availMem.formatSize()
                    (it.thisObject.getObjectField("mTxtMemoryInfo1") as TextView).text =
                        context.getString(memoryInfo1StringId!!, availMem, totalMem)
                    (it.thisObject.getObjectField("mTxtMemoryInfo2") as TextView).text =
                        context.getString(memoryInfo2StringId!!, availMem, totalMem)
                }
            }
    }
}
