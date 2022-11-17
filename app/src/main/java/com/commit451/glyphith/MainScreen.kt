package com.commit451.glyphith

import android.content.Context
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
import com.commit451.glyphith.service.EndlessService
import com.commit451.glyphith.util.Util


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen(
    context: Context,
    navigateToSettings: () -> Unit,
    navigateToCreate: () -> Unit,
) {

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
            FloatingActionButton(onClick = navigateToCreate) {
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
                    Util.service(context, it)
                    isServiceRunning = it
                })

                IconButton(
                    modifier = Modifier
                        .size(24.dp),
                    onClick = navigateToSettings,
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