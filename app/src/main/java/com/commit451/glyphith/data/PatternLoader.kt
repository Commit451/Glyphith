package com.commit451.glyphith.data

import android.animation.ValueAnimator
import android.content.Context
import android.content.res.Resources
import com.commit451.glyphith.R
import com.commit451.glyphith.api.Glyph
import com.opencsv.CSVReader
import java.io.InputStreamReader
import kotlin.math.roundToInt

object PatternLoader {

    const val DefaultPatternName = "chill"

    var patterns = emptyList<LightPattern>()
        private set

    fun init(context: Context) {
        val resources = context.resources
        patterns = listOf(
            LightPattern(DefaultPatternName, loadAnimation(resources, R.raw.chill)),
            LightPattern("chaos", loadAnimation(resources, R.raw.chaos)),
            LightPattern("oi!", loadAnimation(resources, R.raw.oi)),
            LightPattern("bulb one", loadAnimation(resources, R.raw.bulbone)),
        )
    }

    fun currentPattern(): LightPattern {
        return patterns.find { it.name == Prefs.patternName }
            ?: patterns.first()
    }

    private fun loadAnimation(resources: Resources, rawRes: Int): List<LightPlusAnimator> {
        val stream = resources.openRawResource(rawRes)
        val reader = CSVReader(InputStreamReader(stream))
        val lines = reader.readAll()

        return lines.map { config ->
            val light = config.first()
            val delay = config[1].toLong()
            val duration = config[2].toLong()
            val percent = config.last().toFloat()
            val va = ValueAnimator.ofInt(0, (Glyph.MaxBrightness * percent).roundToInt(), 0)
            va.duration = duration
            va.startDelay = delay
            LightPlusAnimator(light, va)
        }
    }
}