package ec.edu.epn.examen01

import ec.edu.epn.examen01.data.repository.RepositoryManager
import ec.edu.epn.examen01.data.repository.StorageType
import ec.edu.epn.examen01.domain.model.User
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runTest
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertNotEquals

class RepositoryManagerTest {

    @Test
    fun testCambioDeMotorYEscrituraIndependiente() = runTest {
        val manager = RepositoryManager()

        // Registrar un usuario en el motor SQL por defecto
        val userSql = User(1L, "Melany Lema", "melany@epn.edu.ec")
        manager.currentRepository().insertUser(userSql)

        // Verificar que se guardó en SQL
        val usuariosSql = manager.currentRepository().getUsers().first()
        assertEquals(1, usuariosSql.size, "El repositorio SQL debería tener exactamente 1 registro.")

        // Conmutar el motor a NoSQL
        manager.switchStorage(StorageType.NOSQL)

        // Verificar que el repositorio NoSQL arranca vacío e independiente
        val usuariosNoSqlInicial = manager.currentRepository().getUsers().first()
        assertEquals(0, usuariosNoSqlInicial.size, "El repositorio NoSQL debe estar vacío de forma independiente.")

        // Insertar en NoSQL
        val userNoSql = User(2L, "Kevin Gomez", "kevin@epn.edu.ec")
        manager.currentRepository().insertUser(userNoSql)

        // Confirmar aislamiento total
        val usuariosNoSqlFinal = manager.currentRepository().getUsers().first()
        assertEquals(1, usuariosNoSqlFinal.size)
        assertNotEquals(usuariosSql.first().id, usuariosNoSqlFinal.first().id, "Los datos de SQL y NoSQL no deben colisionar.")
    }

    @Test
    fun testValidacionDeLogsYConmutacionDeTipos() {
        val manager = RepositoryManager()

        // Validar estado por defecto
        assertEquals(StorageType.SQL, manager.storage.value)

        // Ejecutar cambio y comprobar mutación de estado
        manager.switchStorage(StorageType.NOSQL)
        assertEquals(StorageType.NOSQL, manager.storage.value)
    }
}