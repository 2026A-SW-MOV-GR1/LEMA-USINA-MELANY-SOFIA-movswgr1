package ec.edu.epn.proyecto01

// Añadimos () justo después del nombre de la clase
expect class StoragePlatform() {
    suspend fun savePreference(key: String, value: String)
    suspend fun getPreference(key: String): String?

    suspend fun saveDataStore(key: String, value: String)
    suspend fun getDataStore(key: String): String?

    suspend fun saveEncrypted(key: String, value: String)
    suspend fun getEncrypted(key: String): String?
}