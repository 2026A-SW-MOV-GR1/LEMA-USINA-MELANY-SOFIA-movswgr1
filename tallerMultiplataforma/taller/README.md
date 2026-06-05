# Taller — Gestión Multidimensional de Recursos Multiplataforma
## EPN · Aplicaciones Móviles 2026

Los recursos base (strings.xml / colors.xml con calificadores) ya están
configurados en el proyecto `capitulo01`. Cada tecnología los reutiliza.

---

## Matriz de configuraciones (ya implementada en el proyecto base)

| Carpeta               | Idioma  | Orient.  | Texto             | Color texto | Fondo   |
|-----------------------|---------|----------|-------------------|-------------|---------|
| `values/`             | ES      | Portrait | ¡Hola Mundo!      | #1A237E     | #FFF9C4 |
| `values-en/`          | EN      | Portrait | Hello World!      | #1B5E20     | #BBDEFB |
| `values-land/`        | ES      | Landscape| ¡Modo Horizontal! | #B71C1C     | #C8E6C9 |
| `values-en-land/`     | EN      | Landscape| Landscape Mode!   | #4A148C     | #FFE0B2 |

---

## Opción 1 — WebView

### Archivos
- `webview/MainActivity.kt`        → reemplaza el MainActivity original
- `webview/activity_main_webview.xml` → nuevo layout (res/layout/)
- `webview/index.html`             → copiar a app/src/main/assets/
- `webview/AndroidManifest_patch.xml` → leer e incorporar al Manifest

### Pasos
1. Copiar `index.html` a `app/src/main/assets/` (crear la carpeta si no existe).
2. Reemplazar `activity_main.xml` por el nuevo layout o renombrar.
3. Actualizar `AndroidManifest.xml` con `android:configChanges`.
4. Reemplazar `MainActivity.kt`.

---

## Opción 2 — React Native

### Archivos
- `react_native/ResourceModule.kt` → módulo nativo (android/app/src/.../modules/)
- `react_native/App.tsx`           → componente raíz

### Pasos
1. Crear `ResourceModule.kt` en el paquete nativo.
2. Registrar el módulo en `MainApplication.kt` dentro de `getPackages()`.
3. Reemplazar `App.tsx`.
4. `npx react-native run-android`

---

## Opción 3 — Flutter

### Archivos
- `flutter/MainActivity.kt`  → reemplaza el MainActivity de Flutter
- `flutter/main.dart`        → entrypoint de Flutter

### Pasos
1. Copiar `MainActivity.kt` a `android/app/src/main/kotlin/.../`.
2. Asegurarse de que `strings.xml` y `colors.xml` existan en el módulo Android.
3. Reemplazar `lib/main.dart`.
4. `flutter run`

---

## Opción 4 — KMP / Compose Multiplatform

### Archivos
- `kmp/App.kt`  → estructura y comentarios del proyecto

### Pasos
1. Crear la estructura `composeResources/values[-en][-land]/` en `commonMain`.
2. Copiar `strings.xml` y `colors.xml` de `capitulo01` a cada carpeta.
3. Usar `stringResource(Res.string.mensaje)` y `colorResource(Res.color.*)`.
4. Si se necesita acceso directo al R de Android, implementar `expect/actual`.

---

## Opción 5 — NativeScript

### Archivos
- `nativescript/main-view-model.ts`
- `nativescript/main-page.xml`
- `nativescript/main-page.ts`

### Pasos
1. Inicializar proyecto NS: `ns create myapp --ts`
2. Copiar los 3 archivos reemplazando los que genera NS.
3. Copiar las carpetas `values[-en][-land]/` del proyecto Android a
   `App_Resources/Android/src/main/res/`.
4. `ns run android`

---

## Reglas del taller (recordatorio)

- **Sin hardcoding**: no `if (language == "en")` en la lógica de UI.
- **Sin CSS Media Queries** (WebView) ni librerías JS de i18n (RN/NS).
- **Sin Intl / .arb** (Flutter).
- El cambio de recursos debe ocurrir vía `onConfigurationChanged` o equivalente.
- La jerarquía de calificadores la gestiona Android, no el código de la app.
