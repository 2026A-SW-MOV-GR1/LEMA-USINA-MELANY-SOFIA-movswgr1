// Opción 3 — Flutter (Platform Channels)
// main.dart
//
// Consulta el MethodChannel nativo para obtener texto y colores.
// OrientationBuilder detecta la rotación y solicita nuevos valores.
// Sin Intl ni archivos .arb.

import 'package:flutter/material.dart';
import 'package:flutter/services.dart';

void main() => runApp(const MyApp());

class MyApp extends StatelessWidget {
  const MyApp({super.key});

  @override
  Widget build(BuildContext context) {
    return const MaterialApp(
      debugShowCheckedModeBanner: false,
      home: ResourceScreen(),
    );
  }
}

class ResourceScreen extends StatefulWidget {
  const ResourceScreen({super.key});

  @override
  State<ResourceScreen> createState() => _ResourceScreenState();
}

class _ResourceScreenState extends State<ResourceScreen>
    with WidgetsBindingObserver {

  // Canal que conecta con el MainActivity.kt nativo
  static const _channel =
      MethodChannel('com.example.moviles2026aswgr1/resources');

  Map<String, String> _resources = {
    'mensaje': '...',
    'colorTexto': '#000000',
    'colorFondo': '#FFFFFF',
  };

  @override
  void initState() {
    super.initState();
    WidgetsBinding.instance.addObserver(this);
    _loadResources();
  }

  @override
  void dispose() {
    WidgetsBinding.instance.removeObserver(this);
    super.dispose();
  }

  /// didChangeMetrics se llama cuando rota la pantalla.
  /// Consultamos al nativo para obtener los valores actualizados.
  @override
  void didChangeMetrics() => _loadResources();

  /// Invoca getResources en el MethodChannel.
  /// El lado nativo consulta el Context con el Configuration actualizado.
  Future<void> _loadResources() async {
    try {
      final res = await _channel
          .invokeMapMethod<String, String>('getResources');
      if (res != null) setState(() => _resources = res);
    } on PlatformException catch (e) {
      debugPrint('Error al obtener recursos: ${e.message}');
    }
  }

  /// Convierte "#RRGGBB" a Color de Flutter
  Color _hexToColor(String hex) {
    return Color(int.parse(hex.replaceAll('#', 'FF'), radix: 16));
  }

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      body: Container(
        color: _hexToColor(_resources['colorFondo']!),
        width: double.infinity,
        height: double.infinity,
        alignment: Alignment.center,
        child: Text(
          _resources['mensaje']!,
          style: TextStyle(
            color: _hexToColor(_resources['colorTexto']!),
            fontSize: 32,
            fontWeight: FontWeight.bold,
          ),
          textAlign: TextAlign.center,
        ),
      ),
    );
  }
}
