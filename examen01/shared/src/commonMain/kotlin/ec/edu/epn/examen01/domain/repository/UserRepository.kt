package ec.edu.epn.examen01.domain.repository

import ec.edu.epn.examen01.domain.model.User
import kotlinx.coroutines.flow.Flow

interface UserRepository {

    fun getUsers(): Flow<List<User>>

    suspend fun insertUser(user: User)

    suspend fun updateUser(user: User)

    suspend fun deleteUser(id: Long)
}