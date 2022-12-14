package com.commit451.glyphith

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.commit451.glyphith.data.Prefs

@Composable
fun IntroScreen(onMoveOn: () -> Unit) {

    val message = """
        This app requires root. This is due to the fact that Nothing has not yet provided an API 
        for controlling the Glyph lights. Additionally, it is more than likely not good for your 
        phone to keep flashing the Glyph lights over and over, so please use at your own risk and 
        this app is not responsible for any damage that may occur. If you don't see the lights 
        changing, you likely have not granted the app root permissions. It only needs it to change
        the system files that control the glyph lights. See the source code on GitHub to confirm.
    """.trimIndent()

    LaunchedEffect(Prefs.hasSeenIntro) {
        Prefs.hasSeenIntro = true
    }

    Box(modifier = Modifier.padding(16.dp)) {
        Column(
            modifier = Modifier
                .align(Alignment.Center)
                .padding(16.dp)
        ) {
            Text(text = "Warning", fontSize = 32.sp)
            Text(
                text = message,
                modifier = Modifier.padding(vertical = 16.dp)
            )
            Button(
                onClick = onMoveOn,
                modifier = Modifier.padding(vertical = 16.dp)
            ) {
                Text("Got it")
            }
        }
    }
}