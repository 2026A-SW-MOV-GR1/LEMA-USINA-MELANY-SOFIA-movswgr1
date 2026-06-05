package com.example.kmpclient

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.sp

@Composable
fun App() {
    val text = ResourcesManager.getText()
    val textColor = ResourcesManager.getTextColor()
    val backgroundColor = ResourcesManager.getBackgroundColor()

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(backgroundColor)),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = text,
            style = TextStyle(
                color = Color(textColor),
                fontSize = 24.sp
            )
        )
    }
}

