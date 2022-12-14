package com.commit451.glyphith.ui

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun TitleText(text: String, modifier: Modifier) {
    Text(text = text, fontFamily = FontNDot, fontSize = 32.sp, modifier = modifier.padding(16.dp))
}