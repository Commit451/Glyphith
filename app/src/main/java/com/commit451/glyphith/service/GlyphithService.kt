package com.commit451.glyphith.service

import android.app.*
import android.content.Context
import android.content.Intent
import android.graphics.drawable.Icon
import android.os.IBinder
import android.os.PowerManager
import com.commit451.glyphith.MainActivity
import com.commit451.glyphith.R
import com.commit451.glyphith.api.Glyph
import com.commit451.glyphith.data.PatternLoader
import com.commit451.glyphith.data.Prefs
import kotlinx.coroutines.*
import timber.log.Timber
import java.util.concurrent.TimeUnit


/**
 * https://robertohuertas.com/2019/06/29/android_foreground_services/
 */
class GlyphithService : Service() {

    private var isServiceStarted = false
    private var restInterval = 10L

    override fun onBind(intent: Intent): IBinder? {
        log("Some component wants to bind with the service")
        // We don't provide binding, so return null
        return null
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        log("onStartCommand executed with startId: $startId")
        if (intent != null) {
            val action = intent.action
            log("using an intent with action $action")
            when (action) {
                ServiceAction.Start.name -> startService()
                ServiceAction.Stop.name -> stopService()
                ServiceAction.Modified.name -> readModifiedSettings()
                else -> log("This should never happen. No action in the received intent")
            }
        } else {
            log(
                "with a null intent. It has been probably restarted by the system."
            )
        }
        // by returning this we make sure the service is restarted if the system kills the service
        return START_STICKY
    }

    override fun onCreate() {
        super.onCreate()
        log("The service has been created".uppercase())
        readModifiedSettings()
        val notification = createNotification()
        startForeground(1, notification)
    }

    override fun onDestroy() {
        super.onDestroy()
        log("The service has been destroyed".uppercase())
    }

    private fun startService() {
        if (isServiceStarted) return
        log("Starting the foreground service task")
        isServiceStarted = true

        launch()
    }

    private fun stopService() {
        log("Stopping the foreground service")
        try {
            stopForeground(STOP_FOREGROUND_REMOVE)
            stopSelf()
        } catch (e: Exception) {
            log("Service stopped without being started: ${e.message}")
        }
        isServiceStarted = false
    }

    @OptIn(DelicateCoroutinesApi::class)
    private fun launch() {
        // we're starting a loop in a coroutine
        GlobalScope.launch(Dispatchers.IO) {
            while (isServiceStarted) {
                launch(Dispatchers.Main) {
                    val powerManager = getSystemService(POWER_SERVICE) as PowerManager
                    val isScreenAwake = powerManager.isInteractive
                    if (isScreenAwake) {
                        Glyph.animate()
                    }
                }
                delay(TimeUnit.SECONDS.toMillis(restInterval))
            }
            log("End of the loop for the service")
        }
    }

    private fun readModifiedSettings() {
        log("Modifying settings for service")
        restInterval = Prefs.restIntervalSeconds.toLong()
        val pattern = PatternLoader.currentPattern()
        Glyph.setPattern(pattern)
    }

    private fun createNotification(): Notification {
        val notificationChannelId = "ENDLESS SERVICE CHANNEL"

        val notificationManager =
            getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        val channel = NotificationChannel(
            notificationChannelId,
            "Glyphith service",
            NotificationManager.IMPORTANCE_LOW
        ).apply {
            description = "Notifies you that the service is running to control the Glyph lights"
        }
        notificationManager.createNotificationChannel(channel)

        val pendingIntent = Intent(this, MainActivity::class.java).let {
            PendingIntent.getActivity(this, 0, it, PendingIntent.FLAG_IMMUTABLE)
        }

        val builder: Notification.Builder = Notification.Builder(
            this,
            notificationChannelId
        )

        val pendingStopIntent = Intent(this, MainActivity::class.java).let {
            it.action = ServiceAction.Stop.name
            PendingIntent.getActivity(this, 0, it, PendingIntent.FLAG_IMMUTABLE)
        }

        val stopAction = Notification.Action.Builder(
            Icon.createWithResource(
                this,
                R.drawable.ic_baseline_flash_on_24
            ), "Stop", pendingStopIntent
        )
            .build()
        return builder
            .setContentTitle("Glyphith lights running")
            .setContentIntent(pendingIntent)
            .setSmallIcon(R.drawable.ic_baseline_flash_on_24)
            .addAction(stopAction)
            .build()
    }

    private fun log(message: String) {
        Timber.d(message)
    }
}