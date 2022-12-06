package com.commit451.glyphith.data

import android.content.Context
import android.content.SharedPreferences

object Prefs {

    private const val FileName = "glyphith_prefs"

    private const val PrefPatternName = "pattern_name"
    private const val PrefSeenIntro = "seen_intro"
    private const val PrefRestIntervalSeconds = "rest_interval_seconds"

    private lateinit var sharedPreferences: SharedPreferences

    fun init(context: Context) {
        sharedPreferences = context.getSharedPreferences(FileName, Context.MODE_PRIVATE)
    }

    var patternName: String
        get() = sharedPreferences.getString(PrefPatternName, PatternLoader.DefaultPatternName)
            ?: PatternLoader.DefaultPatternName
        set(value) {
            with(sharedPreferences.edit()) {
                putString(PrefPatternName, value)
                apply()
            }
        }

    var hasSeenIntro: Boolean
        get() = sharedPreferences.getBoolean(PrefSeenIntro, false)
        set(value) {
            with(sharedPreferences.edit()) {
                putBoolean(PrefSeenIntro, value)
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