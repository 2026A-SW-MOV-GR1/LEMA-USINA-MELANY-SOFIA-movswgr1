# MATRIZ VISUAL DE CONFIGURACIONES

## 🎨 Vista General de las 4 Configuraciones

```
┌──────────────────────────────────────────────────────────────────────┐
│                    MATRIZ DE CONFIGURACIONES KMP                     │
└──────────────────────────────────────────────────────────────────────┘

                    PORTRAIT                 LANDSCAPE
              (Vertical/Default)         (Horizontal/Rotated)
       ┌─────────────────────┬─────────────────────┐
       │                     │                     │
ESPAÑOL│  CONFIG 1 ┌─────┐   │  CONFIG 3 ┌─────┐   │
       │          │ A   │   │          │ C   │   │
       │          │BN:  │   │          │GN:  │   │
       │          │BLK  │   │          │DGN  │   │
       │          └─────┘   │          └─────┘   │
       │         Blanco     │         Amarillo    │
       │                     │                     │
       ├─────────────────────┼─────────────────────┤
       │                     │                     │
ENGLISH│  CONFIG 2 ┌─────┐   │  CONFIG 4 ┌─────┐   │
       │          │ B   │   │          │ D   │   │
       │          │WHT: │   │          │PUR: │   │
       │          │BLUE │   │          │PINK │   │
       │          └─────┘   │          └─────┘   │
       │          Azul      │           Rosa     │
       │                     │                     │
       └─────────────────────┴─────────────────────┘

Legend: A/B/C/D = Texto | TXT: = Color de texto | Fondo = Color de fondo
```

---

## 📊 TABLA COMPARATIVA

```
┌───────────┬──────────────────────┬──────────────┬──────────────┬───────────────┐
│ CONFIG    │ Texto                │ Color Texto  │ Color Fondo  │ Directorio    │
├───────────┼──────────────────────┼──────────────┼──────────────┼───────────────┤
│           │                      │              │              │               │
│ CONFIG 1  │ Texto A -            │ Negro        │ Blanco       │ values/       │
│ Portrait  │ Portrait Español     │ #000000      │ #FFFFFF      │ strings.xml   │
│ Español   │                      │              │              │               │
│           │ 🟫 Fondo blanco     │              │              │               │
│           │ 🟩 Texto negro      │              │              │               │
│           │                      │              │              │               │
├───────────┼──────────────────────┼──────────────┼──────────────┼───────────────┤
│           │                      │              │              │               │
│ CONFIG 2  │ Text B -             │ Blanco       │ Azul         │ values-en/    │
│ Portrait  │ Portrait English     │ #FFFFFF      │ #2196F3      │ strings.xml   │
│ Inglés    │                      │              │              │               │
│           │ 🟦 Fondo azul       │              │              │               │
│           │ ⚪ Texto blanco     │              │              │               │
│           │                      │              │              │               │
├───────────┼──────────────────────┼──────────────┼──────────────┼───────────────┤
│           │                      │              │              │               │
│ CONFIG 3  │ Texto C -            │ Verde oscuro │ Amarillo     │ values-land/  │
│ Landscape │ Landscape Español    │ #1B5E20      │ #FFC107      │ strings.xml   │
│ Español   │                      │              │              │               │
│           │ 🟨 Fondo amarillo   │              │              │               │
│           │ 🟩 Texto verde      │              │              │               │
│           │                      │              │              │               │
├───────────┼──────────────────────┼──────────────┼──────────────┼───────────────┤
│           │                      │              │              │               │
│ CONFIG 4  │ Text D -             │ Púrpura      │ Rosa         │ values-en-    │
│ Landscape │ Landscape English    │ #6A1B9A      │ #E91E63      │ land/         │
│ Inglés    │                      │              │              │ strings.xml   │
│           │ 🟩 Fondo rosa       │              │              │               │
│           │ 🟪 Texto púrpura    │              │              │               │
│           │                      │              │              │               │
└───────────┴──────────────────────┴──────────────┴──────────────┴───────────────┘
```

---

## 🎨 VISTA DE COLORES

### CONFIG 1: Portrait - Español
```
┌──────────────────────────────┐
│  Fondo: #FFFFFF (Blanco)     │
│  ┌──────────────────────────┐│
│  │ Texto A - Portrait       ││
│  │ Español                  ││
│  │ Color: #000000 (Negro)   ││
│  └──────────────────────────┘│
└──────────────────────────────┘
```

### CONFIG 2: Portrait - Inglés
```
┌──────────────────────────────┐
│  Fondo: #2196F3 (Azul)       │
│  ┌──────────────────────────┐│
│  │ Text B - Portrait        ││
│  │ English                  ││
│  │ Color: #FFFFFF (Blanco)  ││
│  └──────────────────────────┘│
└──────────────────────────────┘
```

### CONFIG 3: Landscape - Español
```
┌──────────────────────────────┐
│  Fondo: #FFC107 (Amarillo)   │
│  ┌──────────────────────────┐│
│  │ Texto C - Landscape      ││
│  │ Español                  ││
│  │ Color: #1B5E20 (Verde)   ││
│  └──────────────────────────┘│
└──────────────────────────────┘
```

### CONFIG 4: Landscape - Inglés
```
┌──────────────────────────────┐
│  Fondo: #E91E63 (Rosa)       │
│  ┌──────────────────────────┐│
│  │ Text D - Landscape       ││
│  │ English                  ││
│  │ Color: #6A1B9A (Púrpura) ││
│  └──────────────────────────┘│
└──────────────────────────────┘
```

---

## 🔄 FLUJO DE CAMBIO ENTRE CONFIGURACIONES

```
USER ACTION                    RESULT
============                   ======

START APP
  ↓
[Config 1 Visible]
Texto A, Negro, Blanco
  ↓
Settings → English
  ↓
[Config 2 Visible]
Text B, Blanco, Azul
  ↓
Rotate Screen
  ↓
[Config 4 Visible]
Text D, Púrpura, Rosa
  ↓
Settings → Español
  ↓
[Config 3 Visible]
Texto C, Verde, Amarillo
  ↓
Rotate Back
  ↓
[Config 1 Visible]
Texto A, Negro, Blanco
```

---

## 📋 CHECKLIST DE VERIFICACIÓN VISUAL

```
┌─ CONFIG 1: Portrait - Español (DEFAULT) ──────────────────┐
│                                                             │
│  [ ] Abre la app                                            │
│  [ ] Fondo es BLANCO                                        │
│  [ ] Texto es "Texto A - Portrait Español"                 │
│  [ ] Color del texto es NEGRO                              │
│  [ ] Orientación es VERTICAL                               │
│                                                             │
└─────────────────────────────────────────────────────────────┘

┌─ CONFIG 2: Portrait - Inglés ──────────────────────────────┐
│                                                             │
│  [ ] Cambiar a Inglés en Settings                          │
│  [ ] Texto cambió a "Text B - Portrait English"            │
│  [ ] Color de texto es BLANCO                              │
│  [ ] Fondo cambió a AZUL                                   │
│  [ ] Orientación sigue VERTICAL                            │
│                                                             │
└─────────────────────────────────────────────────────────────┘

┌─ CONFIG 3: Landscape - Español ────────────────────────────┐
│                                                             │
│  [ ] Cambiar a Español en Settings                         │
│  [ ] Rotar pantalla (Ctrl+F11)                             │
│  [ ] Texto cambió a "Texto C - Landscape Español"          │
│  [ ] Color de texto es VERDE oscuro                        │
│  [ ] Fondo cambió a AMARILLO                               │
│  [ ] Orientación es HORIZONTAL                             │
│                                                             │
└─────────────────────────────────────────────────────────────┘

┌─ CONFIG 4: Landscape - Inglés ─────────────────────────────┐
│                                                             │
│  [ ] Cambiar a Inglés en Settings                          │
│  [ ] Mantener rotación Landscape                           │
│  [ ] Texto cambió a "Text D - Landscape English"           │
│  [ ] Color de texto es PÚRPURA                             │
│  [ ] Fondo cambió a ROSA                                   │
│  [ ] Orientación sigue HORIZONTAL                          │
│                                                             │
└─────────────────────────────────────────────────────────────┘
```

---

## 🎯 MATRIZ DE RECURSOS ANDROID

```
Android Resolution Engine:
┌─────────────────────────────────────────────────────────┐
│                                                         │
│  getIdentifier("text_content", "string", packageName)   │
│  getIdentifier("text_color", "color", packageName)      │
│  getIdentifier("background_color", "color", packageName)│
│                           ↓                             │
│  ┌─────────────────────────────────────────────────┐    │
│  │ ¿Qué idioma? ¿Qué orientación?                  │    │
│  └──────────────┬──────────────────────────────────┘    │
│                 │                                       │
│  ┌──────────────┴──────────────────────────────────┐    │
│  │  1. values-en-land/  (MAX SPECIFIC)             │    │
│  │  2. values-en/                                  │    │
│  │  3. values-land/                                │    │
│  │  4. values/  (DEFAULT)                          │    │
│  └──────────────────────────────────────────────────┘    │
│                           ↓                             │
│  Retorna el recurso encontrado ✅                       │
│                                                         │
└─────────────────────────────────────────────────────────┘
```

---

## 📐 DIMENSIONES Y VALORES

| Propiedad | Valor |
|-----------|-------|
| Text Size | 24sp |
| Layout Width | match_parent |
| Layout Height | match_parent |
| Content Alignment | Center |
| Text Alignment | Center |

---

## 🔮 EXPANSIÓN FUTURA

```
Hoy: Android con 4 configuraciones
  ↓
Mañana: Agregar iOS con expect/actual
  ↓
Después: Agregar más idiomas (FR, PT, IT)
  ↓
Futuro: Agregar más orientaciones (Reverse)
  ↓
Ultra: Agregar temas (Light/Dark)
```

---

## 📱 VISTA EN DISPOSITIVO

### Portrait - Español
```
╔══════════════════════╗
║                      ║
║                      ║
║    Texto A -         ║
║    Portrait          ║
║    Español           ║
║                      ║
║                      ║
╚══════════════════════╝
(Blanco)  Texto: Negro
```

### Portrait - Inglés
```
╔══════════════════════╗
║                      ║
║                      ║
║    Text B -          ║
║    Portrait          ║
║    English           ║
║                      ║
║                      ║
╚══════════════════════╝
(Azul)  Texto: Blanco
```

### Landscape - Español
```
╔════════════════════════════════╗
║                                ║
║     Texto C - Landscape        ║
║     Español                    ║
║                                ║
╚════════════════════════════════╝
(Amarillo)  Texto: Verde
```

### Landscape - Inglés
```
╔════════════════════════════════╗
║                                ║
║     Text D - Landscape         ║
║     English                    ║
║                                ║
╚════════════════════════════════╝
(Rosa)  Texto: Púrpura
```

---

**Matriz Visual Completa - Proyecto KMP Punto 4** 🎨

