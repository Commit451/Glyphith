package com.commit451.glyphith

import android.content.Context
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.commit451.glyphith.api.Glyph
import com.commit451.glyphith.data.PatternLoader
import com.commit451.glyphith.service.EndlessService
import com.commit451.glyphith.ui.TitleText
import com.commit451.glyphith.util.Util


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen(
    context: Context,
    navigateToSettings: () -> Unit,
    navigateToCreate: () -> Unit,
    navigateToDebug: () -> Unit,
) {

    var currentPatternName by remember {
        mutableStateOf(Glyph.currentPatternName)
    }

    var isServiceRunning by remember {
        mutableStateOf(
            Util.isMyServiceRunning(
                context,
                EndlessService::class.java
            )
        )
    }

    val patterns = PatternLoader.loadPatterns(context.resources)

    Scaffold(
        floatingActionButton = {
            FloatingActionButton(onClick = navigateToCreate) {
                Icon(
                    Icons.Filled.Add,
                    "Add",
                )
            }
        }
    ) { paddingValues ->

        Column(Modifier.padding(16.dp)) {

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(paddingValues)
            ) {

                TitleText("Glyphith")

                Spacer(modifier = Modifier.weight(1f))

                Switch(checked = isServiceRunning, onCheckedChange = {
                    Util.service(context, it)
                    isServiceRunning = it
                }, modifier = Modifier
                    .align(Alignment.CenterVertically)
                    .padding(end = 4.dp))

                IconButton(
                    modifier = Modifier
                        .size(42.dp)
                        .align(Alignment.CenterVertically),
                    onClick = navigateToSettings,
                ) {
                    Icon(
                        Icons.Filled.Settings,
                        "Settings",
                    )
                }

                if (BuildConfig.DEBUG) {
                    IconButton(
                        modifier = Modifier
                            .size(42.dp)
                            .align(Alignment.CenterVertically),
                        onClick = navigateToDebug,
                    ) {
                        Icon(
                            Icons.Filled.Warning,
                            "Debug",
                        )
                    }
                }
            }

            patterns.forEach {
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp)
                        .clickable {
                            Glyph.setPattern(it)
                            currentPatternName = it.name
                        }
                ) {
                    if (currentPatternName == it.name) {
                        Icon(
                            Icons.Filled.CheckCircle,
                            "Check",
                            modifier = Modifier.padding(16.dp)
                        )
                    }
                    Text(text = it.name, modifier = Modifier.padding(16.dp))
                    Button(
                        onClick = {
                            Glyph.setPattern(it)
                            Glyph.animate()
                        },
                        modifier = Modifier.padding(16.dp)
                    ) {
                        Text(text = "Preview")
                    }
                }
            }
        }

    }
}