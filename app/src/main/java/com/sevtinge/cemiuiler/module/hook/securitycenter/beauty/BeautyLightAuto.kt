package com.sevtinge.cemiuiler.module.hook.securitycenter.beauty

import com.sevtinge.cemiuiler.module.base.BaseHook
import com.sevtinge.cemiuiler.utils.DexKit.addUsingStringsEquals
import com.sevtinge.cemiuiler.utils.DexKit.dexKitBridge
import com.sevtinge.cemiuiler.utils.log.XposedLogUtils
import de.robv.android.xposed.XC_MethodReplacement
import de.robv.android.xposed.XposedBridge

object BeautyLightAuto : BaseHook() {
    override fun init() {
        dexKitBridge.findMethod {
            matcher {
                addUsingStringsEquals("taoyao")
                returnType = "boolean"
            }
        }.forEach {
            if (!java.lang.String.valueOf(it).contains("<clinit>")) {
                val beautyLightAuto: java.lang.reflect.Method =
                    it.getMethodInstance(lpparam.classLoader)
                if (!java.lang.String.valueOf(it).contains(BeautyFace.beautyFace.toString())) {
                    XposedLogUtils.logI("beautyLightAuto method is $beautyLightAuto")
                    XposedBridge.hookMethod(
                        beautyLightAuto,
                        XC_MethodReplacement.returnConstant(true)
                    )
                }
            }
        }


        /*try {
            val result: List<DexMethodDescriptor> =
                java.util.Objects.requireNonNull<List<DexMethodDescriptor>>(
                    SecurityCenterDexKit.mSecurityCenterResultMap.get("BeautyLightAuto")
                )
            for (descriptor in result) {
                if (!java.lang.String.valueOf(descriptor).contains("<clinit>")) {
                    val beautyLightAuto: java.lang.reflect.Method =
                        descriptor.getMethodInstance(lpparam.classLoader)
                    if (beautyLightAuto.returnType == Boolean::class.javaPrimitiveType && !java.lang.String.valueOf(
                            descriptor
                        ).contains(BeautyFace.beautyFace.toString())
                    ) {
                        log("beautyLightAuto method is $beautyLightAuto")
                        XposedBridge.hookMethod(
                            beautyLightAuto,
                            XC_MethodReplacement.returnConstant(true)
                        )
                    }
                }
            }
        } catch (e: Throwable) {
            e.printStackTrace()
        }*/
    }
}
