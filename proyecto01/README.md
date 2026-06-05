# Informe de Laboratorio Práctico: Red y Seguridad en Android

**Institución:** Escuela Politécnica Nacional  
**Facultad:** Facultad de Ingeniería de Sistemas (FIS)  
**Asignatura:** Aplicaciones Móviles 
**Nombre:** Melany Lema
**Entorno de Desarrollo:** Kotlin Multiplatform (KMP) con Compose Multiplatform  


---

## 1. Objetivos del Proyecto
* **Conectividad Externa:** Implementar un cliente de red asíncrono para consumir e interactuar con servicios web REST externos empleando un Fake API.
* **Control de Ciclo de Vida y Estados:** Diseñar interfaces reactivas capaces de manejar estados de carga de red (*loading states*) para inhabilitar componentes gráficos en tránsito, optimizando la UX.
* **Persistencia Segura Local:** Evaluar e integrar mecanismos nativos de persistencia en el sistema operativo Android (`SharedPreferences`, `Jetpack DataStore` y `EncryptedSharedPreferences`) bajo principios de aislamiento y conocimiento previo de llave.

---

## 2. Arquitectura de la Solución (KMP)
El proyecto aprovecha la arquitectura multipromesa de **Kotlin Multiplatform** a través del bloque de abstracción `expect / actual`. Toda la capa de presentación (UI) y la lógica de red se ejecutan en la sección común, mientras que los motores de persistencia se inyectan de forma nativa en la plataforma de Android:

```text
shared/
├── commonMain/kotlin/ec.edu.epn.proyecto01/
│   ├── App.kt           <-- Interfaz gráfica unificada en pestañas y lógica reactiva.
│   ├── Post.kt          <-- Entidad de datos serializable para la API REST.
│   └── StoragePlatform.kt <-- Declaración contractual 'expect' del almacenamiento.
└── androidMain/kotlin/ec.edu.epn.proyecto01/
    ├── ContextProvider.kt <-- Proveedor estático del Context subyacente de Android.
    └── StoragePlatform.kt <-- Implementación 'actual' de las APIs nativas de disco.

```

## 3. Desglose Tecnológico de los Módulos

### Módulo 1: Conectividad REST HTTP (30% de Evaluación)
Para la comunicación externa se integró **Ktor Client**, la librería de red multiplataforma.
* **Consulta (GET):** Se ejecuta una petición asíncrona hacia `/posts/{id}` restringida por una entrada de texto puramente numérica. El JSON devuelto se serializa a un objeto fuertemente tipado (`Post.kt`) para pintar los campos editables.
* **Actualización (PUT):** Permite la modificación local del título y cuerpo del post, enviando el objeto modificado en formato JSON de vuelta al recurso. Se captura el código de estado HTTP `200 OK` de manera reactiva para confirmar la validez de la transacción en la interfaz.
* **Manejo de Estados de Carga:** Se utiliza un estado booleano mutable conectado a las corrutinas de Kotlin. Durante el tránsito de la petición, se deshabilitan por completo los campos de texto e inputs de comando para bloquear llamadas concurrentes destructivas.

### Módulo 2: Almacenamiento Seguro (30% de Evaluación)
Se construyó una interfaz transaccional directa sin listado de claves que opera directamente sobre tres abstracciones nativas del sistema operativo Android:

* **SharedPreferences:** Almacenamiento Clave-Valor plano, síncrono y directo sobre archivos XML. Se usa para preferencias sencillas y estados de UI rápidos en memoria sin información crítica. No posee encriptación (Texto Plano).
* **Jetpack DataStore (Preferences):** Alternativa moderna y reactiva basada en Kotlin Flow Streams. Almacenamiento asíncrono robusto que previene el bloqueo del hilo principal de la UI. No posee encriptación (Texto Plano).
* **EncryptedSharedPreferences:** Cifra de manera automática las llaves y valores mediante el estándar criptográfico AES-256 SIV y AES-128 GCM antes de escribirlos de forma transparente en el disco físico. Empleado para fichas confidenciales de identidad, tokens JWT y credenciales críticas.

---

## 4. Diseño de Interfaz de Usuario (UX/UI)
La interfaz fue optimizada empleando componentes de **Material 3**:
* **Navegación por Pestañas (`TabRow`):** Segmentación estricta que separa físicamente el flujo de red del flujo de seguridad para evitar la saturación visual de la pantalla.
* **Componentes de Contención (`Card`):** Encapsulamiento visual con elevación y bordes redondeados para agrupar los formularios operativos de consulta y persistencia.
* **Flujo Transaccional:** La persistencia de secretos opera bajo la premisa de "Conocimiento previo de llave". La UI funciona de forma transaccional directa sin listar claves. El usuario introduce la llave exacta y el compartimento nativo para guardar o revelar el valor. Si la llave o el compartimento no coinciden, se despliega una notificación de inexistencia genérica con fines de seguridad.