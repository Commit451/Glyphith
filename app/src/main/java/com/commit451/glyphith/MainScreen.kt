package com.commit451.glyphith

import android.content.Context
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
import androidx.compose.ui.unit.sp
import com.commit451.glyphith.api.Glyph
import com.commit451.glyphith.data.PatternLoader
import com.commit451.glyphith.data.Prefs
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
    navigateToAbout: () -> Unit,
) {

    var currentPatternName by remember {
        mutableStateOf(Prefs.patternName)
    }

    var isServiceRunning by remember {
        mutableStateOf(
            Util.isMyServiceRunning(
                context,
                EndlessService::class.java
            )
        )
    }

    val patterns = PatternLoader.patterns

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

                    TitleText(
                        text = "Glyphith",
                        modifier = if (BuildConfig.DEBUG) Modifier.clickable {
                            navigateToDebug()
                        } else Modifier
                    )

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

                    IconButton(
                        modifier = Modifier
                            .size(42.dp)
                            .align(Alignment.CenterVertically),
                        onClick = navigateToAbout,
                    ) {
                        Icon(
                            Icons.Filled.MoreVert,
                            "More",
                        )
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
                                Prefs.patternName = it.name
                                Glyph.setPattern(it)
                                currentPatternName = it.name
                                if (!isServiceRunning) {
                                    Glyph.animate()
                                }
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
                                Text(
                                    text = it.name,
                                    color = Color.White,
                                    fontSize = 22.sp,
                                    modifier = Modifier.padding(16.dp)
                                )
                            }
                        }
                    }
                }
            }
        }

    }
}

@Composable
private fun nothing() {
}