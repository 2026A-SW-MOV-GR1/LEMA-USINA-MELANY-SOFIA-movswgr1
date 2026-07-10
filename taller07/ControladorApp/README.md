# Proyecto Contador - Taller 07 (KMP)

Esta es una aplicación de contador desarrollada con **Compose Multiplatform** para el taller de ciclo de vida y persistencia.

## 📝 Resultados del Experimento

### 1. Observación de la Tecnología
*   **Suma del contador:** La UI se actualiza reactivamente.
*   **Multitarea (Home):** Al salir al menú principal y volver, el contador **se mantiene**. La actividad entra en `onStop` pero no se destruye.
*   **Rotación:** 
    *   Inicialmente, con `remember`, el contador volvía a 0 porque la `MainActivity` se destruía.
    *   Tras la investigación, se aplicó la solución correcta para KMP.

### 2. Persistencia de Instancia
**Problema:** En Android, la rotación destruye la actividad. En KMP, si solo usamos `remember`, el estado se pierde en este proceso.

**Solución:** Se implementó **`rememberSaveable`**.
```kotlin
var count by rememberSaveable { mutableStateOf(0) }
```
A diferencia de Android nativo donde se gestiona manualmente el `Bundle` en `onSaveInstanceState`, `rememberSaveable` abstrae este proceso y funciona de forma transparente en las plataformas soportadas.

### 3. Registro del Ciclo de Vida (Logs)
Durante la **rotación**, el orden de ejecución capturado en Logcat es:
1. `onPause`
2. `onSaveInstanceState` (Persistencia del estado)
3. `onStop`
4. **`onDestroy`** (La actividad se elimina)
5. `onCreate` (Nueva instancia)
6. `onStart`
7. `onRestoreInstanceState` (Restauración del estado)
8. `onResume`

---

## Guía del Proyecto
* [/shared](./shared/src) es for code that will be shared across your Compose Multiplatform applications.
  It contains several subfolders:
  - [commonMain](./shared/src/commonMain/kotlin) is for code that’s common for all targets.
  - Other folders are for Kotlin code that will be compiled for only the platform indicated in the folder name.
    For example, if you want to use Apple’s CoreCrypto for the iOS part of your Kotlin app,
    the [iosMain](./shared/src/iosMain/kotlin) folder would be the right place for such calls.
    Similarly, if you want to edit the Desktop (JVM) specific part, the [jvmMain](./shared/src/jvmMain/kotlin)
    folder is the appropriate location.

### Running the apps

Use the run configurations provided by the run widget in your IDE's toolbar. You can also use these commands and options:

- Android app: `./gradlew :androidApp:assembleDebug`

### Running tests

Use the run button in your IDE's editor gutter, or run tests using Gradle tasks:

- Android tests: `./gradlew :shared:testAndroidHostTest`

---

Learn more about [Kotlin Multiplatform](https://www.jetbrains.com/help/kotlin-multiplatform-dev/get-started.html)…