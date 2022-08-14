package com.example.template.adbSets

import android.app.Activity
import com.appsflyer.AppsFlyerConversionListener
import com.appsflyer.AppsFlyerLib

const val KEYKLUCH = "wfAv7XWMN2tsjXxETNSUY8"


fun launchApps(activity: Activity, cosmicApps: AppsFlyerConversionListener) {
    AppsFlyerLib.getInstance().init(KEYKLUCH, cosmicApps, activity)
    AppsFlyerLib.getInstance().start(activity)
}

fun getApp(): String = "myapp://"
fun getNull(): Any? = null
fun getTrue(): Boolean = true