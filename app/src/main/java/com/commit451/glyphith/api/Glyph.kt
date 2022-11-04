package com.commit451.glyphith.api

import com.topjohnwu.superuser.Shell
import timber.log.Timber
import java.io.BufferedWriter
import java.io.FileWriter

object Glyph {

    private const val LIGHTBELT_BATTERY =
        "sys/devices/platform/soc/984000.i2c/i2c-0/0-0020/leds/aw210xx_led/round_leds_br"

    private const val LIGHTBELT_BRIGHTNESS1 =
        "sys/devices/platform/soc/984000.i2c/i2c-0/0-0020/leds/aw210xx_led/all_white_leds_br"

    private const val LIGHTBELT_BRIGHTNESS2 =
        "sys/devices/platform/soc/984000.i2c/i2c-0/0-0020/leds/aw210xx_led/glo_current"

    private const val LIGHTBELT_FRONT_CAMERA =
        "sys/devices/platform/soc/984000.i2c/i2c-0/0-0020/leds/aw210xx_led/front_cam_led_br"

    private const val LIGHTBELT_REAR_CAMERA =
        "sys/devices/platform/soc/984000.i2c/i2c-0/0-0020/leds/aw210xx_led/rear_cam_led_br"

    private const val LIGHTBELT_USB =
        "sys/devices/platform/soc/984000.i2c/i2c-0/0-0020/leds/aw210xx_led/horse_race_leds_br"

    fun blink(): Throwable? {
        return setNodeString(LIGHTBELT_BATTERY, 90)
    }

    private fun setNodeString(light: String, amount: Int): Throwable? {
        val result = Shell.cmd("echo $amount > $light").exec()
        Timber.d(result.toString())
        return null
    }
}