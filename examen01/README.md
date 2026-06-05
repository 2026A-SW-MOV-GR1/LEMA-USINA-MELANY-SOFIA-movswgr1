# FIS - Aplicaciones Móviles
## Evaluación Práctica: Persistencia Dual en Dispositivos Móviles
**Institución:** Escuela Politécnica Nacional (EPN)  
**Facultad:** Facultad de Ingeniería de Sistemas (FIS)   
**Nomre:** Melany Lema

---

## 1. Introducción y Propósito del Proyecto
Este proyecto implementa una **arquitectura móvil híbrida con conmutación dinámica en tiempo de ejecución** entre dos paradigmas de almacenamiento local independiente: **Relacional (SQL)** y **No Relacional (NoSQL)**. 

El objetivo principal es cumplir con los principios avanzados de la ingeniería de software modernos, aislando por completo la capa de presentación (UI) de los acoplamientos tecnológicos directos a motores de bases de datos específicos. El sistema es capaz de conmutar su motor y reflejar los cambios de forma reactiva e instantánea en la interfaz gráfica sin requerir el reinicio de la aplicación.

---

## 2. Decisiones de Arquitectura e Ingeniería

El proyecto se rige estrictamente por los principios de **Clean Architecture** y patrones estructurales, dividiendo la solución en el contenedor común del módulo compartido (`shared` bajo el target de Kotlin Multiplatform):

### A. Patrón Repositorio (Domain Layer)
Para cumplir con las directrices de la evaluación, las vistas de la interfaz gráfica no consumen ni conocen los flujos del motor SQL o NoSQL de forma directa. Se diseñó una abstracción común (`UserRepository`) en la capa de dominio que define los contratos de operaciones CRUD:
* `getUsers(): Flow<List<User>>`
* `insertUser(user: User)`
* `updateUser(user: User)`
* `deleteUser(id: Long)`

### B. Gestión de Persistencia Dual e Independiente (Data Layer)
Para evitar la colisión de datos exigida por la rúbrica de control de estado, se implementaron dos almacenes físicos aislados en el almacenamiento interno asignado del dispositivo móvil:
* **Mapeo Relacional (SQL):** Simula una base de datos SQLite estructurada estricta utilizando un archivo plano plano (`sqlite_simulation_table.txt`) con un esquema fijo delimitado por campos rígidos (`|`).
* **Mapeo No Relacional (NoSQL):** Opera de manera semiestructurada y documental a través de un archivo dinámico independiente (`nosql_document_collection.json`) delimitado por claves dinámicas y validación estructural ágil.

### C. Conmutación Reactiva Avanzada (Presentation Layer)
Para evitar la duplicación de hilos o la pérdida de recolección de flujos reactivos, se implementó un pipeline en el `UserViewModel` comandado por el operador reactivo **`flatMapLatest`**. Al alternar el Switch interactivo en la App Bar de la `MainScreen`, el flujo principal permuta de manera transparente la suscripción al repositorio activo en tiempo real, garantizando la actualización reactiva requerida.

---

## 3. Demostración y Logs de Auditoría Estructurados
Cumpliendo con las exigencias de auditoría, cada transacción de datos genera una traza explícita en la consola especificando el nivel de severidad y el motor sobre el cual se ejecutó:

* **DEBUG:** Imprime el éxito del almacenamiento de objetos con su respectivo identificador hash o ID transaccional.
* **INFO:** Registra las consultas de lectura física que hace la interfaz gráfica hacia las colecciones persistentes.
* **ERROR:** Captura violaciones de campos obligatorios (campo *name* en SQL) o fallos de esquema de validación dinámica (campo *email* en NoSQL).

---

## 4. Estructura de Directorios del Proyecto (`shared`)

El módulo unificado se organiza de la siguiente manera:
```text
📁 shared/src
├── 📁 commonMain/kotlin/ec/edu/epn/examen01
│    ├── 📁 data
│    │    ├── 📁 local
│    │    │    ├── 📁 nosql         # Implementación física del repositorio NoSQL
│    │    │    └── 📁 sql           # Implementación física del repositorio SQLite
│    │    └── 📁 repository        # Gestor de conmutación (RepositoryManager) y StorageType
│    ├── 📁 domain
│    │    ├── 📁 model             # Entidad de dominio pura (User.kt)
│    │    └── 📁 repository        # Interfaz genérica del repositorio (Contrato)
│    └── 📁 presentation
│         ├── 📁 screen            # Interfaz de Usuario (MainScreen.kt con Switch y AssistChip)
│         └── 📁 viewmodel         # Estado y reactividad por flatMapLatest
└── 📁 commonTest/kotlin/ec/edu/epn/examen01
     └── 📄 RepositoryManagerTest.kt # Suite de Pruebas Unitarias Automatizadas
```

## 5. Pruebas Unitarias Automatizadas (Suite de Tests)
Se adjunta una suite de pruebas automatizadas locales que validan la correcta escritura aislada y el cambio de motor de datos en las capas lógicas, asegurando el cumplimiento de la rúbrica:

* **Test 1: `testCambioDeMotorYEscrituraIndependiente`** — Valida mediante coroutines de prueba (`runTest`) y aserciones de flujo (`.first()`) que los registros creados bajo el entorno SQLite no colisionan, alteran o eliminan los documentos residentes del almacén NoSQL de forma errónea.
* **Test 2: `testValidacionDeLogsYConmutacionDeTipos`** — Verifica la mutación de estado síncrona en el administrador de almacenamiento al invocar los cambios de persistencia desde la capa lógica.

### Ejecutar Pruebas Locales vía Consola:
Puedes correr los tests automatizados directamente desde la terminal del IDE ejecutando el siguiente comando de Gradle:
```bash
./gradlew :shared:testAndroidHostTest