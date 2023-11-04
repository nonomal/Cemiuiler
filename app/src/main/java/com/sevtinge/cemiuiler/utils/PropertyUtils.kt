package com.sevtinge.cemiuiler.utils

import android.annotation.SuppressLint
import com.sevtinge.cemiuiler.utils.log.XposedLogUtils.logW
import java.lang.reflect.Method

object PropertyUtils {
    private val get: Method by lazy {
        @SuppressLint("PrivateApi")
        val cls = Class.forName("android.os.SystemProperties")
        cls.getDeclaredMethod("get", String::class.java, String::class.java)
    }

    operator fun get(prop: String, defaultValue: String?): String? {
        kotlin.runCatching {
            get.invoke(null, prop, defaultValue) as String?
        }.onFailure {
            logW("", it)
        }.onSuccess {
            return it
        }
        return defaultValue
    }
}
