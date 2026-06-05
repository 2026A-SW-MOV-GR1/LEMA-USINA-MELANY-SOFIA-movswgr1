package ec.edu.epn.proyecto01

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform