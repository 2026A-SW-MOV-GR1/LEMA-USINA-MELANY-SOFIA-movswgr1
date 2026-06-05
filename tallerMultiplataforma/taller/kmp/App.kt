// Opción 4 — Kotlin Multiplatform (Compose Multiplatform)
//
// Estructura de archivos:
//
//   composeApp/src/commonMain/composeResources/
//       values/strings.xml          → ES portrait (defecto)
//       values-en/strings.xml       → EN portrait
//       values-land/strings.xml     → ES landscape  (*)
//       values-en-land/strings.xml  → EN landscape  (*)
//       values/colors.xml           → colores defecto
//       values-en/colors.xml        → colores EN
//       values-land/colors.xml      → colores landscape ES
//       values-en-land/colors.xml   → colores landscape EN
//
// (*) El soporte de calificadores -land en Compose Multiplatform
//     está en evolución. Si no es soportado, usar expect/actual
//     con el R nativo de Android (ver abajo).

// ── commonMain/App.kt ────────────────────────────────────────────────────

import androidx.compose.runtime.*
import androidx.compose.ui.*
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Text
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.sp
import org.jetbrains.compose.resources.*

// Compose Multiplatform resuelve el calificador correcto
// según la Configuration del sistema (idioma + orientación).
@OptIn(ExperimentalResourceApi::class)
@Composable
fun App() {
    val mensaje    = stringResource(Res.string.mensaje)
    val colorFondo = colorResource(Res.color.color_fondo)
    val colorTexto = colorResource(Res.color.color_texto)

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(colorFondo),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = mensaje,
            color = colorTexto,
            fontSize = 32.sp,
            fontWeight = androidx.compose.ui.text.font.FontWeight.Bold
        )
    }
}

// ── expect/actual — acceso directo a R de Android (opcional) ────────────
// commonMain/ResourceProvider.kt

expect fun getMensaje(): String
expect fun getColorTexto(): Long
expect fun getColorFondo(): Long

// ── androidMain/ResourceProvider.android.kt ─────────────────────────────

// actual fun getMensaje(): String =
//     applicationContext.getString(R.string.mensaje)
//
// actual fun getColorTexto(): Long =
//     ContextCompat.getColor(applicationContext, R.color.color_texto).toLong()
//
// actual fun getColorFondo(): Long =
//     ContextCompat.getColor(applicationContext, R.color.color_fondo).toLong()

// ── composeResources/values/strings.xml ─────────────────────────────────
/*
<?xml version="1.0" encoding="utf-8"?>
<resources>
    <string name="mensaje">¡Hola Mundo!</string>
</resources>
*/

// ── composeResources/values-en/strings.xml ──────────────────────────────
/*
<?xml version="1.0" encoding="utf-8"?>
<resources>
    <string name="mensaje">Hello World!</string>
</resources>
*/
