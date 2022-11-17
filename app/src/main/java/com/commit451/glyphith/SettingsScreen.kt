package com.commit451.glyphith

import android.content.Context
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.commit451.glyphith.data.Prefs
import com.commit451.glyphith.util.Util

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SettingsScreen(context: Context, onBack: () -> Unit) {

    var alwaysOn by remember {
        mutableStateOf(Prefs.isAlwaysOn)
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Settings") },
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

            Row {

                Text(text = "Always on")
                Switch(checked = alwaysOn, onCheckedChange = {
                    alwaysOn = it
                    Prefs.isAlwaysOn = it
                    Util.service(context, false)
                    Util.service(context, true)
                })
            }
        }
    }
}