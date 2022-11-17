package com.commit451.glyphith

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.commit451.glyphith.api.Glyph
import com.commit451.glyphith.data.PatternLoader
import com.commit451.glyphith.nav.AppRoot
import com.commit451.glyphith.service.Actions
import com.commit451.glyphith.util.Util
import timber.log.Timber

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (intent.action == Actions.STOP.name) {
            Util.service(this, false)
        }
        setContent {
            AppRoot(context = this)
        }
        val patterns = PatternLoader.loadPatterns(resources)
        // TODO fix
        Glyph.setPattern(patterns.first())
    }

    override fun onNewIntent(intent: Intent?) {
        super.onNewIntent(intent)
        Timber.d("onNewIntent")
        if (intent?.action == Actions.STOP.name) {
            Util.service(this, false)
        }
    }
}