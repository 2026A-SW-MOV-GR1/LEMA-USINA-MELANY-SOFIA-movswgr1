package ec.edu.epn.examen01.data.repository

import ec.edu.epn.examen01.domain.repository.UserRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class RepositoryManager {

    private val sqlRepository =
        ec.edu.epn.examen01.data.local.sql.SqlUserRepository()

    private val noSqlRepository =
        ec.edu.epn.examen01.data.local.nosql.NoSqlUserRepository()

    private val _storage =
        MutableStateFlow(StorageType.SQL)

    val storage: StateFlow<StorageType>
        get() = _storage

    fun switchStorage(type: StorageType) {

        println("DEBUG: Cambiando a $type")

        _storage.value = type
    }

    fun currentRepository(): UserRepository {

        return when (_storage.value) {

            StorageType.SQL ->
                sqlRepository

            StorageType.NOSQL ->
                noSqlRepository
        }
    }
}