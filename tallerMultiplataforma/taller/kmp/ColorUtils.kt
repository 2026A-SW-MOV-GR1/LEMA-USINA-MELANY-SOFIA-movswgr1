package com.taller.kmp

import androidx.compose.ui.graphics.Color

// Convert Android color Int (0xAARRGGBB) to Compose Color
fun colorIntToComposeColor(colorInt: Int): Color {
    val a = (colorInt shr 24) and 0xFF
    val r = (colorInt shr 16) and 0xFF
    val g = (colorInt shr 8) and 0xFF
    val b = colorInt and 0xFF
    return Color(red = r / 255f, green = g / 255f, blue = b / 255f, alpha = a / 255f)
}

// If any APIs return Long colors, convert safely
fun colorLongToComposeColor(colorLong: Long): Color = colorIntToComposeColor(colorLong.toInt())

