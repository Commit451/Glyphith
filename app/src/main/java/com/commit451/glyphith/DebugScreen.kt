package com.commit451.glyphith

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.commit451.glyphith.api.Glyph
import com.commit451.glyphith.api.Light

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DebugScreen(onBack: () -> Unit) {

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Debug") },
                navigationIcon = {
                    IconButton(
                        onClick = onBack
                    ) {
                        Icon(
                            Icons.Filled.ArrowBack,
                            contentDescription = "Back"
                        )
                    }
                },
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .padding(paddingValues)
                .padding(16.dp)
        ) {

            Button(onClick = {
                Glyph.off()
            }) {
                Text(text = "Turn off all lights")
            }
            LightSlider(light = Light.Battery)
            LightSlider(light = Light.RearCamera)
            LightSlider(light = Light.Diagonal)
            LightSlider(light = Light.USBLine)
            LightSlider(light = Light.USBDot)
        }
    }
}

@Composable
private fun LightSlider(light: Light) {
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