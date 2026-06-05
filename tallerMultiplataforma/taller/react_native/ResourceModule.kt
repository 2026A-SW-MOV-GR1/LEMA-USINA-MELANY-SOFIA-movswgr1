package com.example.moviles2026aswgr1

import android.content.res.Configuration
import androidx.core.content.ContextCompat
import com.facebook.react.bridge.*

/**
 * Opción 2 — React Native (Native Bridge)
 *
 * Módulo nativo que expone los recursos de Android (resueltos con calificadores)
 * al hilo de JavaScript de React Native.
 * NO se usa react-i18next ni ninguna librería JS de internacionalización.
 */
class ResourceModule(private val reactContext: ReactApplicationContext) :
    ReactContextBaseJavaModule(reactContext) {

    override fun getName(): String = "ResourceModule"

    /**
     * Devuelve el texto y colores correspondientes a la configuración actual
     * (idioma + orientación). Android resuelve automáticamente el calificador
     * correcto al llamar a getString() y getColor() con el Context actualizado.
     *
     * JS lo llama así:
     *   const res = await NativeModules.ResourceModule.getResources()
     */
    @ReactMethod
    fun getResources(promise: Promise) {
        try {
            val ctx = reactApplicationContext
            val result = WritableNativeMap().apply {
                putString("mensaje",    ctx.getString(R.string.mensaje))
                putString("colorTexto", colorToHex(ctx, R.color.color_texto))
                putString("colorFondo", colorToHex(ctx, R.color.color_fondo))
                // Enviamos la orientación para diagnóstico (no para if/else en UI)
                putBoolean("isLandscape",
                    ctx.resources.configuration.orientation ==
                    Configuration.ORIENTATION_LANDSCAPE)
            }
            promise.resolve(result)
        } catch (e: Exception) {
            promise.reject("RESOURCE_ERROR", e.message, e)
        }
    }

    private fun colorToHex(ctx: android.content.Context, resId: Int): String {
        val color = ContextCompat.getColor(ctx, resId)
        return String.format("#%06X", 0xFFFFFF and color)
    }
}
