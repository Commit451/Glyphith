package com.commit451.glyphith.util

import android.app.ActivityManager
import android.content.Context
import android.content.Intent
import com.commit451.glyphith.service.GlyphithService
import com.commit451.glyphith.service.ServiceAction
import java.util.concurrent.TimeUnit

object Util {

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
        serviceAction(context, if (start) ServiceAction.Start else ServiceAction.Stop)
    }

    fun notifyServiceModified(context: Context) {
        serviceAction(context, ServiceAction.Modified)
    }

    private fun serviceAction(context: Context, action: ServiceAction) {
        Intent(context, GlyphithService::class.java).also {
            it.action = action.name
            context.startForegroundService(it)
        }
    }
}