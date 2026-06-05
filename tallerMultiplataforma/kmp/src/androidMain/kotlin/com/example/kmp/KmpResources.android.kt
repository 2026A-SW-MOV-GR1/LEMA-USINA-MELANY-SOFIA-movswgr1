package com.example.kmp

import android.content.Context

actual object KmpResources {
    private var ctx: Context? = null

    actual fun init(context: Any?) {
        ctx = context as? Context
    }

    actual fun getString(name: String): String {
        val c = ctx ?: return name
        val id = c.resources.getIdentifier(name, "string", c.packageName)
        return if (id != 0) c.getString(id) else name
    }
}

