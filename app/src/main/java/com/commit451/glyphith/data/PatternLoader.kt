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

    private const val SizeOfCsvLine = 4
    const val DefaultPatternName = "chill"

    var patterns = emptyList<LightPattern>()
        private set

    fun init(context: Context) {
        val resources = context.resources
        patterns = listOf(
            LightPattern(DefaultPatternName, loadAnimation(resources, R.raw.chill)),
            LightPattern("reverse chill", loadAnimation(resources, R.raw.reversechill)),
            LightPattern("pulse", loadAnimation(resources, R.raw.pulse)),
            LightPattern("chaos", loadAnimation(resources, R.raw.chaos)),
            LightPattern("oi!", loadAnimation(resources, R.raw.oi)),
            LightPattern("bulb one", loadAnimation(resources, R.raw.bulbone)),
            LightPattern("bulb two", loadAnimation(resources, R.raw.bulbtwo)),
            LightPattern("guiro", loadAnimation(resources, R.raw.guiro)),
            LightPattern("volley", loadAnimation(resources, R.raw.volley)),
            LightPattern("squiggle", loadAnimation(resources, R.raw.squiggle)),
            LightPattern("gamma", loadAnimation(resources, R.raw.gamma)),
            LightPattern("beak", loadAnimation(resources, R.raw.beak)),
            LightPattern("nope", loadAnimation(resources, R.raw.nope)),
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

        return lines
            .filter { it.size == SizeOfCsvLine }
            .map { config ->
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