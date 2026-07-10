package edu.epn.interapp

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.graphics.ImageBitmap

object AppState {
    // Para Sección A (Acciones Salientes)
    var outgoingPhoto by mutableStateOf<ImageBitmap?>(null)

    // Para Sección B (Acciones Entrantes)
    var receivedText by mutableStateOf<String?>(null)
    var receivedImage by mutableStateOf<ImageBitmap?>(null)

    fun resetReceivedData() {
        receivedText = null
        receivedImage = null
    }
}
