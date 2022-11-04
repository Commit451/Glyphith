package com.commit451.glyphith.api

import android.animation.ValueAnimator
import com.topjohnwu.superuser.Shell

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

    private const val MAX = 255

    private val AllLights = listOf(
        LIGHTBELT_BATTERY,
        LIGHTBELT_BRIGHTNESS1,
        LIGHTBELT_BRIGHTNESS2,
        LIGHTBELT_FRONT_CAMERA,
        LIGHTBELT_REAR_CAMERA,
        LIGHTBELT_USB,
    )

    fun blink() {
        val va = ValueAnimator.ofInt(0, MAX, 0)
        va.duration = 1000
        va.addUpdateListener { animation ->
            AllLights.forEach {
                setNodeString(it, animation.animatedValue as Int)
            }
        }
        va.start()
    }

    fun off() {
        AllLights.forEach {
            setNodeString(it, 0)
        }
    }

    private fun setNodeString(light: String, amount: Int): Boolean {
        val result = Shell.cmd("echo $amount > $light").exec()
        return result.isSuccess
    }
}