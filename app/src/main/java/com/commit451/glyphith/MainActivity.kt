package com.commit451.glyphith

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.commit451.glyphith.api.Glyph
import com.commit451.glyphith.api.Light
import com.commit451.glyphith.service.Actions
import com.commit451.glyphith.service.EndlessService
import com.commit451.glyphith.ui.GlyphithTheme
import com.commit451.glyphith.util.Util
import timber.log.Timber

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (intent.action == Actions.STOP.name) {
            service(this, false)
        }
        setContent {
            GlyphithTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    Screen(this)
                }
            }
        }
        // comment this out normally
        //service(this, true)
        //Glyph.turnOnLight(Glyph.LightUSB)
        Glyph.loadAnimation(resources)
    }

    override fun onNewIntent(intent: Intent?) {
        super.onNewIntent(intent)
        Timber.d("onNewIntent")
        if (intent?.action == Actions.STOP.name) {
            service(this, false)
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Screen(context: Context) {

    var showSliders by remember { mutableStateOf(false) }
    var isServiceRunning by remember {
        mutableStateOf(
            Util.isMyServiceRunning(
                context,
                EndlessService::class.java
            )
        )
    }

    Scaffold(
        floatingActionButton = {
            FloatingActionButton(onClick = { /* ... */ }) {
                Icon(
                    Icons.Filled.Add,
                    "Add",
                )
            }
        }
    ) { paddingValues ->

        Column(Modifier.padding(16.dp)) {

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(paddingValues)
            ) {

                Text(text = "Glyphith", fontSize = 30.sp)

                Spacer(modifier = Modifier.weight(1f))

                Switch(checked = isServiceRunning, onCheckedChange = {
                    service(context, it)
                    isServiceRunning = it
                })

                IconButton(
                    modifier = Modifier
                        .size(24.dp),
                    onClick = {

                    }
                ) {
                    Icon(
                        Icons.Filled.Settings,
                        "Settings",
                    )
                }

            }


            Button(onClick = {
                Glyph.animate()
            }) {
                Text(text = "Animate")
            }

            Button(onClick = {
                Glyph.off()
            }) {
                Text(text = "Off")
            }

            Text(text = "Service")

            Text(text = "Show sliders")
            Switch(checked = showSliders, onCheckedChange = {
                showSliders = it
            })

            if (showSliders) {
                lightSlider(light = Light.Battery)
                lightSlider(light = Light.RearCamera)
                lightSlider(light = Light.Diagonal)
                lightSlider(light = Light.USBLine)
                lightSlider(light = Light.USBDot)
            }
        }

    }
}

@Composable
private fun lightSlider(light: Light) {
    var lightPercentage by remember {
        mutableStateOf(Glyph.readLightValue(light))
    }

    Column(modifier = Modifier.padding(16.dp)) {

        Text(text = light.name)

        Slider(value = lightPercentage, onValueChange = {
            lightPercentage = it
            Glyph.setLightValue(light, it)
        }, modifier = Modifier.padding(16.dp))

    }
}

private fun service(context: Context, start: Boolean) {
    Intent(context, EndlessService::class.java).also {
        it.action = if (start) Actions.START.name else Actions.STOP.name
        context.startForegroundService(it)
    }
}