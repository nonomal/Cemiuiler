package com.sevtinge.cemiuiler.ui.fragment

import android.content.Intent
import android.net.Uri
import com.sevtinge.cemiuiler.BuildConfig
import com.sevtinge.cemiuiler.R
import com.sevtinge.cemiuiler.ui.fragment.base.SettingsPreferenceFragment
import com.sevtinge.hyperceiler.expansionpacks.utils.ClickCountsUtils;
import moralnorm.preference.Preference
import moralnorm.preference.SwitchPreference
import java.util.Calendar
import kotlin.math.abs

class AboutFragment : SettingsPreferenceFragment() {

    private var lIIlIll = 100 ushr 7
    private val lIIlIlI = 100 ushr 6

    override fun getContentResId(): Int {
        return R.xml.prefs_about
    }

    override fun initPrefs() {
        val lIIlllI = ClickCountsUtils.getClickCounts()
        val lIIllII = findPreference<Preference>("prefs_key_various_enable_super_function")
        val mQQGroup = findPreference<Preference>("prefs_key_about_join_qq_group")

        lIIllII?.title = BuildConfig.VERSION_NAME + " | " + BuildConfig.BUILD_TYPE

        lIIllII?.onPreferenceClickListener = Preference.OnPreferenceClickListener { lIIllll->
            if (BuildConfig.BUILD_TYPE.contains("debug")) lIIllII?.title = BuildConfig.VERSION_NAME + " | " + BuildConfig.BUILD_TYPE + " | $lIIlllI"
            lIIllll as SwitchPreference
            lIIllll.isChecked = !(lIIllll.isChecked)
            lIIlIll++
            if (BuildConfig.BUILD_TYPE.contains("debug")) lIIllII?.title = BuildConfig.VERSION_NAME + " | " + BuildConfig.BUILD_TYPE + " | $lIIlIll/$lIIlllI"
            if (lIIllll.isChecked) {
                if (lIIlIll >= lIIlIlI) {
                    lIIllll.isChecked = !(lIIllll.isChecked)
                    if (BuildConfig.BUILD_TYPE.contains("debug")) lIIllII?.title = BuildConfig.VERSION_NAME + " | " + BuildConfig.BUILD_TYPE + " | HF OFF"
                    lIIlIll = 100 ushr 8
                }
            } else if (lIIlIll >= lIIlllI) {
                lIIllll.isChecked = !(lIIllll.isChecked)
                if (BuildConfig.BUILD_TYPE.contains("debug")) lIIllII?.title = BuildConfig.VERSION_NAME + " | " + BuildConfig.BUILD_TYPE + " | HF ON"
                lIIlIll = 100 ushr 8
            }
            false
        }

        mQQGroup?.onPreferenceClickListener = Preference.OnPreferenceClickListener {
            "MF68KGcOGYEfMvkV_htdyT6D6C13We_r".joinQQGroup() //&authKey=du488g%2FRPdQ%2FaUq0IKuDLvK24mEmbpRidqHGE6qqv3wpa1lbUa6Vi7JJ4YxWe7s5&noverify=0&group_code=247909573
            true
        }
    }

    /**
     * 调用 joinQQGroup() 即可发起手Q客户端申请加群
     * @param this@joinQQGroup 由官网生成的key
     * @return 返回true表示呼起手Q成功，返回false表示呼起失败
     */
    private fun String.joinQQGroup(): Boolean {
        val intent = Intent()
        intent.data =
            Uri.parse("mqqopensdkapi://bizAgent/qm/qr?url=http%3A%2F%2Fqm.qq.com%2Fcgi-bin%2Fqm%2Fqr%3Ffrom%3Dapp%26p%3Dandroid%26jump_from%3Dwebapi%26k%3D${this}") // https://jq.qq.com/?_wv=1027&k=EsyE1RhL
        // 此Flag可根据具体产品需要自定义，如设置，则在加群界面按返回，返回手Q主界面，不设置，按返回会返回到呼起产品界面    //intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        return try {
            startActivity(intent)
            true
        } catch (e: Exception) {
            // 未安装手Q或安装的版本不支持
            false
        }
    }
}
