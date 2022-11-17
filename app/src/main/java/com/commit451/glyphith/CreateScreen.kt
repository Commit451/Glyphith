package com.commit451.glyphith

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CreateScreen() {

    Scaffold { paddingValues ->
        Text(text = "Create", modifier = Modifier.padding(paddingValues))
    }
}