package com.sevtinge.cemiuiler.module.hook.home.recent

import android.view.View
import android.widget.TextView
import com.sevtinge.cemiuiler.module.base.BaseHook
import com.sevtinge.cemiuiler.utils.findClass
import com.sevtinge.cemiuiler.utils.getObjectField
import com.sevtinge.cemiuiler.utils.hookAfterMethod

object HideFreeform : BaseHook() {
    override fun init() {

        val recentsContainerClass = "com.miui.home.recents.views.RecentsContainer".findClass()
        if (mPrefsMap.getBoolean("home_recent_hide_freeform")) {
            recentsContainerClass.hookAfterMethod(
                "onFinishInflate"
            ) {
                val mTitle = it.thisObject.getObjectField("mTxtSmallWindow") as TextView
                mTitle.visibility = View.GONE
            }
        }
    }
}
