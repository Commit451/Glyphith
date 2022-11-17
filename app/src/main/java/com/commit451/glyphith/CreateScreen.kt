package com.commit451.glyphith

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CreateScreen() {

    Scaffold { paddingValues ->
        Text(text = "Create", modifier = Modifier.padding(paddingValues))

        Box {
            Column(modifier = Modifier.align(Alignment.Center)) {

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
    }
}