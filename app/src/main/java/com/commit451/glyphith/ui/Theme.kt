package com.commit451.glyphith.ui

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

private val DarkColorScheme = darkColorScheme(
    primary = Red,
    secondary = Red,
    tertiary = Red,
    background = Color.Black,
    primaryContainer = Red,
    onPrimary = Color.Black,
    surface = Color.Black,
)

@Composable
fun GlyphithTheme(
    content: @Composable () -> Unit
) {
    MaterialTheme(
        colorScheme = DarkColorScheme,
        content = content
    )
}