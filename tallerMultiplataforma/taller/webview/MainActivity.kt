package com.example.moviles2026aswgr1

import android.annotation.SuppressLint
import android.content.Context
import android.content.res.Configuration
import android.os.Bundle
import android.webkit.JavascriptInterface
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat

/**
 * Opción 1 — WebView (Enfoque Híbrido Nativo)
 *
 * Regla: Los valores de texto y color provienen EXCLUSIVAMENTE de
 * res/values-*/strings.xml y res/values-*/colors.xml.
 * Android resuelve el calificador correcto según idioma y orientación.
 * NO se usan CSS Media Queries ni librerías JS de i18n.
 */
class MainActivity : AppCompatActivity() {

    private lateinit var webView: WebView

    @SuppressLint("SetJavaScriptEnabled")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main_webview)

        webView = findViewById(R.id.webView)
        webView.settings.javaScriptEnabled = true

        // Registramos el puente; "Android" es el objeto global en JS
        webView.addJavascriptInterface(AndroidBridge(this), "Android")
        webView.webViewClient = WebViewClient()

        // Cargamos el HTML desde assets/
        webView.loadUrl("file:///android_asset/index.html")
    }

    /** Se llama cuando rota la pantalla o cambia el idioma del sistema */
    override fun onConfigurationChanged(newConfig: Configuration) {
        super.onConfigurationChanged(newConfig)
        // Le pedimos al HTML que refresque sus valores desde el puente nativo
        webView.evaluateJavascript("actualizarUI()", null)
    }

    // ── JavascriptInterface ──────────────────────────────────────────────

    /**
     * Puente nativo → JS.
     * Cada método consulta el Context actualizado, que ya tiene el
     * Configuration correcto (idioma + orientación) resuelto por Android.
     */
    inner class AndroidBridge(private val ctx: Context) {

        @JavascriptInterface
        fun getMensaje(): String = ctx.getString(R.string.mensaje)

        @JavascriptInterface
        fun getColorTexto(): String = colorToHex(ctx, R.color.color_texto)

        @JavascriptInterface
        fun getColorFondo(): String = colorToHex(ctx, R.color.color_fondo)

        private fun colorToHex(ctx: Context, resId: Int): String {
            val color = ContextCompat.getColor(ctx, resId)
            return String.format("#%06X", 0xFFFFFF and color)
        }
    }
}
