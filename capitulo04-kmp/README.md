# Taller Práctico - Punto 4: KMP (Kotlin Multiplatform)

## Gestión Multidimensional de Recursos en Frameworks Multiplataforma

### 📋 Descripción del Proyecto

Este proyecto implementa un sistema de personalización dinámica utilizando **Kotlin Multiplatform (KMP)** con **Compose Multiplatform**. El enfoque principal es gestionar internacionalización (i18n) y orientación de pantalla mediante el sistema de calificadores de recursos nativos de Android.

**Institución**: Escuela Politécnica Nacional (EPN)  
**Materia**: Aplicaciones Móviles  
**Grupo**: LEMA, USINA, MELANY, SOFÍA

---

### 🎯 Objetivo

Evaluar la capacidad de implementar sistemas de personalización dinámica que cambien automáticamente al detectar:
1. **Cambios de idioma** (Español ↔ Inglés)
2. **Cambios de orientación** (Portrait ↔ Landscape)

Los tres elementos siguientes deben cambiar simultáneamente:
- **Texto del contenido**
- **Color del texto**
- **Color de fondo**

---

### 📱 Matriz de Configuraciones Implementadas

| Configuración | Directorio | Texto | Color Texto | Color Fondo | Descripción |
|---|---|---|---|---|---|
| Portrait - Español | `values/` | Texto A | Negro (#000000) | Blanco (#FFFFFF) | Configuración por defecto |
| Portrait - Inglés | `values-en/` | Text B | Blanco (#FFFFFF) | Azul (#2196F3) | Idioma inglés en vertical |
| Landscape - Español | `values-land/` | Texto C | Verde (#1B5E20) | Amarillo (#FFC107) | Orientación horizontal en español |
| Landscape - Inglés | `values-en-land/` | Text D | Púrpura (#6A1B9A) | Rosa (#E91E63) | Orientación horizontal en inglés |

---

### 🏗️ Estructura del Proyecto

```
capitulo04-kmp/
├── composeApp/
│   ├── src/
│   │   ├── androidMain/
│   │   │   ├── kotlin/com/example/kmpclient/
│   │   │   │   ├── MainActivity.kt
│   │   │   │   └── ResourcesManager.kt (implementación actual para Android)
│   │   │   ├── res/
│   │   │   │   ├── values/strings.xml (Portrait - Español)
│   │   │   │   ├── values-en/strings.xml (Portrait - Inglés)
│   │   │   │   ├── values-land/strings.xml (Landscape - Español)
│   │   │   │   └── values-en-land/strings.xml (Landscape - Inglés)
│   │   │   └── AndroidManifest.xml
│   │   └── commonMain/
│   │       └── kotlin/com/example/kmpclient/
│   │           └── App.kt (Interfaz con Compose)
│   └── build.gradle.kts
│
├── shared/
│   ├── src/
│   │   └── commonMain/
│   │       └── kotlin/com/example/kmpclient/
│   │           └── ResourcesManager.kt (declaración expect)
│   └── build.gradle.kts
│
├── build.gradle.kts
├── settings.gradle.kts
├── gradle.properties
└── gradle/libs.versions.toml
```

---

### 🔑 Conceptos Implementados

#### 1. **Expect/Actual Pattern**
```kotlin
// shared/commonMain: ResourcesManager.kt
expect object ResourcesManager {
    fun getText(): String
    fun getTextColor(): Long
    fun getBackgroundColor(): Long
}

// composeApp/androidMain: ResourcesManager.kt
actual object ResourcesManager {
    // Implementación específica para Android
}
```

#### 2. **Resolución de Recursos Nativos Android**
El proyecto accede directamente al sistema de recursos de Android sin hardcoding:
```kotlin
val textColorId = resources.getIdentifier("text_color", "color", ctx.packageName)
currentTextColor = ContextCompat.getColor(ctx, textColorId).toLong()
```

#### 3. **Detección de Cambios de Configuración**
En `MainActivity.kt`:
```kotlin
override fun onConfigurationChanged(newConfig: Configuration) {
    super.onConfigurationChanged(newConfig)
    ResourcesManager.updateResources()
}
```

Configuración en `AndroidManifest.xml`:
```xml
android:configChanges="locale|orientation|screenSize"
```

#### 4. **Compose Multiplatform UI**
La interfaz utiliza composables que reaccionan a los cambios de recursos:
```kotlin
@Composable
fun App() {
    val text = ResourcesManager.getText()
    val textColor = ResourcesManager.getTextColor()
    val backgroundColor = ResourcesManager.getBackgroundColor()
    
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(backgroundColor)),
        contentAlignment = Alignment.Center
    ) {
        Text(text = text, color = Color(textColor))
    }
}
```

---

### 🚀 Instalación y Ejecución

#### Requisitos Previos
- **Android Studio** 2023.1.1 o superior
- **JDK 11** o superior
- **Android SDK** mínimo API 24
- **Gradle** 8.2 o superior

#### Pasos de Instalación

1. **Clonar/Crear el proyecto**
```bash
cd "C:\Users\Sofia\Documents\Septimo Semestre\App Moviles\Nueva carpeta\LEMA-USINA-MELANY-SOFIA-movswgr1"
```

2. **Sincronizar Gradle**
```bash
cd capitulo04-kmp
./gradlew.bat build
```

3. **Ejecutar la aplicación**
```bash
./gradlew.bat :composeApp:installDebug
```

---

### 📝 Cómo Probar

1. **Compilar el proyecto**
   - Android Studio → Build → Make Project

2. **Ejecutar en emulador o dispositivo**
   - Android Studio → Run → Run 'composeApp'

3. **Cambiar idioma del sistema**
   - Emulador: Settings → System → Languages & input → Languages
   - Física: Settings → System → Languages

4. **Cambiar orientación**
   - Emulador: Presionar Ctrl+F11 (o cmd+Fn+F11 en Mac)
   - Física: Rotar el dispositivo

5. **Observar los cambios**
   - El texto, color del texto y color de fondo cambiarán según la configuración

---

### 📌 Reglas Aplicadas (Sin Hardcoding)

✅ **Permitido:**
- Usar `resources.getIdentifier()`
- Usar `getString()` y `getColor()` del contexto
- Implementar expect/actual para plataformas específicas

❌ **Prohibido:**
- `if (language == "en") { ... }` condicionales basadas en idioma
- Condicionales basados en orientación
- Mapeo manual de textos y colores

---

### 🔄 Flujo de Cambio de Configuración

```
Usuario cambia idioma/orientación
         ↓
Android detecta cambio de configuración
         ↓
onConfigurationChanged() se ejecuta
         ↓
ResourcesManager.updateResources()
         ↓
Android resuelve los recursos correctos (getIdentifier)
         ↓
UI se actualiza con los nuevos valores
```

---

### 🛠️ Extensiones Futuras

- Implementar para iOS utilizando plataforma específica
- Agregar más idiomas (Francés, Portugués, etc.)
- Implementar animaciones de transición
- Agregar persistencia de preferencias del usuario

---

### 📚 Referencias

- [Kotlin Multiplatform Documentation](https://kotlinlang.org/docs/multiplatform.html)
- [Compose Multiplatform](https://www.jetbrains.com/help/compose-multiplatform/)
- [Android Resources Guide](https://developer.android.com/guide/topics/resources/providing-resources)
- [Configuration Changes Android](https://developer.android.com/guide/topics/resources/runtime-changes)

---

### 👥 Autores

**Grupo**: LEMA, USINA, MELANY, SOFÍA  
**Institución**: Escuela Politécnica Nacional  
**Semestre**: Séptimo  
**Año**: 2024

---

### 📄 Licencia

Proyecto académico - EPN

