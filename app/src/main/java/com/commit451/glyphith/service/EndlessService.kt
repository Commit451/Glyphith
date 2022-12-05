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
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import timber.log.Timber
import java.util.concurrent.TimeUnit


/**
 * https://robertohuertas.com/2019/06/29/android_foreground_services/
 */
class EndlessService : Service() {

    companion object {
        private const val SecondsBetween = 10L
    }

    private var wakeLock: PowerManager.WakeLock? = null
    private var isServiceStarted = false
    private var isAlwaysOn = false

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
                Actions.START.name -> startService()
                Actions.STOP.name -> stopService()
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
        isAlwaysOn = Prefs.isAlwaysOn
        val notification = createNotification()
        startForeground(1, notification)
        val patterns = PatternLoader.loadPatterns(resources)
        Glyph.setPattern(patterns.first())
    }

    override fun onDestroy() {
        super.onDestroy()
        log("The service has been destroyed".uppercase())
    }

    private fun startService() {
        if (isServiceStarted) return
        log("Starting the foreground service task")
        isServiceStarted = true
        //setServiceState(this, ServiceState.STARTED)

        // we need this lock so our service gets not affected by Doze Mode
//        wakeLock =
//            (getSystemService(Context.POWER_SERVICE) as PowerManager).run {
//                newWakeLock(PowerManager.PARTIAL_WAKE_LOCK, "EndlessService::lock").apply {
//                    acquire()
//                }
//            }

        // we're starting a loop in a coroutine
        GlobalScope.launch(Dispatchers.IO) {
            while (isServiceStarted) {
                launch(Dispatchers.Main) {
                    if (isAlwaysOn) {
                        Glyph.animate()
                    } else {
                        val powerManager = getSystemService(POWER_SERVICE) as PowerManager
                        val isScreenAwake = powerManager.isInteractive
                        if (isScreenAwake) {
                            Glyph.animate()
                        }
                    }
                }
                delay(TimeUnit.SECONDS.toMillis(SecondsBetween))
            }
            log("End of the loop for the service")
        }
    }

    private fun stopService() {
        log("Stopping the foreground service")
        try {
            wakeLock?.let {
                if (it.isHeld) {
                    it.release()
                }
            }
            stopForeground(STOP_FOREGROUND_REMOVE)
            stopSelf()
        } catch (e: Exception) {
            log("Service stopped without being started: ${e.message}")
        }
        isServiceStarted = false
        //setServiceState(this, ServiceState.STOPPED)
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
            it.action = Actions.STOP.name
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