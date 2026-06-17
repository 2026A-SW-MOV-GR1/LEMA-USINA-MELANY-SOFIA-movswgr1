package edu.epn.interapp

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.graphics.ImageBitmap

object AppState {
    var receivedText by mutableStateOf<String?>(null)
    var receivedImage by mutableStateOf<ImageBitmap?>(null)

    fun resetReceivedData() {
        receivedText = null
        receivedImage = null
    }
}
