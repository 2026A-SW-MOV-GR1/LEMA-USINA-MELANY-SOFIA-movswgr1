package com.example.moviles2026aswgr1.shared

import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import com.example.moviles2026aswgr1.R

@Composable
actual fun appGreeting(): String {
    val context = LocalContext.current
    return context.getString(R.string.saludo)
}

@Composable
actual fun appColors(): AppColors {
    val context = LocalContext.current
    return AppColors(
        text = Color(context.getColor(R.color.texto_principal)),
        background = Color(context.getColor(R.color.fondo_principal))
    )
}

