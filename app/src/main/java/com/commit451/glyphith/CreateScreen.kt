package com.commit451.glyphith

import android.view.HapticFeedbackConstants
import androidx.compose.foundation.Image
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalView
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.commit451.glyphith.util.observeAsStateSafe

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CreateScreen(viewModel: GlyphithViewModel, onBack: () -> Unit) {
    val uiState by viewModel.uiState.observeAsStateSafe()
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Create") },
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
                actions = {
                    Text(text = uiState.counter.orEmpty())
                    if (uiState.currentlyRecording) {
                        IconButton(
                            onClick = {
                                viewModel.onStopRecording()
                            }
                        ) {
                            Icon(
                                Icons.Filled.PlayArrow,
                                contentDescription = "Stop"
                            )
                        }
                    }

                }
            )
        }
    ) { paddingValues ->
        Column(modifier = Modifier.padding(paddingValues)) {
            LightButtonRow(
                lightName = "Camera",
                R.drawable.create_light_camera,
                { viewModel.onStartRecording() })
            LightButtonRow(
                lightName = "Diagonal",
                R.drawable.create_light_diagonal,
                { viewModel.onStartRecording() })
            LightButtonRow(
                lightName = "Battery",
                R.drawable.create_light_battery,
                { viewModel.onStartRecording() })
            LightButtonRow(
                lightName = "Line",
                R.drawable.create_light_usb_line,
                { viewModel.onStartRecording() })
            LightButtonRow(
                lightName = "Dot",
                R.drawable.create_light_usb_dot,
                { viewModel.onStartRecording() })
        }
    }
}

@Composable
private fun LightButtonRow(lightName: String, drawableRes: Int, onStart: () -> Unit) {
    val scrollState = rememberScrollState()
    val clickCount = remember { mutableStateListOf<String>() }
    val view = LocalView.current
    Row(
        modifier = Modifier
            .padding(vertical = 8.dp, horizontal = 16.dp)
            .horizontalScroll(scrollState)
    ) {
        Column {

            Text(lightName)
            Button(
                shape = ShapeDefaults.Large,
                onClick = {
                    clickCount.add("asdf")
                    onStart()
                    view.performHapticFeedback(HapticFeedbackConstants.KEYBOARD_TAP)
                },
                modifier = Modifier.size(100.dp)
            ) {
                Image(
                    painterResource(drawableRes), "",
                    modifier = Modifier.size(78.dp, 78.dp)
                )
            }

        }

        clickCount.forEach {
            Text(text = it)
        }
    }
}

@Composable
private fun GlyphPreview() {
    Column {

        Row {
            Image(
                painterResource(R.drawable.create_light_camera), "camera",
                modifier = Modifier.size(69.dp, 85.dp)
            )
            Image(
                painterResource(R.drawable.create_light_diagonal), "diagonal",
                modifier = Modifier.size(82.dp, 62.dp)
            )

        }
        Image(
            painterResource(R.drawable.create_light_battery), "battery",
            modifier = Modifier.size(222.dp, 207.dp)
        )

        Column(Modifier.align(Alignment.CenterHorizontally)) {

            Image(
                painterResource(R.drawable.create_light_usb_line), "usb line",
                modifier = Modifier.size(77.dp, 58.dp)
            )
            Image(
                painterResource(R.drawable.create_light_usb_dot), "dot",
                modifier = Modifier.size(29.dp, 17.dp)
            )
        }
    }
}