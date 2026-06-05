KMP — Option A (ResourceProvider inyectado)

Qué contiene (mínimo entregable para el taller)
- `ResourceProvider.common.kt` — interfaz `ResourceProvider` y `LocalResourceProvider` (CompositionLocal).
- `ResourceProvider.android.kt` — implementación `AndroidResourceProvider` que resuelve recursos por nombre usando `Context`.
- `App.kt` — `AppUsingProvider()` muestra cómo consumir el `ResourceProvider` desde Compose.
- `ColorUtils.kt` — helper para convertir colores Android a `Color` de Compose.

Instrucción mínima para integrar en Android (MainActivity)
1. Cree una instancia de `AndroidResourceProvider` y pásela al `CompositionLocal` antes de renderizar la UI:

```kotlin
override fun onCreate(savedInstanceState: Bundle?) {
  super.onCreate(savedInstanceState)
  val provider = AndroidResourceProvider(applicationContext)
  setContent {
    CompositionLocalProvider(LocalResourceProvider provides provider) {
      // Llamar a la variante que usa el provider
      AppUsingProvider()
    }
  }
}
```

2. Recursos esperados (rápido):
- `res/values/strings.xml` debe tener `<string name="mensaje">...</string>`
- `res/values/colors.xml` o `res/values/` debe tener `<color name="color_texto">#...</color>` y `<color name="color_fondo">#...</color>`

Cómo cumple la consigna del taller
- Android decide qué recurso cargar con base en los calificadores (`values/`, `values-en/`, `values-land/`, `values-en-land/`).
- La UI Compose se recompone cuando cambia la configuración (usando `LocalConfiguration.current`) y vuelve a pedir los recursos al `ResourceProvider`.
- No hay `if (lang==...)` ni lógica de selección de idioma/orientación en la UI.

Pruebas rápidas
- Cambiar idioma en el emulador (Settings → System → Languages) a Español / English.
- Rotar pantalla (panel del emulador o Ctrl+F11 / Ctrl+F12).
- Verificar que `mensaje`, `color_texto` y `color_fondo` cambien según la combinación (portrait/es, portrait/en, landscape/es, landscape/en).

Si quieres, puedo insertar automáticamente el snippet en tu `MainActivity` (indícame la ruta del archivo) o leer tus `strings.xml` y `colors.xml` para asegurar que los nombres coincidan con los usados aquí.

