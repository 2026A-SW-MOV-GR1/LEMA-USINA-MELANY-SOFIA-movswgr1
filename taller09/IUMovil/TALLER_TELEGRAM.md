# Taller: Clon de Telegram (KMP + Compose)

## Fase A: Análisis (Entregable 1)

### 1. Definición de Mercado
*   **Público Objetivo:** Usuarios jóvenes y adultos (15-55 años) que priorizan la seguridad, la velocidad y la funcionalidad multidispositivo.
*   **Intereses:** Tecnología, privacidad, comunidades (canales/grupos), y gestión de archivos pesados.
*   **Nivel Socioeconómico:** Amplio (Medio-Bajo a Alto), ya que es una herramienta de comunicación esencial y gratuita.

### 2. Psicología del Color
*   **Paleta Principal:**
    *   **Azul Telegram (#24A1DE):** Color primario. Representa confianza, profesionalismo y serenidad. Es un color que no fatiga la vista y se asocia con la tecnología.
    *   **Gris (#8E8E93):** Usado para textos secundarios y estados de mensajes. Aporta equilibrio y neutralidad.
    *   **Blanco/Oscuro (Backgrounds):** Pureza y legibilidad en modo claro; reducción de fatiga visual en modo oscuro.
*   **Justificación:** El azul fomenta un ambiente de comunicación segura y eficiente, alejándose del verde (WhatsApp) para diferenciarse como una alternativa más robusta y tecnológica.

### 3. Auditoría de Componentes (Listas)
Se clonarán los siguientes iterables:
1.  **Lista de Chats (Home):** Columna vertical con fotos de perfil, nombres, último mensaje y hora.
2.  **Lista de Contactos (Búsqueda):** Lista simplificada para encontrar personas.
3.  **Lista de Canales/Sugeridos:** Lista para explorar contenido.

---

## Fase B: Desarrollo Técnico (Entregable 2)

### Estructura de Datos
Se crearon modelos para `Chat`, `Contact` y `Message`.

### Implementación
*   Uso de `LazyColumn` para garantizar fluidez (60 FPS).
*   Estilización con `MaterialTheme` adaptado a la paleta de Telegram.

---

## Fase C: Análisis Crítico y Mejora

### 1. Falla Identificada
**Problema:** En la versión original, la gestión de "Archivados" a veces se oculta demasiado (swipe down), lo que puede hacer que el usuario olvide conversaciones importantes. Además, la retroalimentación visual al marcar un mensaje como leído es sutil.

### 2. Propuesta de Mejora
**Solución:** Implementar un indicador visual más vibrante para mensajes no leídos en chats importantes y una micro-animación de "pulso" en el icono de notificaciones dentro de la lista para captar la atención del usuario de forma no intrusiva.

---

## Requerimientos Técnicos Cumplidos
*   **Nativo:** Renderizado 100% en Compose Multiplatform (sin WebViews).
*   **Performance:** Uso eficiente de llaves en `LazyColumn`.
*   **SOLID:** Separación de UI, modelos y lógica de datos.
