package com.commit451.glyphith

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.commit451.glyphith.nav.AppRoot
import com.commit451.glyphith.service.ServiceAction
import com.commit451.glyphith.util.Util
import timber.log.Timber

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (intent.action == ServiceAction.Stop.name) {
            Util.service(this, false)
        }
        setContent {
            AppRoot(context = this)
        }
    }

    override fun onNewIntent(intent: Intent?) {
        super.onNewIntent(intent)
        Timber.d("onNewIntent")
        if (intent?.action == ServiceAction.Stop.name) {
            Util.service(this, false)
        }
    }
}