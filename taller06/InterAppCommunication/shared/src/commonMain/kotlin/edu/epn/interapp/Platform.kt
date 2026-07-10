package edu.epn.interapp

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform