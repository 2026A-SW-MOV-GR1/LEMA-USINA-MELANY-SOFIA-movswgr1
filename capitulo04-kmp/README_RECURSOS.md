# Recursos Android - Estructura de Calificadores

## 📂 Descripción

Este directorio contiene los recursos de la aplicación organizados por **calificadores** para proporcionar diferentes valores según:
- **Idioma**: Spanish (default), English (en)
- **Orientación**: Portrait (default), Landscape (land)

---

## 🗂️ Estructura de Directorios

```
res/
├── values/
│   └── strings.xml (Portrait - Español)
├── values-en/
│   └── strings.xml (Portrait - Inglés)
├── values-land/
│   └── strings.xml (Landscape - Español)
└── values-en-land/
    └── strings.xml (Landscape - Inglés)
```

---

## 📋 Cada Configuración Contiene

### values/strings.xml
```xml
<resources>
    <string name="app_name">KMP Resources App</string>
    <string name="text_content">Texto A - Portrait Español</string>
    <color name="text_color">#000000</color>
    <color name="background_color">#FFFFFF</color>
</resources>
```

### values-en/strings.xml
```xml
<resources>
    <string name="app_name">KMP Resources App</string>
    <string name="text_content">Text B - Portrait English</string>
    <color name="text_color">#FFFFFF</color>
    <color name="background_color">#2196F3</color>
</resources>
```

### values-land/strings.xml
```xml
<resources>
    <string name="app_name">KMP Resources App</string>
    <string name="text_content">Texto C - Landscape Español</string>
    <color name="text_color">#1B5E20</color>
    <color name="background_color">#FFC107</color>
</resources>
```

### values-en-land/strings.xml
```xml
<resources>
    <string name="app_name">KMP Resources App</string>
    <string name="text_content">Text D - Landscape English</string>
    <color name="text_color">#6A1B9A</color>
    <color name="background_color">#E91E63</color>
</resources>
```

---

## 🔍 Cómo Android Resuelve los Recursos

### Prioridad de Resolución

1. El sistema operativo detecta el idioma y orientación actual
2. Android busca en el directorio con mayor especificidad
3. Si no encuentra, cae en el siguiente nivel

### Ejemplos de Resolución

#### Caso 1: Portrait - Español
```
Idioma: Español
Orientación: Portrait
↓
Android busca en: values-es-land → NO EXISTE
Android busca en: values-land → NO EXISTE
Android busca en: values-es → NO EXISTE
Android busca en: values ← ENCONTRADO ✅

Resultado: "Texto A - Portrait Español"
```

#### Caso 2: Portrait - Inglés
```
Idioma: Inglés
Orientación: Portrait
↓
Android busca en: values-en-land → NO EXISTE
Android busca en: values-land → NO EXISTE
Android busca en: values-en ← ENCONTRADO ✅

Resultado: "Text B - Portrait English"
```

#### Caso 3: Landscape - Español
```
Idioma: Español
Orientación: Landscape
↓
Android busca en: values-land ← ENCONTRADO ✅

Resultado: "Texto C - Landscape Español"
```

#### Caso 4: Landscape - Inglés
```
Idioma: Inglés
Orientación: Landscape
↓
Android busca en: values-en-land ← ENCONTRADO ✅

Resultado: "Text D - Landscape English"
```

---

## 🎨 Valores de Color Utilizados

| Configuración | Texto | Fondo |
|---|---|---|
| Portrait - ES | #000000 (Negro) | #FFFFFF (Blanco) |
| Portrait - EN | #FFFFFF (Blanco) | #2196F3 (Azul Material) |
| Landscape - ES | #1B5E20 (Verde oscuro) | #FFC107 (Amarillo) |
| Landscape - EN | #6A1B9A (Púrpura) | #E91E63 (Rosa Material) |

---

## ✏️ Cómo Modificar los Recursos

### Cambiar un Texto
1. Abre el archivo deseado (ej: `values-en/strings.xml`)
2. Edita el contenido dentro de `<string name="text_content">`
3. Guarda el archivo
4. Reconstruye el proyecto

### Cambiar un Color
1. Abre el archivo deseado
2. Edita el código hexadecimal en `<color name="...">XXXXXX</color>`
3. Guarda el archivo
4. Reconstruye el proyecto

### Agregar un Nuevo Recurso
1. Añade un nuevo elemento en cada archivo:
   ```xml
   <string name="nuevo_texto">Mi nuevo texto</string>
   ```
2. Accede desde Kotlin usando `ResourcesManager`

---

## 🔄 Cómo Se Cargan en la Aplicación

### En AndroidManifest.xml
```xml
android:configChanges="locale|orientation|screenSize"
```
Esto permite que la Activity detecte cambios sin destruirse.

### En MainActivity.kt
```kotlin
override fun onConfigurationChanged(newConfig: Configuration) {
    super.onConfigurationChanged(newConfig)
    ResourcesManager.updateResources()  // Recarga los recursos
}
```

### En ResourcesManager.kt
```kotlin
val textId = resources.getIdentifier("text_content", "string", packageName)
val text = ctx.getString(textId)  // Carga desde el directorio correcto
```

---

## ⚙️ Configuración Automática de Calificadores

Android Studio puede crear automáticamente los directorios:

1. **Haz clic derecho** en la carpeta `res`
2. **Selecciona** "New" → "Android Resource Directory"
3. **Elige** el tipo de calificador (Language, Orientation)
4. **Completa** los valores deseados
5. **Android Studio** creará la carpeta automáticamente

---

## 📚 Referencia de Calificadores

### Calificadores de Idioma
- `values` - Idioma por defecto
- `values-en` - Inglés
- `values-es` - Español
- `values-fr` - Francés
- Etc.

### Calificadores de Orientación
- `values` - Orientación por defecto (portrait)
- `values-land` - Landscape (apaisado)
- `values-port` - Portrait (vertical)

### Combinación de Calificadores
- `values-en-land` - Inglés + Landscape
- `values-es-port` - Español + Portrait
- Orden importa: Idioma primero, luego orientación

---

## 🚀 Buenas Prácticas

✅ **HACER**:
- Mantener consistencia de nombres entre directorios
- Usar identificadores claros y descriptivos
- Documentar cambios realizados
- Probar en todos los idiomas y orientaciones

❌ **NO HACER**:
- Hardcodear valores en Kotlin
- Crear carpetas con nombres errados
- Mezclar orden de calificadores
- Olvidar sincronizar entre idiomas

---

## 🧪 Verificación

Después de realizar cambios:

1. **Compilar**: `./gradlew.bat build`
2. **Instalar**: `./gradlew.bat :composeApp:installDebug`
3. **Probar cada configuración**:
   - Portrait - Español (default)
   - Portrait - Inglés (Settings → Languages → English)
   - Landscape - Español (Rotar pantalla + cambiar a español)
   - Landscape - Inglés (Rotar pantalla + seleccionar English)

---

## 📝 Notas Importantes

- Los recursos se cargan **en tiempo de ejecución**
- No requiere recompilación total si solo cambias valores
- Android maneja automáticamente la resolución
- Los cambios se aplican sin perder estado de la app

---

**Última actualización**: Mayo 2026  
**Versión**: 1.0

