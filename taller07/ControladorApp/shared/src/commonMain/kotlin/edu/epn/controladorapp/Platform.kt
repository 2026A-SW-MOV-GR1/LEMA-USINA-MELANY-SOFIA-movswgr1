package edu.epn.controladorapp

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform