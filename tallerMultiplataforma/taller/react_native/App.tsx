/**
 * Opción 2 — React Native (Native Bridge)
 * App.tsx — Lado JavaScript
 *
 * Consulta el módulo nativo cada vez que cambia la orientación.
 * Sin react-i18next. Sin hardcoding de idioma.
 */
import React, { useEffect, useState, useCallback } from 'react';
import {
  View,
  Text,
  NativeModules,
  Dimensions,
  StyleSheet,
  StatusBar,
} from 'react-native';

interface AndroidResources {
  mensaje: string;
  colorTexto: string;
  colorFondo: string;
  isLandscape: boolean;
}

const { ResourceModule } = NativeModules;

export default function App() {
  const [resources, setResources] = useState<AndroidResources>({
    mensaje: '...',
    colorTexto: '#000000',
    colorFondo: '#FFFFFF',
    isLandscape: false,
  });

  /**
   * Solicita los recursos al módulo nativo.
   * El módulo nativo consulta el Context de Android, que ya tiene
   * el Configuration actualizado (idioma + orientación).
   */
  const loadResources = useCallback(async () => {
    try {
      const res: AndroidResources = await ResourceModule.getResources();
      setResources(res);
    } catch (e) {
      console.error('Error al obtener recursos nativos:', e);
    }
  }, []);

  useEffect(() => {
    // Carga inicial
    loadResources();

    // Re-consulta al nativo cuando cambian las dimensiones (rotación)
    const subscription = Dimensions.addEventListener('change', loadResources);
    return () => subscription?.remove();
  }, [loadResources]);

  return (
    <View
      style={[styles.container, { backgroundColor: resources.colorFondo }]}>
      <StatusBar translucent backgroundColor="transparent" />
      <Text
        style={[
          styles.message,
          { color: resources.colorTexto },
        ]}>
        {resources.mensaje}
      </Text>
    </View>
  );
}

const styles = StyleSheet.create({
  container: {
    flex: 1,
    alignItems: 'center',
    justifyContent: 'center',
  },
  message: {
    fontSize: 32,
    fontWeight: 'bold',
    textAlign: 'center',
    paddingHorizontal: 24,
  },
});
