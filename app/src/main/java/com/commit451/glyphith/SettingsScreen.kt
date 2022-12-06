package com.commit451.glyphith

import android.content.Context
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.chargemap.compose.numberpicker.NumberPicker
import com.commit451.glyphith.data.Prefs
import com.commit451.glyphith.ui.FontNDot
import com.commit451.glyphith.util.Util

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SettingsScreen(context: Context, onBack: () -> Unit) {

    var alwaysOn by remember {
        mutableStateOf(Prefs.isAlwaysOn)
    }

    var restIntervalSeconds by remember {
        mutableStateOf(Prefs.restIntervalSeconds)
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
                .verticalScroll(rememberScrollState())
        ) {
            checkSetting(
                title = "Always on",
                description = "The Glyph lights will light up, even if the device is asleep. This will use more battery and is NOT recommended.",
                isChecked = alwaysOn,
                onCheckChanged = {
                    alwaysOn = it
                    Prefs.isAlwaysOn = it
                    Util.notifyServiceModified(context)
                }
            )
            Spacer(modifier = Modifier.height(32.dp))
            numberSetting(
                title = "Rest interval",
                description = "Set the number of seconds the service will wait before it plays the Glyph animation again. The more frequent, the more battery will be used. Change with caution.",
                currentValue = restIntervalSeconds,
                onValueChanged = {
                    restIntervalSeconds = it
                    Prefs.restIntervalSeconds = it
                    Util.notifyServiceModified(context)
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
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
            .clickable { onCheckChanged(!isChecked) }
    ) {

        Column(Modifier.fillMaxWidth(0.75f)) {
            Title(text = title)
            Text(
                text = description,
                modifier = Modifier.padding(vertical = 4.dp)
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

@Composable
private fun numberSetting(
    title: String,
    description: String,
    currentValue: Int,
    onValueChanged: (value: Int) -> Unit,
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
    ) {

        Column(Modifier.fillMaxWidth(0.75f)) {
            Title(text = title)
            Text(
                text = description,
                modifier = Modifier.padding(vertical = 4.dp)
            )
        }
        NumberPicker(
            value = currentValue,
            range = 3..60,
            dividersColor = Color.White,
            textStyle = TextStyle(color = Color.White),
            onValueChange = {
                onValueChanged(it)
            }
        )
    }
}

@Composable
private fun Title(text: String) {
    Text(text = text, fontFamily = FontNDot, fontSize = 28.sp, lineHeight = 34.sp)
}