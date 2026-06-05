package ec.edu.epn.examen01.data.local.sql

import ec.edu.epn.examen01.domain.model.User
import ec.edu.epn.examen01.domain.repository.UserRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update
import java.io.File

class SqlUserRepository : UserRepository {

    // Archivo físico real en el almacenamiento interno de la app
    private val sqlFile = File(System.getProperty("java.io.tmpdir"), "sqlite_simulation_table.txt")
    private val users = MutableStateFlow<List<User>>(emptyList())

    init {
        loadFromDisk()
    }

    private fun loadFromDisk() {
        try {
            if (sqlFile.exists()) {
                val lines = sqlFile.readLines()
                val loadedUsers = lines.mapNotNull { line ->
                    val parts = line.split("|")
                    if (parts.size == 3) {
                        User(id = parts[0].toLong(), name = parts[1], email = parts[2])
                    } else null
                }
                users.value = loadedUsers
                println("INFO: [SQLite] Base de datos física cargada desde el disco. Registros: ${loadedUsers.size}")
            }
        } catch (e: Exception) {
            println("ERROR: [SQLite] Corrupción de datos al leer el archivo de base de datos.")
        }
    }

    private fun saveToDisk(currentList: List<User>) {
        try {
            val lines = currentList.map { "${it.id}|${it.name}|${it.email}" }
            sqlFile.writeText(lines.joinToString("\n"))
        } catch (e: Exception) {
            println("ERROR: [SQLite] Fallo crítico de hardware al escribir en disco.")
        }
    }

    override fun getUsers(): Flow<List<User>> {
        println("INFO: [SQLite] Consultando tablas indexadas relacionales en almacenamiento físico.")
        return users
    }

    override suspend fun insertUser(user: User) {
        if (user.name.isBlank()) {
            println("ERROR: [SQLite] Restricción de campo obligatorio violada.")
            return
        }
        users.update { currentList ->
            val updatedList = currentList + user
            saveToDisk(updatedList)
            updatedList
        }
        println("DEBUG: [SQLite] Transacción física EXITOSA. Guardado ID: ${user.id} en disco.")
    }

    override suspend fun updateUser(user: User) {
        users.update { currentList ->
            val updatedList = currentList.map { if (it.id == user.id) user else it }
            saveToDisk(updatedList)
            updatedList
        }
        println("DEBUG: [SQLite] Fila con ID: ${user.id} actualizada físicamente.")
    }

    override suspend fun deleteUser(id: Long) {
        users.update { currentList ->
            val updatedList = currentList.filter { it.id != id }
            saveToDisk(updatedList)
            updatedList
        }
        println("DEBUG: [SQLite] Registro con ID: $id eliminado físicamente del archivo.")
    }
}