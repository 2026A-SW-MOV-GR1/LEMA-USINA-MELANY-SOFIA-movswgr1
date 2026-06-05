package com.example.kmp

actual object KmpResources {
    actual fun init(context: Any?) {
        // no-op for desktop
    }

    actual fun getString(name: String): String = name
}

