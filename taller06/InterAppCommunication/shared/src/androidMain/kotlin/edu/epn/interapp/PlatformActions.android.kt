package edu.epn.interapp

import android.content.Intent
import android.net.Uri
import androidx.activity.ComponentActivity
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.asImageBitmap
import android.graphics.Bitmap
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts

class AndroidPlatformActions(
    private val activity: ComponentActivity,
    private val onPhotoCaptured: (ImageBitmap?) -> Unit
) : PlatformActions {

    private val takePictureLauncher: ActivityResultLauncher<Void?> =
        activity.registerForActivityResult(ActivityResultContracts.TakePicturePreview()) { bitmap: Bitmap? ->
            onPhotoCaptured(bitmap?.asImageBitmap())
        }

    override fun openDialer(phoneNumber: String) {
        val intent = Intent(Intent.ACTION_DIAL).apply {
            data = Uri.parse("tel:$phoneNumber")
        }
        activity.startActivity(intent)
    }

    override fun takePhoto() {
        takePictureLauncher.launch(null)
    }
}

private var platformActionsInstance: PlatformActions? = null

fun initializePlatformActions(activity: ComponentActivity, onPhotoCaptured: (ImageBitmap?) -> Unit) {
    platformActionsInstance = AndroidPlatformActions(activity, onPhotoCaptured)
}

actual fun getPlatformActions(): PlatformActions {
    return platformActionsInstance ?: throw IllegalStateException("PlatformActions not initialized")
}
