package com.example.moviles2026aswgr1.shared

import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

data class AppColors(
    val text: Color,
    val background: Color
)

@Composable
expect fun appGreeting(): String

@Composable
expect fun appColors(): AppColors

