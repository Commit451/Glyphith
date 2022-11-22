package com.commit451.glyphith

import androidx.compose.foundation.Image
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CreateScreen(onBack: () -> Unit) {
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
            )
        }
    ) { paddingValues ->
        Column(modifier = Modifier.padding(paddingValues)) {
            LightButtonRow(lightName = "Camera", R.drawable.create_light_camera)
            LightButtonRow(lightName = "Diagonal", R.drawable.create_light_diagonal)
            LightButtonRow(lightName = "Battery", R.drawable.create_light_battery)
            LightButtonRow(lightName = "Line", R.drawable.create_light_usb_line)
            LightButtonRow(lightName = "Dot", R.drawable.create_light_usb_dot)
        }
    }
}

@Composable
private fun LightButtonRow(lightName: String, drawableRes: Int) {
    val scrollState = rememberScrollState()
    val clickCount = remember { mutableStateListOf<String>() }
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