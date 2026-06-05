// Opción 5 — NativeScript
// main-page.ts — code-behind

import { NavigatedData, Page } from '@nativescript/core';
import { MainViewModel } from './main-view-model';

export function navigatingTo(args: NavigatedData) {
    const page = args.object as Page;
    // El ViewModel ya se suscribe a orientationChanged en su constructor
    page.bindingContext = new MainViewModel();
}
