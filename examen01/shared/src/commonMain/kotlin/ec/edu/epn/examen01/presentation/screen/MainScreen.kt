package ec.edu.epn.examen01.presentation.screen

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import ec.edu.epn.examen01.data.repository.StorageType
import ec.edu.epn.examen01.presentation.viewmodel.UserViewModel

@Composable
fun MainScreen(
    viewModel: UserViewModel
) {
    val users by viewModel.users.collectAsState()
    val storageType by viewModel.storage.collectAsState()

    var name by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var editingId by remember { mutableStateOf<Long?>(null) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        // Título de la sección principal
        Text(
            text = "Examen 01 - Persistencia Dual",
            style = MaterialTheme.typography.headlineSmall
        )

        Spacer(modifier = Modifier.height(16.dp))

        // --- EN ESTA PARTE EXACTA AGREGAMOS EL CONTROL RECONVERTIDO ---
        ElevatedCard(
            modifier = Modifier.fillMaxWidth(),
            colors = CardDefaults.elevatedCardColors(
                containerColor = MaterialTheme.colorScheme.surfaceVariant
            )
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column {
                        Text(
                            text = "Estrategia de Almacenamiento",
                            style = MaterialTheme.typography.labelMedium
                        )
                        Text(
                            text = if (storageType == StorageType.SQL) "Estructurada (SQL)" else "Ágil (NoSQL)",
                            style = MaterialTheme.typography.bodyLarge,
                            color = MaterialTheme.colorScheme.primary
                        )
                    }

                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Text("SQL", style = MaterialTheme.typography.labelLarge)
                        Spacer(modifier = Modifier.width(8.dp))
                        Switch(
                            checked = storageType == StorageType.NOSQL,
                            onCheckedChange = { isNoSql ->
                                if (isNoSql) {
                                    viewModel.switchStorage(StorageType.NOSQL)
                                } else {
                                    viewModel.switchStorage(StorageType.SQL)
                                }
                            }
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Text("NoSQL", style = MaterialTheme.typography.labelLarge)
                    }
                }

                Spacer(modifier = Modifier.height(12.dp))

                // Indicador de Origen Activo mediante un Chip de Color dinámico
                AssistChip(
                    onClick = { },
                    label = {
                        Text(
                            text = if (storageType == StorageType.SQL) "Origen: SQLite Core" else "Origen: Almacén NoSQL Local"
                        )
                    },
                    colors = AssistChipDefaults.assistChipColors(
                        containerColor = if (storageType == StorageType.SQL) {
                            MaterialTheme.colorScheme.primaryContainer
                        } else {
                            MaterialTheme.colorScheme.tertiaryContainer
                        },
                        labelColor = if (storageType == StorageType.SQL) {
                            MaterialTheme.colorScheme.onPrimaryContainer
                        } else {
                            MaterialTheme.colorScheme.onTertiaryContainer
                        }
                    )
                )
            }
        }
        // --- FIN DEL BLOQUE DE CONTROL DE CONMUTACIÓN ---

        Spacer(modifier = Modifier.height(16.dp))

        OutlinedTextField(
            value = name,
            onValueChange = { name = it },
            label = { Text("Nombre") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(8.dp))

        OutlinedTextField(
            value = email,
            onValueChange = { email = it },
            label = { Text("Email") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(12.dp))

        Button(
            onClick = {
                if (name.isNotBlank() && email.isNotBlank()) {
                    if (editingId == null) {
                        viewModel.addUser(name, email)
                    } else {
                        viewModel.updateUser(editingId!!, name, email)
                        editingId = null
                    }
                    name = ""
                    email = ""
                }
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(if (editingId == null) "Guardar Usuario" else "Actualizar Usuario")
        }

        Spacer(modifier = Modifier.height(24.dp))

        Text(
            text = "Registros en el Motor Activo",
            style = MaterialTheme.typography.titleMedium
        )

        Spacer(modifier = Modifier.height(8.dp))

        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items(users) { user ->
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    colors = CardDefaults.cardColors(
                        containerColor = MaterialTheme.colorScheme.surface
                    )
                ) {
                    Column(
                        modifier = Modifier.padding(16.dp)
                    ) {
                        Text(
                            text = user.name,
                            style = MaterialTheme.typography.titleMedium
                        )
                        Text(
                            text = user.email,
                            style = MaterialTheme.typography.bodyMedium,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )

                        Spacer(modifier = Modifier.height(12.dp))

                        Row(
                            horizontalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            OutlinedButton(
                                onClick = {
                                    editingId = user.id
                                    name = user.name
                                    email = user.email
                                }
                            ) {
                                Text("Editar")
                            }

                            Button(
                                onClick = { viewModel.deleteUser(user.id) },
                                colors = ButtonDefaults.buttonColors(
                                    containerColor = MaterialTheme.colorScheme.error
                                )
                            ) {
                                Text("Eliminar")
                            }
                        }
                    }
                }
            }
        }
    }
}