package edu.epn.interapp

import android.content.Intent
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.tooling.preview.Preview

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        enableEdgeToEdge()
        super.onCreate(savedInstanceState)

        initializePlatformActions(this) { bitmap ->
            AppState.outgoingPhoto = bitmap
        }

        handleIntent(intent)

        setContent {
            App()
        }
    }

    override fun onNewIntent(intent: Intent) {
        super.onNewIntent(intent)
        handleIntent(intent)
    }

    private fun handleIntent(intent: Intent?) {
        if (intent == null) return
        if (intent.action == Intent.ACTION_SEND) {
            val type = intent.type
            if ("text/plain" == type) {
                intent.getStringExtra(Intent.EXTRA_TEXT)?.let {
                    AppState.receivedText = it
                    AppState.receivedImage = null
                }
            } else if (type?.startsWith("image/") == true) {
                val uri = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                    intent.getParcelableExtra(Intent.EXTRA_STREAM, Uri::class.java)
                } else {
                    @Suppress("DEPRECATION")
                    intent.getParcelableExtra<Uri>(Intent.EXTRA_STREAM)
                }

                uri?.let { imageUri ->
                    try {
                        contentResolver.openInputStream(imageUri)?.use { inputStream ->
                            val bitmap = BitmapFactory.decodeStream(inputStream)
                            AppState.receivedImage = bitmap?.asImageBitmap()
                            AppState.receivedText = null
                        }
                    } catch (e: Exception) {
                        e.printStackTrace()
                    }
                }
            }
        }
    }
}

@Preview
@Composable
fun AppAndroidPreview() {
    App()
}