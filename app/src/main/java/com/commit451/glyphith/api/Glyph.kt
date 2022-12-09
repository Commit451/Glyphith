package com.commit451.glyphith.api

import android.animation.ValueAnimator
import com.commit451.glyphith.data.LightPattern
import com.topjohnwu.superuser.Shell
import timber.log.Timber
import kotlin.math.roundToInt

/**
 * See https://twitter.com/linuxct/status/1546648878774190080
 */
object Glyph {

    private const val debugLog = false
    const val MaxBrightness = 4095

    private const val PathRoot = "sys/devices/platform/soc/984000.i2c/i2c-0/0-0020/leds/aw210xx_led"

    private const val PathBattery = "$PathRoot/round_leds_br"
    private const val PathRearCamera = "$PathRoot/rear_cam_led_br"
    private const val PathDiagonal = "$PathRoot/front_cam_led_br"
    private const val PathUSBDot = "$PathRoot/dot_led_br"
    private const val PathUSBLine = "$PathRoot/vline_leds_br"

    private val lightMap = mapOf(
        Light.Battery to PathBattery,
        Light.RearCamera to PathRearCamera,
        Light.Diagonal to PathDiagonal,
        Light.USBLine to PathUSBLine,
        Light.USBDot to PathUSBDot,
    )

    var currentPatternName = ""
        private set
    private var currentAnimations = emptyList<ValueAnimator>()

    private val shorthandMap = mapOf(
        "camera" to Light.RearCamera,
        "diag" to Light.Diagonal,
        "battery" to Light.Battery,
        "usbline" to Light.USBLine,
        "usbdot" to Light.USBDot,
    )

    fun setPattern(lightPattern: LightPattern) {
        log("Setting pattern to ${lightPattern.name}")
        reset()
        currentPatternName = lightPattern.name
        currentAnimations = lightPattern.animators.map {
            val light = shorthandMap[it.light]!!
            it.animator.addUpdateListener { animation ->
                setNodeString(path(light), animation.animatedValue as Int)
            }
            it.animator
        }
    }

    /**
     * Set the light brightness value, a percentage from 0 to 1
     */
    fun setLightValue(light: Light, percentage: Float) {
        val value = (percentage * MaxBrightness).roundToInt()
        log("Setting light ${light.name} to $value")
        setNodeString(path(light), (percentage * MaxBrightness).roundToInt())
    }

    /**
     * Tells the current light brightness value, a percentage from 0 to 1
     */
    fun readLightValue(light: Light): Float {
        val currentValue =
            Shell.cmd("cat ${path(light)}").exec().out.firstOrNull()?.toInt() ?: return 0f
        return currentValue.toFloat() / MaxBrightness
    }

    fun animate() {
        reset()
        currentAnimations.forEach {
            it.start()
        }
    }

    fun off() {
        Light.values().forEach {
            setNodeString(path(it), 0)
        }
    }

    private fun reset() {
        currentAnimations.forEach {
            it.cancel()
        }
        off()
    }

    private fun path(light: Light): String {
        return lightMap[light]!!
    }

    private fun setNodeString(light: String, amount: Int) {
        val result = Shell.cmd("echo $amount > $light").exec()
        if (!result.isSuccess) {
            Timber.e("Error: ${result.err}")
        }
    }

    private fun log(message: String) {
        if (debugLog) {
            Timber.d(message)
        }
    }
}