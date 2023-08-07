package com.vvechirko.nav_compose

import android.content.Context
import android.content.Intent

fun Context.restartApp() {
    val intent: Intent = packageManager.getLaunchIntentForPackage(packageName)!!
    startActivity(Intent.makeRestartActivityTask(intent.component))
    Runtime.getRuntime().exit(0)
}