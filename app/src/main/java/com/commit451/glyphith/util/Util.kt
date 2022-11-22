package com.commit451.glyphith.util

import android.app.ActivityManager
import android.content.Context
import android.content.Intent
import com.commit451.glyphith.service.Actions
import com.commit451.glyphith.service.EndlessService
import java.util.concurrent.TimeUnit

object Util {

    private const val TIME_FORMAT = "%02d:%02d"

    @Suppress("DEPRECATION")
    fun isMyServiceRunning(context: Context, serviceClass: Class<*>): Boolean {
        val manager = context.getSystemService(Context.ACTIVITY_SERVICE) as ActivityManager
        for (service in manager.getRunningServices(Int.MAX_VALUE)) {
            if (serviceClass.name == service.service.className) {
                return true
            }
        }
        return false
    }

    fun service(context: Context, start: Boolean) {
        Intent(context, EndlessService::class.java).also {
            it.action = if (start) Actions.START.name else Actions.STOP.name
            context.startForegroundService(it)
        }
    }


    // convert time to milli seconds
    fun Long.formatTime(): String = String.format(
        TIME_FORMAT,
        TimeUnit.MILLISECONDS.toMinutes(this),
        TimeUnit.MILLISECONDS.toSeconds(this) % 60
    )
}