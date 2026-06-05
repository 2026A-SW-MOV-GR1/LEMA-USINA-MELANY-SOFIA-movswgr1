# Guía Práctica: Implementación de KMP para Gestión de Recursos Multidimensionales

## 📌 Introducción a Kotlin Multiplatform (KMP)

Kotlin Multiplatform permite compartir código entre diferentes plataformas (Android, iOS, etc.) mientras se permite código específico por plataforma cuando sea necesario.

### Conceptos Clave: expect/actual

```kotlin
// Fichero compartido (commonMain)
expect object ResourcesManager {
    fun getText(): String
    fun getTextColor(): Long
    fun getBackgroundColor(): Long
}

// Implementación Android (androidMain)
actual object ResourcesManager {
    // Implementación específica para Android
}

// Implementación iOS (iosMain)
actual object ResourcesManager {
    // Implementación específica para iOS
}
```

---

## 🎨 Sistema de Recursos de Android

### Estructura de Directorios

Los recursos en Android se organizan con **calificadores**:

```
res/
├── values/                    # Default (Spanish, Portrait)
├── values-en/                 # English language
├── values-land/               # Landscape orientation
└── values-en-land/            # English + Landscape
```

### Prioridad de Calificadores

Android sigue un orden específico para resolver recursos:

1. **MCC/MNC** (mobile country code)
2. **Language + Region** (es, en, fr, etc.)
3. **Layout Direction** (ldrtl, ldltr)
4. **Smallest Width** (sw600dp)
5. **Available Width** (w600dp)
6. **Available Height** (h800dp)
7. **Screen Size** (small, normal, large, xlarge)
8. **Screen Aspect** (long, notlong)
9. **Screen Orientation** (port, land)
10. **UI Mode** (car, desk, television, watch, etc.)
11. **Night Mode** (night, notnight)
12. **Screen Pixel Density** (ldpi, hdpi, xhdpi, etc.)
13. **Touchscreen Type** (notouch, finger)
14. **Keyboard Availability** (keysexposed, keyshidden)
15. **Primary Text Input Method** (nokeys, qwerty, 12key)
16. **Navigation Key Availability** (navexposed, navhidden)
17. **Primary Non-Touch Navigation** (nonav, dpad, trackball, wheel)
18. **Platform API Level** (v3, v4, v7, etc.)

### En Nuestro Caso

Utilizamos solo:
- **Language** (values-en para inglés)
- **Orientation** (values-land para landscape)

Combinaciones:
- `values/` → Portrait + Idioma por defecto (Español)
- `values-en/` → Portrait + Inglés
- `values-land/` → Landscape + Idioma por defecto (Español)
- `values-en-land/` → Landscape + Inglés

---

## 💻 Implementación Detallada

### 1. Declaración expect (Shared Code)

**Fichero**: `shared/src/commonMain/kotlin/com/example/kmpclient/ResourcesManager.kt`

```kotlin
package com.example.kmpclient

expect object ResourcesManager {
    fun getText(): String
    fun getTextColor(): Long
    fun getBackgroundColor(): Long
}
```

**Propósito**: Define el contrato que debe cumplir cada plataforma.

---

### 2. Implementación actual (Android)

**Fichero**: `composeApp/src/androidMain/kotlin/com/example/kmpclient/ResourcesManager.kt`

```kotlin
package com.example.kmpclient

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import android.content.Context
import androidx.core.content.ContextCompat

actual object ResourcesManager {
    private var context: Context? = null
    private var currentText by mutableStateOf("")
    private var currentTextColor by mutableStateOf(0L)
    private var currentBackgroundColor by mutableStateOf(0L)

    fun init(context: Context) {
        this.context = context
        updateResources()
    }

    fun updateResources() {
        context?.let { ctx ->
            val resources = ctx.resources
            
            // Obtener el ID del recurso usando getIdentifier
            val textId = resources.getIdentifier("text_content", "string", ctx.packageName)
            currentText = if (textId != 0) ctx.getString(textId) else "Default"
            
            // Obtener color del texto
            val textColorId = resources.getIdentifier("text_color", "color", ctx.packageName)
            currentTextColor = if (textColorId != 0) {
                ContextCompat.getColor(ctx, textColorId).toLong()
            } else {
                0xFF000000L
            }
            
            // Obtener color de fondo
            val bgColorId = resources.getIdentifier("background_color", "color", ctx.packageName)
            currentBackgroundColor = if (bgColorId != 0) {
                ContextCompat.getColor(ctx, bgColorId).toLong()
            } else {
                0xFFFFFFFF
            }
        }
    }

    actual fun getText(): String = currentText
    actual fun getTextColor(): Long = currentTextColor
    actual fun getBackgroundColor(): Long = currentBackgroundColor
}
```

**Características**:
- `getIdentifier()`: Obtiene el ID del recurso en tiempo de ejecución
- `updateResources()`: Recarga los recursos (llamado cuando cambia configuración)
- `mutableStateOf`: Permite que Compose reaccione a cambios

---

### 3. Activity con Detección de Cambios

**Fichero**: `composeApp/src/androidMain/kotlin/com/example/kmpclient/MainActivity.kt`

```kotlin
package com.example.kmpclient

import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.content.res.Configuration

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        
        // Inicializar el administrador de recursos
        ResourcesManager.init(this)
        
        setContent {
            App()
        }
    }

    override fun onConfigurationChanged(newConfig: Configuration) {
        super.onConfigurationChanged(newConfig)
        // Recargar recursos cuando cambia la configuración
        ResourcesManager.updateResources()
    }
}
```

**Nota Importante**: En `AndroidManifest.xml`, incluir:
```xml
android:configChanges="locale|orientation|screenSize"
```

Esto evita que Android destruya y recree la Activity.

---

### 4. Interfaz con Compose Multiplatform

**Fichero**: `composeApp/src/commonMain/kotlin/com/example/kmpclient/App.kt`

```kotlin
package com.example.kmpclient

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.sp

@Composable
fun App() {
    // Obtener valores del ResourcesManager
    val text = ResourcesManager.getText()
    val textColor = ResourcesManager.getTextColor()
    val backgroundColor = ResourcesManager.getBackgroundColor()

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(backgroundColor)),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = text,
            style = TextStyle(
                color = Color(textColor),
                fontSize = 24.sp
            )
        )
    }
}
```

**Ventajas**:
- ✅ Declarativo
- ✅ Reacciona automáticamente a cambios
- ✅ Sin boilerplate

---

## 📂 Archivos de Recursos XML

### Portrait - Español (Default)

**Fichero**: `composeApp/src/androidMain/res/values/strings.xml`

```xml
<?xml version="1.0" encoding="utf-8"?>
<resources>
    <string name="app_name">KMP Resources App</string>
    <string name="text_content">Texto A - Portrait Español</string>
    <color name="text_color">#000000</color>
    <color name="background_color">#FFFFFF</color>
</resources>
```

### Portrait - Inglés

**Fichero**: `composeApp/src/androidMain/res/values-en/strings.xml`

```xml
<?xml version="1.0" encoding="utf-8"?>
<resources>
    <string name="app_name">KMP Resources App</string>
    <string name="text_content">Text B - Portrait English</string>
    <color name="text_color">#FFFFFF</color>
    <color name="background_color">#2196F3</color>
</resources>
```

### Landscape - Español

**Fichero**: `composeApp/src/androidMain/res/values-land/strings.xml`

```xml
<?xml version="1.0" encoding="utf-8"?>
<resources>
    <string name="app_name">KMP Resources App</string>
    <string name="text_content">Texto C - Landscape Español</string>
    <color name="text_color">#1B5E20</color>
    <color name="background_color">#FFC107</color>
</resources>
```

### Landscape - Inglés

**Fichero**: `composeApp/src/androidMain/res/values-en-land/strings.xml`

```xml
<?xml version="1.0" encoding="utf-8"?>
<resources>
    <string name="app_name">KMP Resources App</string>
    <string name="text_content">Text D - Landscape English</string>
    <color name="text_color">#6A1B9A</color>
    <color name="background_color">#E91E63</color>
</resources>
```

---

## 🔍 Cómo Funciona el Flujo

```
┌─────────────────────────────────────────────────────────┐
│ Usuario cambia idioma o rota el dispositivo             │
└─────────────────┬───────────────────────────────────────┘
                  │
                  ▼
┌─────────────────────────────────────────────────────────┐
│ Android detecta cambio de configuración                 │
│ (onConfigurationChanged se ejecuta)                     │
└─────────────────┬───────────────────────────────────────┘
                  │
                  ▼
┌─────────────────────────────────────────────────────────┐
│ MainActivity.onConfigurationChanged(newConfig)          │
│ ↓                                                        │
│ ResourcesManager.updateResources()                      │
└─────────────────┬───────────────────────────────────────┘
                  │
                  ▼
┌─────────────────────────────────────────────────────────┐
│ ResourcesManager busca los recursos usando:            │
│ - resources.getIdentifier("text_content", "string", ...) │
│ - resources.getIdentifier("text_color", "color", ...)   │
│ - resources.getIdentifier("background_color", "color"...) │
└─────────────────┬───────────────────────────────────────┘
                  │
                  ▼
┌─────────────────────────────────────────────────────────┐
│ Android resuelve los recursos correctos:               │
│ - Si idioma = en: busca en values-en o values-en-land   │
│ - Si orientación = land: busca en values-land o values-en-land │
│ - Sino: busca en values                                 │
└─────────────────┬───────────────────────────────────────┘
                  │
                  ▼
┌─────────────────────────────────────────────────────────┐
│ Los estados (mutableStateOf) se actualizan            │
│ currentText, currentTextColor, currentBackgroundColor   │
└─────────────────┬───────────────────────────────────────┘
                  │
                  ▼
┌─────────────────────────────────────────────────────────┐
│ Compose recompone la UI automáticamente                │
│ App() se ejecuta con nuevos valores                     │
└─────────────────┬───────────────────────────────────────┘
                  │
                  ▼
┌─────────────────────────────────────────────────────────┐
│ ✅ La pantalla muestra:                                 │
│ - Nuevo texto                                           │
│ - Nuevo color del texto                                │
│ - Nuevo color de fondo                                 │
└─────────────────────────────────────────────────────────┘
```

---

## 🧪 Pruebas Manuales

### Pasos para Verificar

1. **Compilar y ejecutar**
   ```bash
   ./gradlew.bat :composeApp:installDebug
   ```

2. **Comprobar Portrait - Español** (default)
   - Texto: "Texto A - Portrait Español"
   - Color texto: Negro
   - Color fondo: Blanco

3. **Cambiar a Inglés**
   - Settings → System → Languages → Seleccionar English
   - Verificar: Texto B, color texto blanco, fondo azul

4. **Rotar a Landscape**
   - Presionar Ctrl+F11 (en emulador)
   - Verificar: Texto C, color texto verde, fondo amarillo

5. **Landscape + Inglés**
   - Verificar: Texto D, color texto púrpura, fondo rosa

---

## 🐛 Solución de Problemas

### Problema: Los recursos no cambian
**Solución**: Verificar que `android:configChanges="locale|orientation|screenSize"` esté en AndroidManifest.xml

### Problema: Error "getIdentifier returns 0"
**Solución**: Asegurarse de que los nombres en strings.xml coincidan exactamente con los usados en getIdentifier()

### Problema: La aplicación se cuelga
**Solución**: No llamar a updateResources() desde el hilo principal. Usar `runOnUiThread()` si es necesario.

---

## 📝 Checklist de Implementación

- [ ] Crear estructura base de KMP
- [ ] Implementar expect/actual para ResourcesManager
- [ ] Crear todos los archivos strings.xml con calificadores
- [ ] Modificar MainActivity para inicializar y actualizar recursos
- [ ] Implementar UI con Compose Multiplatform
- [ ] Agregar `configChanges` en AndroidManifest.xml
- [ ] Probar todas las 4 combinaciones de configuración
- [ ] Documentar el código
- [ ] Presentar el proyecto

---

## 🎓 Lecciones Aprendidas

1. **KMP permite código compartido** con implementaciones específicas por plataforma
2. **El sistema de recursos de Android** maneja automáticamente la resolución de recursos
3. **expect/actual** es el patrón fundamental para KMP
4. **Compose Multiplatform** simplifica la creación de UI multiplataforma
5. **onConfigurationChanged()** permite reaccionar a cambios sin recrear la Activity

---

**¡Felicidades! Ahora entiendes cómo funciona la gestión de recursos en KMP.**

