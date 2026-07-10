package edu.epn.interapp

interface PlatformActions {
    fun openDialer(phoneNumber: String)
    fun takePhoto()
}

expect fun getPlatformActions(): PlatformActions
