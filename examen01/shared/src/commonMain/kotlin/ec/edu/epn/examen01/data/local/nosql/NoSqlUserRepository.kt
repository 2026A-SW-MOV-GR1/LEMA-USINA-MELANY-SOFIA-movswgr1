package ec.edu.epn.examen01.data.local.nosql

import ec.edu.epn.examen01.domain.model.User
import ec.edu.epn.examen01.domain.repository.UserRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update
import java.io.File

class NoSqlUserRepository : UserRepository {

    // Segundo archivo físico, completamente independiente del de SQL
    private val nosqlFile = File(System.getProperty("java.io.tmpdir"), "nosql_document_collection.json")
    private val users = MutableStateFlow<List<User>>(emptyList())

    init {
        loadFromDisk()
    }

    private fun loadFromDisk() {
        try {
            if (nosqlFile.exists()) {
                val lines = nosqlFile.readLines()
                val loadedUsers = lines.mapNotNull { line ->
                    val parts = line.split(";")
                    if (parts.size == 3) {
                        User(id = parts[0].toLong(), name = parts[1], email = parts[2])
                    } else null
                }
                users.value = loadedUsers
                println("INFO: [NoSQL] Documentos cargados de forma ágil desde el almacenamiento persistente. Cantidad: ${loadedUsers.size}")
            }
        } catch (e: Exception) {
            println("ERROR: [NoSQL] Error de parseo en la colección documental persistente.")
        }
    }

    private fun saveToDisk(currentList: List<User>) {
        try {
            val lines = currentList.map { "${it.id};${it.name};${it.email}" }
            nosqlFile.writeText(lines.joinToString("\n"))
        } catch (e: Exception) {
            println("ERROR: [NoSQL] Error al persistir el árbol documental en disco.")
        }
    }

    override fun getUsers(): Flow<List<User>> {
        println("INFO: [NoSQL] Leyendo colección dinámica semiestructurada desde almacenamiento físico.")
        return users
    }

    override suspend fun insertUser(user: User) {
        if (!user.email.contains("@")) {
            println("ERROR: [NoSQL] Validación de esquema dinámico fallida (Email inválido).")
            return
        }
        users.update { currentList ->
            val updatedList = currentList + user
            saveToDisk(updatedList)
            updatedList
        }
        println("DEBUG: [NoSQL] Documento guardado de forma ágil en colección física. ID: ${user.id}")
    }

    override suspend fun updateUser(user: User) {
        users.update { currentList ->
            val updatedList = currentList.map { if (it.id == user.id) user else it }
            saveToDisk(updatedList)
            updatedList
        }
        println("DEBUG: [NoSQL] Documento con ID: ${user.id} reemplazado de manera persistente.")
    }

    override suspend fun deleteUser(id: Long) {
        users.update { currentList ->
            val updatedList = currentList.filter { it.id != id }
            saveToDisk(updatedList)
            updatedList
        }
        println("DEBUG: [NoSQL] Objeto eliminado del árbol persistente local por hash ID: $id")
    }
}