@echo off
REM ========================================
REM SCRIPTS UTILES PARA PROYECTO KMP
REM ========================================
REM Este archivo contiene comandos útiles para Windows PowerShell
REM Copia estos comandos en PowerShell o crea scripts .bat individuales

REM ========================================
REM 1. COMPILAR EL PROYECTO
REM ========================================
REM Ejecuta: ./gradlew.bat build
REM Resultado: Compila todo el proyecto

REM ========================================
REM 2. LIMPIAR Y COMPILAR
REM ========================================
REM Ejecuta: ./gradlew.bat clean build
REM Resultado: Limpia cachés y compila de nuevo

REM ========================================
REM 3. INSTALAR EN EMULADOR (DEBUG)
REM ========================================
REM Ejecuta: ./gradlew.bat :composeApp:installDebug
REM Resultado: Instala la app en el emulador/dispositivo

REM ========================================
REM 4. EJECUTAR LA APP
REM ========================================
REM Ejecuta: adb shell am start -n com.example.kmpclient/.MainActivity
REM Resultado: Inicia la app

REM ========================================
REM 5. VER LOGS EN TIEMPO REAL
REM ========================================
REM Ejecuta: adb logcat | findstr "RESOURCES"
REM Resultado: Muestra logs de recursos en tiempo real

REM ========================================
REM 6. INSTALAR + EJECUTAR (Combinado)
REM ========================================
REM Ejecuta: ./gradlew.bat :composeApp:installDebug; adb shell am start -n com.example.kmpclient/.MainActivity
REM Resultado: Instala y ejecuta en un comando

REM ========================================
REM 7. LISTAR DISPOSITIVOS CONECTADOS
REM ========================================
REM Ejecuta: adb devices
REM Resultado: Lista emuladores y dispositivos

REM ========================================
REM 8. RESETEAR APP (Borrar datos)
REM ========================================
REM Ejecuta: adb shell pm clear com.example.kmpclient
REM Resultado: Borra datos y caché de la app

REM ========================================
REM 9. DESINSTALAR APP
REM ========================================
REM Ejecuta: adb uninstall com.example.kmpclient
REM Resultado: Desinstala la app

REM ========================================
REM 10. BUILD RELEASE
REM ========================================
REM Ejecuta: ./gradlew.bat :composeApp:assembleRelease
REM Resultado: Crea APK de release

REM ========================================
REM SCRIPTS BATCH LISTOS PARA USAR
REM ========================================

REM ARCHIVO: build.bat
REM @echo off
REM echo [*] Compilando proyecto...
REM call ./gradlew.bat build
REM if %ERRORLEVEL% EQU 0 (
REM     echo [OK] Compilacion exitosa
REM ) else (
REM     echo [ERROR] Compilacion fallida
REM )
REM pause

REM ARCHIVO: install_run.bat
REM @echo off
REM echo [*] Instalando y ejecutando...
REM call ./gradlew.bat :composeApp:installDebug
REM timeout /t 2 /nobreak
REM adb shell am start -n com.example.kmpclient/.MainActivity
REM echo [OK] App iniciada
REM pause

REM ARCHIVO: logs.bat
REM @echo off
REM echo [*] Mostrando logs en tiempo real...
REM adb logcat | findstr "RESOURCES"
REM pause

REM ARCHIVO: clean_build.bat
REM @echo off
REM echo [*] Limpiando y compilando...
REM call ./gradlew.bat clean build
REM echo [OK] Proceso completado
REM pause

REM ARCHIVO: reset_app.bat
REM @echo off
REM echo [*] Reseteando app...
REM adb shell pm clear com.example.kmpclient
REM adb shell am start -n com.example.kmpclient/.MainActivity
REM echo [OK] App reseteada
REM pause

