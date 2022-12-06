package com.commit451.glyphith

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalUriHandler
import androidx.compose.ui.unit.dp
import com.mikepenz.aboutlibraries.ui.compose.LibrariesContainer

private const val fontUrl =
    "https://fontstruct.com/fontstructions/show/1947061/ndot-45-inspired-by-nothing"

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AboutScreen(onBack: () -> Unit) {

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("About") },
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
        val uriHandler = LocalUriHandler.current
        Column {

            Column(
                modifier = Modifier
                    .padding(paddingValues)
                    .padding(16.dp)
            ) {
                Text("The dot font used in this app is called NDOT 45, created by Interactivate. See more here:")
                Button(onClick = {
                    uriHandler.openUri(fontUrl)
                }) {
                    Text(text = "Fontstruct")
                }
            }
            LibrariesContainer()
        }
    }
}