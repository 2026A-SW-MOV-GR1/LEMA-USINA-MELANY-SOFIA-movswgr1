# Informe de Experimento: Ciclo de Vida y Persistencia en KMP

**Tecnología:** Kotlin Multiplatform (KMP) con Compose Multiplatform.

---

## 1. Observación de la Tecnología Asignada

En Kotlin Multiplatform, especialmente al usar Compose Multiplatform para la UI, el comportamiento del estado depende de cómo se declare la variable en el código compartido (`commonMain`).

Al interactuar con la aplicación:
- **Suma del contador:** Funciona correctamente actualizando la UI en tiempo real.
- **Multitarea (Salir al Home y volver):** El contador **se mantiene**. Esto ocurre porque el proceso de la aplicación sigue vivo en segundo plano y la actividad no ha sido destruida, solo detenida (`onStop`).
- **Rotación:** El comportamiento depende de la función de estado utilizada (`remember` vs `rememberSaveable`).

---

## 2. Persistencia de Instancia: ¿Por qué vuelve a cero y cómo se soluciona?

### El Problema
Si utilizamos `var count by remember { mutableStateOf(0) }`, el contador **vuelve a cero** al rotar el celular. 

**Investigación:** 
En Android, una rotación de pantalla es un "cambio de configuración". Por defecto, Android destruye la actividad actual y crea una nueva para aplicar los recursos correspondientes (como layouts para horizontal). Como `remember` guarda el valor solo en la memoria de la composición actual, al destruirse la actividad y la composición, el valor se pierde.

### La Solución en Compose Multiplatform
A diferencia de Android Nativo donde se usaría `onSaveInstanceState` con un `Bundle`, en KMP la solución estándar es:

**`rememberSaveable`**

```kotlin
// Solución aplicada en App.kt
var count by rememberSaveable { mutableStateOf(0) }
```

**¿Cómo funciona?**
`rememberSaveable` es una versión avanzada de `remember` que sobrevive a la recreación de la actividad. En Android, utiliza internamente el mecanismo de `SavedInstanceState` (Bundle) de forma automática para guardar y restaurar el valor sin que el desarrollador tenga que escribir código manual en la `MainActivity`.

---

## 3. Documentación de Logs (Ciclo de Vida)

### Orden de los eventos durante la Rotación:
Al girar el celular, se disparan los logs en el siguiente orden exacto:

1. `onPause`: La actividad pierde el foco.
2. `onSaveInstanceState`: El sistema da la oportunidad de guardar el estado (aquí actúa `rememberSaveable`).
3. `onStop`: La actividad ya no es visible.
4. **`onDestroy`**: **Sí, se dispara**. La instancia actual de la actividad es destruida completamente.
5. `onCreate`: Se crea una nueva instancia de la misma actividad.
6. `onStart`: La nueva actividad se prepara para ser visible.
7. `onRestoreInstanceState`: El sistema restaura los datos guardados.
8. `onResume`: La actividad vuelve a estar en primer plano.

### Conclusiones del Ciclo de Vida:
- **¿Se dispara onDestroy?** Sí. En Android, la rotación implica una destrucción total de la actividad actual.
- **¿En qué momento?** Ocurre inmediatamente después de `onStop` y antes del nuevo `onCreate`.
- **Diferencia con Multitarea:** Al salir al Home, el ciclo se detiene en `onStop`. El `onDestroy` es la diferencia clave que obliga a usar mecanismos de persistencia como `rememberSaveable`.
