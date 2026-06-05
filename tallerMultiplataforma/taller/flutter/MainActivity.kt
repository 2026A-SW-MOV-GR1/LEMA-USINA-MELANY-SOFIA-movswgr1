package com.example.moviles2026aswgr1

import android.content.res.Configuration
import androidx.core.content.ContextCompat
import io.flutter.embedding.android.FlutterActivity
import io.flutter.embedding.engine.FlutterEngine
import io.flutter.plugin.common.MethodChannel

/**
 * Opción 3 — Flutter (Platform Channels / MethodChannel)
 *
 * Expone los recursos de Android (strings.xml + colors.xml con calificadores)
 * al motor de renderizado de Flutter mediante un MethodChannel.
 * Sin Intl ni archivos .arb.
 */
class MainActivity : FlutterActivity() {

    companion object {
        private const val CHANNEL = "com.example.moviles2026aswgr1/resources"
    }

    override fun configureFlutterEngine(flutterEngine: FlutterEngine) {
        super.configureFlutterEngine(flutterEngine)

        MethodChannel(
            flutterEngine.dartExecutor.binaryMessenger,
            CHANNEL
        ).setMethodCallHandler { call, result ->
            when (call.method) {
                "getResources" -> result.success(buildResourceMap())
                else           -> result.notImplemented()
            }
        }
    }

    /**
     * Construye el mapa de recursos con el Context actualizado.
     * Android resuelve el calificador correcto (idioma + orientación)
     * antes de que getString() y getColor() sean invocados.
     */
    private fun buildResourceMap(): Map<String, String> {
        val ctx = applicationContext
        return mapOf(
            "mensaje"    to ctx.getString(R.string.mensaje),
            "colorTexto" to colorToHex(ctx, R.color.color_texto),
            "colorFondo" to colorToHex(ctx, R.color.color_fondo),
        )
    }

    private fun colorToHex(ctx: android.content.Context, resId: Int): String {
        val color = ContextCompat.getColor(ctx, resId)
        return String.format("#%06X", 0xFFFFFF and color)
    }
}
