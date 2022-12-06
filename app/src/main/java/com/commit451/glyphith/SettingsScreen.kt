package com.commit451.glyphith

import android.content.Context
import android.widget.Toast
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.commit451.glyphith.data.Prefs

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
        ) {
            checkSetting(
                title = "Always on",
                description = "The Glyph lights will blink, even if the device is asleep. This will use more battery and is NOT recommended.",
                isChecked = alwaysOn,
                onCheckChanged = {
                    alwaysOn = it
                    Prefs.isAlwaysOn = it
                    Toast.makeText(context, "Please restart the service!", Toast.LENGTH_SHORT)
                        .show()
                }
            )
        }
    }
}

@Composable
private fun checkSetting(
    title: String,
    description: String,
    isChecked: Boolean,
    onCheckChanged: (checked: Boolean) -> Unit,
) {
    Row(modifier = Modifier.padding(16.dp)) {

        Column {
            Text(text = title)
            Text(
                text = description,
                fontSize = 12.sp
            )
        }
        Switch(
            checked = isChecked,
            onCheckedChange = {
                onCheckChanged(it)
            }, modifier = Modifier
                .padding(horizontal = 6.dp)
                .align(Alignment.CenterVertically)
        )
    }
}