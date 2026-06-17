package edu.epn.interapp

import androidx.compose.ui.graphics.ImageBitmap

interface PlatformActions {
    fun openDialer(phoneNumber: String)
    fun takePhoto()
}

expect fun getPlatformActions(onPhotoCaptured: (ImageBitmap?) -> Unit): PlatformActions
