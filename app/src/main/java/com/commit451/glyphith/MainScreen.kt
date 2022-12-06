package com.commit451.glyphith

import android.content.Context
import android.os.Handler
import android.os.Looper
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.core.os.postDelayed
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

    var isShowingCountdown by remember {
        mutableStateOf(false)
    }
    var countdownText by remember {
        mutableStateOf("")
    }

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
            if (BuildConfig.DEBUG) {
                FloatingActionButton(onClick = navigateToCreate) {
                    Icon(
                        Icons.Filled.Add,
                        "Add",
                    )
                }
            } else {
                nothing()
            }
        }
    ) { paddingValues ->

        Box {
            Column(
                Modifier
                    .padding(16.dp)
                    .verticalScroll(rememberScrollState())
            ) {

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(paddingValues)
                ) {

                    TitleText("Glyphith")

                    Spacer(modifier = Modifier.weight(1f))

                    Switch(
                        checked = isServiceRunning, onCheckedChange = {
                            Util.service(context, it)
                            isServiceRunning = it
                        }, modifier = Modifier
                            .align(Alignment.CenterVertically)
                            .padding(end = 4.dp)
                    )

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
                    val selected = currentPatternName == it.name
                    Card(
                        border = if (selected) BorderStroke(2.dp, Color.White) else null,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp)
                            .clickable {
                                Glyph.setPattern(it)
                                Glyph.animate()
                                currentPatternName = it.name
                            }
                    ) {
                        Box(Modifier.fillMaxWidth()) {
                            if (selected) {
                                Icon(
                                    Icons.Filled.CheckCircle,
                                    "Selected",
                                    modifier = Modifier
                                        .size(70.dp)
                                        .padding(16.dp)
                                        .align(Alignment.TopEnd)
                                )
                            }
                            Column {
                                Text(text = it.name, modifier = Modifier.padding(16.dp))
                                Button(
                                    onClick = {
                                        isShowingCountdown = true
                                        showPreview(
                                            modifyText = { newText ->
                                                countdownText = newText
                                            },
                                            showPreview = {
                                                Glyph.setPattern(it)
                                                Glyph.animate()
                                                isShowingCountdown = false
                                            }
                                        )
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

            if (isShowingCountdown) {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(
                            com.commit451.glyphith.ui.Scrim
                        )
                ) {
                    Text(
                        text = countdownText,
                        modifier = Modifier
                            .align(Alignment.Center)
                    )
                }
            }
        }

    }
}

private fun showPreview(modifyText: (newText: String) -> Unit, showPreview: () -> Unit) {
    val handler = Handler(Looper.getMainLooper())
    val increment = if (BuildConfig.DEBUG) 200L else 600L
    modifyText("3")
    handler.postDelayed(increment) {
        modifyText("2")
    }
    handler.postDelayed(increment * 2) {
        modifyText("1")
    }
    handler.postDelayed(increment * 3) {
        showPreview()
    }
}

@Composable
private fun nothing() {
}