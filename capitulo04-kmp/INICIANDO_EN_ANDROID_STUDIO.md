# INICIANDO EL PROYECTO EN ANDROID STUDIO

## 📋 Pre-requisitos

Asegúrate de tener instalado:
- ✅ Android Studio 2023.1.1 o superior
- ✅ JDK 11 o superior
- ✅ Android SDK (mínimo API 24)
- ✅ Gradle 8.2

---

## 🚀 Pasos para Abrir el Proyecto

### 1. Abrir Android Studio

### 2. Seleccionar "Open an Existing Project"
   - Navega a: `C:\Users\Sofia\Documents\Septimo Semestre\App Moviles\Nueva carpeta\LEMA-USINA-MELANY-SOFIA-movswgr1\capitulo04-kmp`
   - Haz clic en "Open"

### 3. Esperar la Sincronización Inicial
   - Android Studio descargará todas las dependencias
   - Puede tomar 5-10 minutos la primera vez

### 4. Configurar local.properties (IMPORTANTE)

Si necesitas, copia el contenido de `local.properties.template` a `local.properties`:

```properties
sdk.dir=C:\\Users\\Sofia\\AppData\\Local\\Android\\Sdk
android.useAndroidX=true
android.enableJetifier=true
```

**Reemplaza el path con tu SDK actual si es diferente.**

### 5. Build → Make Project

En el menú: **Build → Make Project**

**Esperado**: Build exitoso sin errores

---

## 📱 Ejecutar en Emulador

### Opción A: Desde Android Studio
1. **Device Manager** → Crea un emulador si no tienes uno
2. **Run → Run 'composeApp'**
3. Selecciona el emulador
4. Presiona OK

### Opción B: Desde Terminal
```bash
cd "C:\Users\Sofia\Documents\Septimo Semestre\App Moviles\Nueva carpeta\LEMA-USINA-MELANY-SOFIA-movswgr1\capitulo04-kmp"
./gradlew.bat :composeApp:installDebug
adb shell am start -n com.example.kmpclient/.MainActivity
```

---

## ✅ Verificación de Instalación Exitosa

### Indicadores de Éxito:
- ✅ Android Studio no muestra errores rojos
- ✅ El proyecto se puede compilar (Build exitoso)
- ✅ Puedes ejecutar la app en emulador

### Indicadores de Problemas:
- ❌ Errores en `build.gradle.kts`
- ❌ Mensaje: "Gradle sync failed"
- ❌ Errores de compilación Kotlin

---

## 🧪 Prueba Rápida

1. Ejecuta la app
2. Deberías ver:
   - Un cuadro blanco (fondo)
   - Texto negro en el centro: **"Texto A - Portrait Español"**

Si ves esto, ¡todo está correcto! ✅

---

## 🔍 Estructura del Proyecto en Android Studio

```
capitulo04-kmp (Project Root)
├── composeApp (Módulo aplicación)
│   ├── src
│   │   ├── androidMain
│   │   │   ├── kotlin ← Código Kotlin específico Android
│   │   │   ├── res ← Recursos (strings.xml, colors)
│   │   │   └── AndroidManifest.xml
│   │   └── commonMain
│   │       └── kotlin ← Código compartido (UI Compose)
│   └── build.gradle.kts
│
├── shared (Módulo de código compartido)
│   ├── src
│   │   └── commonMain
│   │       └── kotlin ← Interfaces expect/actual
│   └── build.gradle.kts
│
├── gradle
│   └── libs.versions.toml
├── build.gradle.kts
├── settings.gradle.kts
└── gradle.properties
```

---

## 📝 Editar Archivos Importantes

### Para cambiar los textos:
1. **Portrait - Español**: `composeApp/src/androidMain/res/values/strings.xml`
2. **Portrait - Inglés**: `composeApp/src/androidMain/res/values-en/strings.xml`
3. **Landscape - Español**: `composeApp/src/androidMain/res/values-land/strings.xml`
4. **Landscape - Inglés**: `composeApp/src/androidMain/res/values-en-land/strings.xml`

### Para cambiar los colores:
- Edita el elemento `<color>` en los mismos archivos

### Para cambiar la lógica de recursos:
1. `composeApp/src/androidMain/kotlin/com/example/kmpclient/ResourcesManager.kt`
2. `shared/src/commonMain/kotlin/com/example/kmpclient/ResourcesManager.kt`

---

## 🎮 Controles del Emulador

| Acción | Tecla |
|--------|-------|
| Rotar pantalla | Ctrl + F11 |
| Abrir menu | Right-Click |
| Back | Esc |
| Home | Home |
| Volumen arriba | Page Up |
| Volumen abajo | Page Down |

---

## 🐛 Troubleshooting

### Problema: "Gradle sync failed"
**Solución**:
1. File → Invalidate Caches → Invalidate and Restart
2. Espera a que sincronice de nuevo
3. Build → Clean Project

### Problema: "Cannot find symbol 'ResourcesManager'"
**Solución**:
1. Asegurate que los archivos expect/actual existen
2. Build → Clean Project
3. Build → Rebuild Project

### Problema: "AndroidManifest.xml not found"
**Solución**:
1. Verifica que existe: `composeApp/src/androidMain/AndroidManifest.xml`
2. Si no existe, cópialo de la documentación

### Problema: "Emulador no inicia"
**Solución**:
1. Tools → Device Manager → Crear nuevo emulador
2. O instalar desde línea de comandos:
   ```bash
   adb devices
   ```

---

## 📚 Documentación Disponible en el Proyecto

Después de abrir el proyecto, puedes leer:
- **README.md** - Visión general
- **GUIA_PRACTICA.md** - Guía paso a paso
- **NOTAS_TECNICAS.md** - Detalles técnicos
- **CHECKLIST_VERIFICACION.md** - Requisitos cumplidos

---

## 🎯 Próximos Pasos

1. ✅ Abre el proyecto en Android Studio
2. ✅ Sincroniza Gradle
3. ✅ Compila el proyecto
4. ✅ Ejecuta en emulador
5. ✅ Prueba las 4 configuraciones
6. ✅ Lee la documentación

---

## 💡 Consejos Útiles

- **Sincroniza frecuentemente**: Si haces cambios en build.gradle, presiona "Sync Now"
- **Limpia antes de compilar**: Si tengo problemas, usa Build → Clean Project
- **Lee los mensajes de error**: Generalmente son muy específicos
- **Invalida caché**: Si persisten problemas, Invalidate Caches

---

## 🚀 ¡Listo para Comenzar!

Ya tienes todo configurado. Solo:
1. Abre el proyecto
2. Sincroniza
3. Compila
4. ¡Disfruta! 🎉

---

**Fecha de creación**: Mayo 2026  
**Versión**: 1.0  
**Grupo**: LEMA, USINA, MELANY, SOFÍA

