// Opción 5 — NativeScript
// main-view-model.ts
//
// Accede directamente a android.content.res.Resources desde TypeScript.
// Sin el sistema i18n propio de NativeScript.
// Sin hardcoding: los valores provienen de values-en, values-land, etc.

import { Observable, Application, Utils, Color } from '@nativescript/core';

export interface AndroidResources {
  mensaje: string;
  colorTexto: string;
  colorFondo: string;
}

/**
 * Lee los recursos de Android con el Context actualizado.
 * Android resuelve el calificador correcto (idioma + orientación)
 * en tiempo de ejecución cuando se llama a getString() y getColor().
 *
 * La jerarquía de calificadores es:
 *   values-en-land > values-en > values-land > values
 */
export function getAndroidResources(): AndroidResources {
  const ctx  = Utils.android.getApplicationContext();
  const res  = ctx.getResources();
  const pkg  = ctx.getPackageName();

  // Obtenemos los IDs de recurso por nombre (mismo que en R.string / R.color)
  const msgId    = res.getIdentifier('mensaje',     'string', pkg);
  const textoId  = res.getIdentifier('color_texto', 'color',  pkg);
  const fondoId  = res.getIdentifier('color_fondo', 'color',  pkg);

  return {
    mensaje:    res.getString(msgId),
    colorTexto: androidColorToHex(res.getColor(textoId)),
    colorFondo: androidColorToHex(res.getColor(fondoId)),
  };
}

/** Convierte un color int de Android (AARRGGBB) a string "#RRGGBB" */
function androidColorToHex(colorInt: number): string {
  const hex = (colorInt & 0xFFFFFF).toString(16).padStart(6, '0');
  return `#${hex}`;
}

// ── ViewModel ───────────────────────────────────────────────────────────

export class MainViewModel extends Observable {
  private _mensaje:    string = '...';
  private _colorTexto: string = '#000000';
  private _colorFondo: string = '#FFFFFF';

  constructor() {
    super();
    this.cargarRecursos();

    // Recarga cuando el sistema notifica cambio de orientación
    Application.on('orientationChanged', () => this.cargarRecursos());
  }

  cargarRecursos(): void {
    const r = getAndroidResources();
    this.set('mensaje',    r.mensaje);
    this.set('colorTexto', r.colorTexto);
    this.set('colorFondo', r.colorFondo);
  }

  get mensaje():    string { return this._mensaje; }
  get colorTexto(): string { return this._colorTexto; }
  get colorFondo(): string { return this._colorFondo; }
}
