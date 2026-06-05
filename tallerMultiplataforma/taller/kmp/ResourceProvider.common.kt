package com.taller.kmp

import androidx.compose.runtime.staticCompositionLocalOf

// Interfaz común para acceder a recursos desde código común (commonMain)
interface ResourceProvider {
    fun getString(name: String): String
    fun getColorInt(name: String): Int
}

// CompositionLocal para inyectar la implementación Android desde MainActivity
val LocalResourceProvider = staticCompositionLocalOf<ResourceProvider> {
    error("ResourceProvider not provided. Initialize and provide it from Android.")
}

