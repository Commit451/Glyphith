package com.commit451.glyphith.data

import android.content.Context
import android.content.SharedPreferences

object Prefs {

    private const val FileName = "glyphith_prefs"

    private const val PrefAlwaysOn = "always_on"
    private const val PrefSeenIntro = "seen_intro"
    private const val PrefRestIntervalSeconds = "rest_interval_seconds"

    private lateinit var sharedPreferences: SharedPreferences

    fun init(context: Context) {
        sharedPreferences = context.getSharedPreferences(FileName, Context.MODE_PRIVATE)
    }

    var hasSeenIntro: Boolean
        get() = sharedPreferences.getBoolean(PrefSeenIntro, false)
        set(value) {
            with(sharedPreferences.edit()) {
                putBoolean(PrefSeenIntro, value)
                apply()
            }
        }

    var isAlwaysOn: Boolean
        get() = sharedPreferences.getBoolean(PrefAlwaysOn, false)
        set(value) {
            with(sharedPreferences.edit()) {
                putBoolean(PrefAlwaysOn, value)
                apply()
            }
        }

    var restIntervalSeconds: Int
        get() = sharedPreferences.getInt(PrefRestIntervalSeconds, 10)
        set(value) {
            with(sharedPreferences.edit()) {
                putInt(PrefRestIntervalSeconds, value)
                apply()
            }
        }
}