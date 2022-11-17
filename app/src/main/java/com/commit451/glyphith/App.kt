package com.commit451.glyphith

import android.app.Application
import com.commit451.glyphith.data.Prefs
import timber.log.Timber

class App: Application() {

    override fun onCreate() {
        super.onCreate()
        Prefs.init(this)
        Timber.plant(Timber.DebugTree())
    }
}