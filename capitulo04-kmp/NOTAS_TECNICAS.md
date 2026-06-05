# Notas Técnicas - Arquitectura KMP

## 🏗️ Decisiones Arquitectónicas

### 1. Pattern expect/actual vs. Dependency Injection

**Elegido**: expect/actual

**Razón**: 
- Más directo y apropiado para code-sharing en KMP
- Evita dependencias externas
- Permite acceso directo a APIs nativas (R.string, ContextCompat)

**Alternativa rechazada**: Dependency Injection
- Más boilerplate
- Complejidad innecesaria para este caso de uso

---

### 2. Estado con mutableStateOf vs. StateFlow

**Elegido**: mutableStateOf

**Razón**:
- Integrado directamente con Compose
- Recomposición automática
- Más simple para UI Multiplatform

**Nota**: Para backend compartido complejo, StateFlow sería mejor opción.

---

### 3. Recurso Resolution en tiempo de ejecución

**getIdentifier() vs. Referencia directa a R class**

```kotlin
// ❌ NO FUNCIONA EN RUNTIME (compilado)
val id = R.string.text_content

// ✅ FUNCIONA EN RUNTIME (dinámico)
val id = resources.getIdentifier("text_content", "string", packageName)
```

**Razón**: Necesitamos resolver dinámicamente según locale y orientation.

---

### 4. onConfigurationChanged vs. recreate Activity

**Elegido**: onConfigurationChanged

**Razón**:
- Más eficiente (no destruye/recrea UI)
- Preserva estado
- Transición suave

**Configuración requerida**:
```xml
android:configChanges="locale|orientation|screenSize"
```

---

## 🔧 Detalles de Implementación

### Conversión de Color

```kotlin
// De Int (color nativo Android) a Long (para Compose)
val intColor: Int = ContextCompat.getColor(ctx, colorId) // -16777216 (negro)
val longColor: Long = intColor.toLong()

// En Compose
Color(longColor) // Interpreta como ARGB
```

**Nota importante**: Los colores se guardan como Long para compatibilidad multiplataforma.

---

### Estructura de Módulos

```
shared/
└── commonMain/           ← Código compartido (expect)
    └── ResourcesManager  ← Define contrato

composeApp/
├── androidMain/          ← Código específico Android (actual)
│   ├── ResourcesManager  ← Implementación Android
│   ├── MainActivity      ← Activity con ciclo de vida
│   └── res/              ← Recursos con calificadores
└── commonMain/           ← UI compartida
    └── App.kt            ← Composables multiplataforma
```

---

## 🎯 Casos de Uso por Calificador

### Resolución de Recursos por Android

```
Cambio de idioma a Inglés:
  resources.getIdentifier("text_content", "string", packageName)
  ↓
  Android busca en: values-en/strings.xml
  ↓
  Retorna: "Text B - Portrait English"

Cambio de orientación a Landscape:
  resources.getIdentifier("background_color", "color", packageName)
  ↓
  Android busca en: values-land/strings.xml
  ↓
  Retorna: #FFC107 (amarillo)

Cambio a Landscape + Inglés:
  Android busca en: values-en-land/strings.xml
  ↓
  Mayor especificidad = mayor prioridad
```

---

## 📊 Performance Considerations

### Optimizaciones Aplicadas

1. **Lazy loading**: Recursos se cargan solo cuando se necesitan
2. **Caching**: IDs de recursos se resuelven una sola vez por sesión
3. **Recomposición selectiva**: Compose recompone solo Box + Text cuando cambian

### Benchmark Esperado

- Resolución inicial: ~5-10ms
- Actualización al cambiar idioma/orientación: <50ms
- Memory footprint: <5MB adicional

---

## 🔐 Seguridad y Validación

### Validación de Recursos

```kotlin
val textId = resources.getIdentifier("text_content", "string", ctx.packageName)
if (textId != 0) {
    // Recurso existe
    currentText = ctx.getString(textId)
} else {
    // Fallback a valor por defecto
    currentText = "Default Text"
}
```

### Manejo de Valores Nulos

```kotlin
currentBackgroundColor = if (bgColorId != 0) {
    ContextCompat.getColor(ctx, bgColorId).toLong()
} else {
    0xFFFFFFFFl // Blanco por defecto
}
```

---

## 🌐 Multiplatforma: Extensión a iOS

Para completar KMP con iOS:

```kotlin
// iosMain/ResourcesManager.kt
actual object ResourcesManager {
    actual fun getText(): String {
        // Acceder a Strings.swift
    }
    
    actual fun getTextColor(): Long {
        // Acceder a Colors.swift
    }
    
    actual fun getBackgroundColor(): Long {
        // Acceder a Colors.swift
    }
}
```

---

## 🔄 Ciclo de Vida y Estado

### Flujo Completo

```
MainActivity.onCreate()
  ↓
ResourcesManager.init(context)
  ↓
updateResources()
  ↓
mutableStateOf actualiza
  ↓
setContent { App() }
  ↓
App() lee estado actualizado
  ↓
Compose renderiza UI

---

Usuario cambia idioma
  ↓
onConfigurationChanged()
  ↓
ResourcesManager.updateResources()
  ↓
Los mutableStateOf notifican cambios
  ↓
Compose recompone automáticamente
```

---

## 💡 Ventajas de Este Enfoque vs. Alternativas

### vs. Hardcoding (❌ Prohibido)
```kotlin
// ❌ MALO
if (locale.language == "en") {
    text = "English Text"
} else {
    text = "Spanish Text"
}
```

**Problemas**: Mantenibilidad, escalabilidad, violación de requisitos

### vs. Librerías i18n (❌ Prohibido)
```kotlin
// ❌ MALO (según requisitos)
val text = i18n.t("text_content") // React Native i18next style
```

**Razón del requisito**: Usar sistema nativo de Android

### ✅ CORRECTO: Sistema nativo de Android
```kotlin
// ✅ BUENO
val id = resources.getIdentifier("text_content", "string", packageName)
val text = ctx.getString(id)
```

**Ventajas**:
- Usa mecanismos nativos de Android
- Respeta jerarquía de calificadores
- Sin dependencias externas
- Máximo rendimiento

---

## 🐛 Debugging Tips

### Verificar Resolución de Recursos

```kotlin
val resources = context.resources
val config = resources.configuration

Log.d("RESOURCES", "Locale: ${config.locale}")
Log.d("RESOURCES", "Orientation: ${config.orientation}")

val textId = resources.getIdentifier("text_content", "string", packageName)
Log.d("RESOURCES", "Text ID: $textId")
```

### Verificar Calificadores Activos

Android Studio → Logcat:
```
D/ResourcesManager: Loaded text: "Texto A - Portrait Español"
D/ResourcesManager: Text color: -16777216 (negro)
D/ResourcesManager: Background: -1 (blanco)
```

---

## 📈 Roadmap Futuro

1. **iOS Implementation**
   - Localizable.strings
   - Colors.swift
   - MethodChannel para sincronización

2. **Animaciones**
   - Transiciones entre temas
   - Smooth color changes

3. **Persistencia**
   - Guardar preferencias de usuario
   - Override automático del sistema

4. **Testing**
   - Unit tests para ResourcesManager
   - UI tests para diferentes locales

5. **Accesibilidad**
   - Text-to-speech
   - High contrast mode

---

## 📚 Referencias Técnicas

### Documentación Oficial

- [Kotlin Multiplatform Mobile](https://kotlinlang.org/docs/mobile/home.html)
- [Compose Multiplatform](https://www.jetbrains.com/help/compose-multiplatform/getting-started.html)
- [Android Resources](https://developer.android.com/guide/topics/resources/overview)
- [Configuration Changes](https://developer.android.com/guide/topics/resources/runtime-changes)

### Especificaciones de Calificadores

- [Resource Qualifiers](https://developer.android.com/guide/topics/resources/providing-resources.html#QualifierRules)
- [Language and Region](https://developer.android.com/guide/topics/resources/providing-resources.html#LanguageQualifier)
- [Orientation](https://developer.android.com/guide/topics/resources/providing-resources.html#OrientationQualifier)

---

**Documento Técnico v1.0** - Proyecto KMP Gestión Multidimensional de Recursos

