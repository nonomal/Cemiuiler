package com.sevtinge.cemiuiler.module.hook.systemui.statusbar.icon.all

import android.view.View
import android.widget.FrameLayout
import android.widget.ImageView
import android.widget.TextView
import com.github.kyuubiran.ezxhelper.ClassUtils.loadClass
import com.github.kyuubiran.ezxhelper.HookFactory.`-Static`.createHook
import com.github.kyuubiran.ezxhelper.finders.MethodFinder.`-Static`.methodFinder
import com.sevtinge.cemiuiler.module.base.BaseHook
import com.sevtinge.cemiuiler.utils.devicesdk.isAndroidR
import com.sevtinge.cemiuiler.utils.devicesdk.isMoreAndroidVersion
import com.sevtinge.cemiuiler.utils.getObjectField
import com.sevtinge.cemiuiler.utils.getObjectFieldAs

object HideBatteryIcon : BaseHook() {
    override fun init() {
        val mBatteryMeterViewClass = when {
            isAndroidR() -> loadClass("com.android.systemui.MiuiBatteryMeterView")
            isMoreAndroidVersion(31) -> loadClass("com.android.systemui.statusbar.views.MiuiBatteryMeterView")
            else -> null
        }

        mBatteryMeterViewClass!!.methodFinder().first {
            name == "updateResources"
        }.createHook {
            after { param ->
                // 隐藏电池图标
                if (mPrefsMap.getBoolean("system_ui_status_bar_battery_icon")) {
                    (param.thisObject?.getObjectFieldAs<ImageView>("mBatteryIconView"))?.visibility =
                        View.GONE
                    if (param.thisObject?.getObjectField("mBatteryStyle") == 1) {
                        (param.thisObject?.getObjectFieldAs<FrameLayout>("mBatteryDigitalView"))?.visibility =
                            View.GONE
                    }
                }
                // 隐藏电池百分号
                if (mPrefsMap.getBoolean("system_ui_status_bar_battery_percent") ||
                    mPrefsMap.getBoolean("system_ui_status_bar_battery_percent_mark")
                ) {
                    (param.thisObject?.getObjectFieldAs<TextView>("mBatteryPercentMarkView"))?.textSize =
                        0F
                }
                // 隐藏电池内的百分比
                if (mPrefsMap.getBoolean("system_ui_status_bar_battery_percent")) {
                    (param.thisObject?.getObjectFieldAs<TextView>("mBatteryPercentView"))?.textSize =
                        0F
                    (param.thisObject?.getObjectFieldAs<TextView>("mBatteryTextDigitView"))?.textSize =
                        0F
                }
            }
        }

        mBatteryMeterViewClass.methodFinder().first {
            name == "updateChargeAndText"
        }.createHook {
            after { param ->
                // 隐藏电池充电图标
                if (mPrefsMap.getBoolean("system_ui_status_bar_battery_charging")) {
                    (param.thisObject?.getObjectFieldAs<ImageView>("mBatteryChargingInView"))?.visibility =
                        View.GONE
                    (param.thisObject?.getObjectFieldAs<ImageView>("mBatteryChargingView"))?.visibility =
                        View.GONE
                }
            }
        }
    }

}
