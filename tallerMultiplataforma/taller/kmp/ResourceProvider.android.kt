import android.content.Context
import androidx.core.content.ContextCompat

// Implementación Android que resuelve recursos por nombre (delegando a Android)
class AndroidResourceProvider(private val ctx: Context) : ResourceProvider {
    override fun getString(name: String): String {
        val id = ctx.resources.getIdentifier(name, "string", ctx.packageName)
        return if (id != 0) ctx.getString(id) else ""
    }

    override fun getColorInt(name: String): Int {
        val id = ctx.resources.getIdentifier(name, "color", ctx.packageName)
        return if (id != 0) ContextCompat.getColor(ctx, id) else 0
    }
}

