package ec.edu.epn.examen01.presentation.viewmodel

import ec.edu.epn.examen01.data.repository.RepositoryManager
import ec.edu.epn.examen01.data.repository.StorageType
import ec.edu.epn.examen01.domain.model.User
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

@OptIn(ExperimentalCoroutinesApi::class)
class UserViewModel {

    private val repositoryManager = RepositoryManager()

    private val scope = CoroutineScope(Dispatchers.Default)

    // Exponer el almacenamiento actual
    val storage = repositoryManager.storage

    // flatMapLatest asegura que al cambiar el storage, automáticamente se escuche al nuevo repositorio
    val users: StateFlow<List<User>> = repositoryManager.storage
        .flatMapLatest { type ->
            repositoryManager.currentRepository().getUsers()
        }
        .stateIn(
            scope = scope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = emptyList()
        )

    fun switchStorage(type: StorageType) {
        repositoryManager.switchStorage(type)
    }

    fun addUser(name: String, email: String) {
        scope.launch {
            repositoryManager.currentRepository().insertUser(
                User(
                    id = System.currentTimeMillis(),
                    name = name,
                    email = email
                )
            )
        }
    }

    fun updateUser(id: Long, name: String, email: String) {
        scope.launch {
            repositoryManager.currentRepository().updateUser(
                User(id = id, name = name, email = email)
            )
        }
    }

    fun deleteUser(id: Long) {
        scope.launch {
            repositoryManager.currentRepository().deleteUser(id)
        }
    }
}