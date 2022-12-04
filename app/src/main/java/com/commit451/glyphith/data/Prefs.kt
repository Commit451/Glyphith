package com.commit451.glyphith.data

import android.content.Context
import android.content.SharedPreferences

object Prefs {

    private const val FileName = "glyphith_prefs"

    private const val PrefAlwaysOn = "always_on"
    private const val PrefSeenIntro = "seen_intro"

    private lateinit var sharedPreferences: SharedPreferences

    fun init(context: Context) {
        sharedPreferences = context.getSharedPreferences(FileName, Context.MODE_PRIVATE)
    }

    var hasSeenIntro: Boolean
        get() = sharedPreferences.getBoolean(PrefSeenIntro, false)
        set(enabled) {
            with(sharedPreferences.edit()) {
                putBoolean(PrefSeenIntro, enabled)
                apply()
            }
        }

    var isAlwaysOn: Boolean
        get() = sharedPreferences.getBoolean(PrefAlwaysOn, false)
        set(enabled) {
            with(sharedPreferences.edit()) {
                putBoolean(PrefAlwaysOn, enabled)
                apply()
            }
        }
}