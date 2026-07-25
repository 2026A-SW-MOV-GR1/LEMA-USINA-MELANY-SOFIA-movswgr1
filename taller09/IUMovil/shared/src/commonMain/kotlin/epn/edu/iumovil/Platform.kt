package epn.edu.iumovil

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform