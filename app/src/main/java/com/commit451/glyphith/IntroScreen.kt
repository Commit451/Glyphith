package com.commit451.glyphith

import android.content.Context
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.commit451.glyphith.data.Prefs

@Composable
fun IntroScreen(context: Context, onMoveOn: () -> Unit) {

    LaunchedEffect(Prefs.hasSeenIntro) {
        Prefs.hasSeenIntro = true
    }

    Column(
        modifier = Modifier
            .padding(16.dp)
    ) {
        Row {
            Text(text = "Warning")
        }
        Button(onClick = onMoveOn) {
            Text("Got it")
        }
    }
}