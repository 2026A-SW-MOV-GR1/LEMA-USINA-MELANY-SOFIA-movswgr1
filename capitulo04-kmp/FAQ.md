# ❓ Preguntas Frecuentes (FAQ)

## 📖 Preguntas sobre el Proyecto

### P: ¿Dónde está el proyecto?
**R**: 
```
C:\Users\Sofia\Documents\Septimo Semestre\App Moviles\Nueva carpeta\
LEMA-USINA-MELANY-SOFIA-movswgr1\capitulo04-kmp
```

### P: ¿Cómo abro el proyecto?
**R**: 
1. Android Studio → File → Open
2. Navega a la carpeta
3. Haz clic en "Open"

### P: ¿Cuánto tiempo tarda la sincronización?
**R**: 5-10 minutos la primera vez (depende de tu conexión)

### P: ¿Necesito conexión a internet?
**R**: Sí, para descargar dependencias la primera vez

### P: ¿Puedo trabajar sin internet después?
**R**: Sí, una vez sincronizado puedes trabajar offline

---

## 🔧 Preguntas sobre Compilación

### P: ¿Qué significa "Gradle sync failed"?
**R**: 
- Problema de sincronización
- Solución: File → Invalidate Caches → Invalidate and Restart

### P: ¿Cómo limpiar la caché?
**R**: 
```bash
./gradlew.bat clean
```

### P: ¿Puedo compilar desde línea de comandos?
**R**: Sí
```bash
cd capitulo04-kmp
./gradlew.bat build
```

### P: ¿Es normal que tarde mucho en compilar?
**R**: Sí, la primera compilación tarda más (3-5 min). Las siguientes son rápidas.

### P: ¿Qué hago si falla la compilación?
**R**:
1. Limpia: `./gradlew.bat clean`
2. Reconstruye: `./gradlew.bat build`
3. Invalida caché de Android Studio

---

## 📱 Preguntas sobre Emulador

### P: ¿No tengo emulador, cómo creo uno?
**R**: 
1. Android Studio → Device Manager
2. Create Device
3. Selecciona dispositivo
4. Completa la configuración

### P: ¿Puedo usar un dispositivo físico?
**R**: Sí
1. Conecta por USB
2. Activa "Developer Mode"
3. Autoriza la conexión
4. Run normalmente

### P: ¿Cómo rotar la pantalla en emulador?
**R**: Presiona `Ctrl+F11` (o cmd+Fn+F11 en Mac)

### P: ¿El emulador está muy lento?
**R**: 
- Habilita Acceleration (HAXM/KVM)
- Aumenta RAM del emulador
- Usa dispositivo físico

### P: ¿Cómo cambiar el idioma del sistema?
**R**:
1. Settings
2. System
3. Languages & input
4. Languages
5. Selecciona English o Español

---

## 🎨 Preguntas sobre Configuraciones

### P: ¿Cómo cambio el texto en una configuración?
**R**:
1. Abre el archivo strings.xml correspondiente
2. Edita el contenido
3. Reconstruye el proyecto

### P: ¿Cómo cambio los colores?
**R**:
```xml
<color name="text_color">#NUEVCOLOR</color>
```

### P: ¿Puedo agregar más idiomas?
**R**: Sí
1. Crea carpeta `values-XX` (XX = código idioma)
2. Copia strings.xml
3. Traduce los valores

### P: ¿Puedo agregar más configuraciones?
**R**: Sí, pero debes:
1. Crear nuevos directorios con calificadores
2. Agregar recursos
3. Probar cada combinación

### P: ¿Qué pasa si olvido crear un recurso?
**R**: 
- Android usa el fallback (values/)
- Puede no ser lo deseado
- Es mejor mantener consistencia

---

## 💻 Preguntas sobre Código

### P: ¿Qué es expect/actual?
**R**: 
- Pattern de KMP
- `expect`: Define interfaz (compartida)
- `actual`: Implementa para cada plataforma

### P: ¿Puedo cambiar el package name?
**R**: Sí, pero cuidado:
1. Cambia en AndroidManifest.xml
2. Cambia en build.gradle.kts
3. Cambia en archivos Kotlin
4. Reconstruye todo

### P: ¿Dónde está el código de la Activity?
**R**: 
```
composeApp/src/androidMain/kotlin/com/example/kmpclient/MainActivity.kt
```

### P: ¿Dónde está el ResourcesManager?
**R**: 
- Interfaz: `shared/src/commonMain/kotlin/.../ResourcesManager.kt`
- Implementación: `composeApp/src/androidMain/kotlin/.../ResourcesManager.kt`

### P: ¿Dónde está la UI (App.kt)?
**R**:
```
composeApp/src/commonMain/kotlin/com/example/kmpclient/App.kt
```

### P: ¿Puedo modificar directamente los archivos?
**R**: Sí, pero respeta la estructura

---

## 🧪 Preguntas sobre Pruebas

### P: ¿Cómo pruebo todas las configuraciones?
**R**:
1. Inicia app (Portrait-ES)
2. Cambia a English en Settings
3. Rota pantalla (Ctrl+F11)
4. Cambia a Español
5. Rota de nuevo

### P: ¿Cómo verifico que funciona correctamente?
**R**: Usa el checklist en MATRIZ_VISUAL.md

### P: ¿Qué debo ver en cada configuración?
**R**: Revisa CHECKLIST_VERIFICACION.md

### P: ¿Los cambios son inmediatos?
**R**: Sí, sin necesidad de reiniciar la app

### P: ¿Por qué no cambia nada cuando cambio idioma?
**R**: 
- No sincronizaste Gradle
- No reconstruiste
- El idioma no está configurado correctamente

---

## 📚 Preguntas sobre Documentación

### P: ¿Por dónde empiezo a leer?
**R**: 
1. INICIO_RAPIDO.md (5 min)
2. RESUMEN_EJECUTIVO.md (5 min)
3. GUIA_PRACTICA.md (30 min)

### P: ¿Dónde está la documentación?
**R**: Todo está en la carpeta capitulo04-kmp

### P: ¿Hay documentación sobre iOS?
**R**: Sí, EXTENSION_iOS.md

### P: ¿Hay guía de troubleshooting?
**R**: Sí, en GUIA_PRACTICA.md

### P: ¿Hay ejemplos de código?
**R**: Sí, distribuidos en varios documentos

---

## 🚀 Preguntas sobre Ejecución

### P: ¿Cuál es el flujo para ejecutar?
**R**:
1. Build → Make Project
2. Run → Run 'composeApp'
3. Selecciona emulador
4. Presiona OK

### P: ¿Cómo instalo sin ejecutar?
**R**:
```bash
./gradlew.bat :composeApp:installDebug
```

### P: ¿Cómo veo los logs?
**R**:
```bash
adb logcat | findstr "RESOURCES"
```

### P: ¿Cómo reseteo la app?
**R**:
```bash
adb shell pm clear com.example.kmpclient
```

### P: ¿Cómo desinstalo la app?
**R**:
```bash
adb uninstall com.example.kmpclient
```

---

## 🤔 Preguntas Conceptuales

### P: ¿Por qué no puedo usar hardcoding?
**R**: 
- Es un requisito del taller
- No es escalable
- No es mantenible
- Viola principios de arquitectura

### P: ¿Por qué usar expect/actual?
**R**:
- Permite código compartido
- Implementaciones específicas por plataforma
- Estándar de KMP

### P: ¿Por qué Android Resources?
**R**:
- Requisito del taller
- Sistema nativo de Android
- Automatic resolution
- Respeta calificadores

### P: ¿Por qué Compose Multiplatform?
**R**:
- Declarativa
- Reactive
- Multiplatforma
- Moderna

### P: ¿Cuál es la diferencia entre expect y actual?
**R**:
```
expect = Define QUÉ
actual = Define CÓMO
```

---

## 🎯 Preguntas sobre Presentación

### P: ¿Qué debo mostrar en la presentación?
**R**:
1. Las 4 configuraciones funcionando
2. Cambios dinámicos de idioma
3. Cambios dinámicos de orientación
4. La arquitectura KMP

### P: ¿Cómo demuestro que no hay hardcoding?
**R**: Muestra el código:
- Sin `if (language == "en")`
- Con `getIdentifier()`

### P: ¿Qué tiempo debo usar?
**R**: 10-15 minutos

### P: ¿Debo memorizar el código?
**R**: No, pero entiende los conceptos

### P: ¿Qué preguntas podrían hacer?
**R**:
- ¿Cómo funciona expect/actual?
- ¿Cómo Android resuelve recursos?
- ¿Qué pasa al cambiar idioma?
- ¿Por qué no hardcoding?

---

## 🆘 Problemas Específicos

### P: "Error: No android app"
**R**: Asegúrate de estar en la carpeta `capitulo04-kmp`

### P: "Cannot find MainActivity"
**R**: Verifica que existe:
```
composeApp/src/androidMain/kotlin/com/example/kmpclient/MainActivity.kt
```

### P: Los colores no se ven
**R**: 
- Verifica el nombre en strings.xml
- Verifica el getIdentifier()
- Reconstruye

### P: El texto no cambia al rotar
**R**:
- Verifica android:configChanges
- Verifica onConfigurationChanged()
- Reconstruye

### P: El emulador no responde
**R**:
- Reinicia el emulador
- O usa un dispositivo físico

---

## 💡 Tips y Trucos

### Tip 1: Compilación rápida
```bash
./gradlew.bat :composeApp:assembleDebug
```

### Tip 2: Ver estructura de proyecto
```
Android Studio → View → Tool Windows → Project
```

### Tip 3: Buscar archivos
```
Ctrl+Shift+N (Android Studio)
```

### Tip 4: Ir a clase
```
Ctrl+N (Android Studio)
```

### Tip 5: Ver todos los cambios
```
Git → Show History (si usas Git)
```

---

## 📞 ¿Aún tienes preguntas?

1. Revisa el README.md
2. Revisa GUIA_PRACTICA.md
3. Revisa NOTAS_TECNICAS.md
4. Busca en los archivos markdown
5. Revisa el código con comentarios

---

**Última actualización**: Mayo 2026  
**Versión**: 1.0

*Si tienes más preguntas, crea una pregunta nueva basada en esta estructura* 🚀

