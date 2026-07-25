package epn.edu.apprefugios

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform