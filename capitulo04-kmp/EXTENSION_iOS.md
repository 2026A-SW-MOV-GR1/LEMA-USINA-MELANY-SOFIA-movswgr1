# Extensión Multiplatforma a iOS

## Implementación esperada para iOS

### 1. Estructura de directorios iOS

```
composeApp/
└── src/
    └── iosMain/
        ├── kotlin/
        │   └── com/example/kmpclient/
        │       └── ResourcesManager.kt (actual para iOS)
        └── resources/
            ├── en.lproj/
            │   └── Localizable.strings
            ├── es.lproj/
            │   └── Localizable.strings
            └── Colors.swift
```

### 2. Implementación esperada - ResourcesManager.kt (iosMain)

```kotlin
package com.example.kmpclient

import platform.Foundation.*
import platform.UIKit.UIColor

actual object ResourcesManager {
    private var currentText = ""
    private var currentTextColor = 0L
    private var currentBackgroundColor = 0L
    
    fun init() {
        updateResources()
    }
    
    fun updateResources() {
        val currentLocale = NSLocale.currentLocale
        val orientation = UIApplication.sharedApplication.statusBarOrientation
        
        // Determinar qué strings cargar
        val textKey = "text_content"
        currentText = NSLocalizedString(textKey, textKey)
        
        // Cargar colores según orientación
        if (orientation == UIInterfaceOrientationPortrait) {
            if (currentLocale.localeIdentifier.contains("en")) {
                currentTextColor = UIColor.white.hexValue()
                currentBackgroundColor = UIColor(red = 0.129f, green = 0.588f, blue = 0.953f, alpha = 1.0f).hexValue()
            } else {
                currentTextColor = UIColor.black.hexValue()
                currentBackgroundColor = UIColor.white.hexValue()
            }
        } else {
            if (currentLocale.localeIdentifier.contains("en")) {
                currentTextColor = UIColor(red = 0.416f, green = 0.106f, blue = 0.604f, alpha = 1.0f).hexValue()
                currentBackgroundColor = UIColor(red = 0.929f, green = 0.118f, blue = 0.388f, alpha = 1.0f).hexValue()
            } else {
                currentTextColor = UIColor(red = 0.106f, green = 0.369f, blue = 0.125f, alpha = 1.0f).hexValue()
                currentBackgroundColor = UIColor(red = 1.0f, green = 0.761f, blue = 0.067f, alpha = 1.0f).hexValue()
            }
        }
    }
    
    actual fun getText(): String = currentText
    actual fun getTextColor(): Long = currentTextColor
    actual fun getBackgroundColor(): Long = currentBackgroundColor
}
```

### 3. Localizable.strings (en.lproj)

```
"text_content" = "Text B - Portrait English";
```

### 4. Localizable.strings (es.lproj)

```
"text_content" = "Texto A - Portrait Español";
```

### 5. Colors.swift

```swift
import UIKit

struct AppColors {
    // Portrait - Spanish
    static let textColorPortraitES = UIColor.black
    static let backgroundColorPortraitES = UIColor.white
    
    // Portrait - English
    static let textColorPortraitEN = UIColor.white
    static let backgroundColorPortraitEN = UIColor(red: 0.129, green: 0.588, blue: 0.953, alpha: 1.0)
    
    // Landscape - Spanish
    static let textColorLandscapeES = UIColor(red: 0.106, green: 0.369, blue: 0.125, alpha: 1.0)
    static let backgroundColorLandscapeES = UIColor(red: 1.0, green: 0.761, blue: 0.067, alpha: 1.0)
    
    // Landscape - English
    static let textColorLandscapeEN = UIColor(red: 0.416, green: 0.106, blue: 0.604, alpha: 1.0)
    static let backgroundColorLandscapeEN = UIColor(red: 0.929, green: 0.118, blue: 0.388, alpha: 1.0)
}
```

### 6. ViewController (iOS)

```swift
import UIKit
import ComposeApp

class ViewController: UIViewController {
    
    override func viewDidLoad() {
        super.viewDidLoad()
        
        ResourcesManager().init()
        
        NotificationCenter.default.addObserver(
            self,
            selector: #selector(orientationDidChange),
            name: UIDevice.orientationDidChangeNotification,
            object: nil
        )
    }
    
    @objc func orientationDidChange() {
        ResourcesManager().updateResources()
        // Actualizar UI
    }
}
```

## Consideraciones Importantes para iOS

1. **Localización nativa**: Usar NSLocalizedString en lugar de archivos .arb
2. **Detección de orientación**: Observar UIDevice.orientationDidChangeNotification
3. **Sincronización con Compose**: Los cambios de estado en Kotlin se reflejarán en la UI de Compose

## Ventajas de esta arquitectura multiplatforma

✅ Código compartido en `commonMain`
✅ Implementaciones específicas en `androidMain` e `iosMain`
✅ Sin dependencias externas
✅ Respeta mecanismos nativos de cada plataforma
✅ Performance óptimo

## Próximos pasos para implementación iOS

1. Crear estructura de directorios ios
2. Agregar build.gradle.kts con target iOS
3. Implementar ResourcesManager para iOS
4. Crear Localizable.strings
5. Implementar ViewController
6. Sincronizar con Swift UI si es necesario

