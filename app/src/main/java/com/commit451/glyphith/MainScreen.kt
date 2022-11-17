package com.commit451.glyphith

import android.content.Context
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.commit451.glyphith.api.Glyph
import com.commit451.glyphith.api.Light
import com.commit451.glyphith.service.EndlessService
import com.commit451.glyphith.util.Util


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen(
    context: Context,
    navigateToSettings: () -> Unit,
    navigateToCreate: () -> Unit,
    navigateToDebug: () -> Unit,
) {

    var showSliders by remember { mutableStateOf(false) }
    var isServiceRunning by remember {
        mutableStateOf(
            Util.isMyServiceRunning(
                context,
                EndlessService::class.java
            )
        )
    }

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

                Text(text = "Glyphith", fontSize = 30.sp)

                Spacer(modifier = Modifier.weight(1f))

                Switch(checked = isServiceRunning, onCheckedChange = {
                    Util.service(context, it)
                    isServiceRunning = it
                })

                IconButton(
                    modifier = Modifier
                        .size(24.dp),
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
                            .size(24.dp),
                        onClick = navigateToDebug,
                    ) {
                        Icon(
                            Icons.Filled.Warning,
                            "Debug",
                        )
                    }
                }
            }


            Button(modifier = Modifier.size(200.dp), onClick = {
                Glyph.animate()
            }) {
                Text(text = "Animate")
            }
        }

    }
}