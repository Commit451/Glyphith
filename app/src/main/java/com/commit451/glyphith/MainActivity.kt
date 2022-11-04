package com.commit451.glyphith

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.commit451.glyphith.api.Glyph
import com.commit451.glyphith.service.Actions
import com.commit451.glyphith.service.EndlessService
import com.commit451.glyphith.ui.theme.GlyphithTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            GlyphithTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    Screen(this)
                }
            }
        }
    }
}

@Composable
fun Screen(context: Context) {
    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {

        Button(onClick = {
            Glyph.blink()
        }) {
            Text(text = "Animate")
        }

        Button(onClick = {
            Glyph.off()
        }) {
            Text(text = "Off")
        }

        Button(onClick = {
            service(context, true)
        }) {
            Text(text = "Start service")
        }

        Button(onClick = {
            service(context, false)
        }) {
            Text(text = "Stop service")
        }
    }
}

private fun service(context: Context, start: Boolean) {
    Intent(context, EndlessService::class.java).also {
        it.action = if (start) Actions.START.name else Actions.STOP.name
        context.startForegroundService(it)
    }
}