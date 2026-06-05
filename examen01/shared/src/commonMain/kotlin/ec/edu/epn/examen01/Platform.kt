package ec.edu.epn.examen01

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform