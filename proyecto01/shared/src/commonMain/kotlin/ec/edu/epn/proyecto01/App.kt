package ec.edu.epn.proyecto01

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.client.request.*
import io.ktor.client.statement.*
import io.ktor.http.*
import io.ktor.serialization.kotlinx.json.*
import kotlinx.coroutines.launch
import kotlinx.serialization.json.Json

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun App() {
    val coroutineScope = rememberCoroutineScope()
    val storage = remember<StoragePlatform> { StoragePlatform() }

    val httpClient = remember {
        HttpClient {
            install(ContentNegotiation) {
                json(Json { ignoreUnknownKeys = true })
            }
        }
    }

    // Estado para controlar la pestaña activa (0 = Módulo 1, 1 = Módulo 2)
    var selectedTabState by remember { mutableStateOf(0) }
    val tabTitles = listOf("Módulo 1: Red REST", "Módulo 2: Seguridad")

    // Estados Módulo 1: Conectividad REST
    var postIdInput by remember { mutableStateOf("") }
    var postTitle by remember { mutableStateOf("") }
    var postBody by remember { mutableStateOf("") }
    var currentPostUserId by remember { mutableStateOf(1) }
    var isNetworkLoading by remember { mutableStateOf(false) }
    var networkStatusMessage by remember { mutableStateOf("Esperando consulta...") }

    // Estados Módulo 2: Almacenamiento Seguro
    var secretKeyInput by remember { mutableStateOf("") }
    var secretValueInput by remember { mutableStateOf("") }
    var storageResultNotification by remember { mutableStateOf("Ninguna acción realizada") }
    val storageMechanisms = listOf("SharedPreferences", "DataStore (Preferences)", "EncryptedSharedPreferences")
    var selectedMechanismIndex by remember { mutableStateOf(0) }
    var isDropdownExpanded by remember { mutableStateOf(false) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("FIS EPN - Laboratorio Práctico", fontWeight = FontWeight.Bold) },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer,
                    titleContentColor = MaterialTheme.colorScheme.onPrimaryContainer
                )
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {

            // Fila de pestañas para conmutar las pantallas de forma reactiva
            TabRow(selectedTabIndex = selectedTabState) {
                tabTitles.forEachIndexed { index, title ->
                    Tab(
                        selected = selectedTabState == index,
                        onClick = { selectedTabState = index },
                        text = { Text(title, fontWeight = FontWeight.SemiBold) }
                    )
                }
            }

            // Cuerpo contenedor con scroll independiente según la pestaña seleccionada
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp)
                    .verticalScroll(rememberScrollState()),
                verticalArrangement = Arrangement.spacedBy(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {

                if (selectedTabState == 0) {
                    // ==========================================================
                    // APARTADO PESTAÑA 01: MÓDULO 1 - CONECTIVIDAD REST HTTP API
                    // ==========================================================
                    Card(
                        modifier = Modifier.fillMaxWidth(),
                        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant)
                    ) {
                        Column(modifier = Modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(12.dp)) {
                            Text("Interacción Externa de Datos", style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold)

                            OutlinedTextField(
                                value = postIdInput,
                                onValueChange = { input ->
                                    if (input.all { it.isDigit() }) postIdInput = input
                                },
                                label = { Text("Identificador único (ID Post)") },
                                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                                enabled = !isNetworkLoading, // UX: Deshabilitar campos de texto en tránsito
                                modifier = Modifier.fillMaxWidth()
                            )

                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.spacedBy(8.dp)
                            ) {
                                Button(
                                    onClick = {
                                        if (postIdInput.isNotBlank()) {
                                            coroutineScope.launch {
                                                isNetworkLoading = true
                                                networkStatusMessage = "Consultando en servidor..."
                                                try {
                                                    val response: HttpResponse = httpClient.get("https://jsonplaceholder.typicode.com/posts/$postIdInput")
                                                    if (response.status == HttpStatusCode.OK) {
                                                        val post: Post = response.body()
                                                        postTitle = post.title
                                                        postBody = post.body
                                                        currentPostUserId = post.userId
                                                        networkStatusMessage = "Post cargado correctamente."
                                                    } else {
                                                        networkStatusMessage = "Error: Servidor respondió ${response.status.value}"
                                                    }
                                                } catch (e: Exception) {
                                                    networkStatusMessage = "Fallo de red: ${e.message}"
                                                } finally {
                                                    isNetworkLoading = false
                                                }
                                            }
                                        }
                                    },
                                    enabled = !isNetworkLoading && postIdInput.isNotBlank(), // UX: Deshabilitar botones en tránsito
                                    modifier = Modifier.weight(1f)
                                ) {
                                    Text("Consulta (GET)")
                                }

                                Button(
                                    onClick = {
                                        if (postIdInput.isNotBlank()) {
                                            coroutineScope.launch {
                                                isNetworkLoading = true
                                                networkStatusMessage = "Enviando actualización..."
                                                try {
                                                    val updatedPost = Post(
                                                        userId = currentPostUserId,
                                                        id = postIdInput.toInt(),
                                                        title = postTitle,
                                                        body = postBody
                                                    )
                                                    val response: HttpResponse = httpClient.put("https://jsonplaceholder.typicode.com/posts/$postIdInput") {
                                                        contentType(ContentType.Application.Json)
                                                        setBody(updatedPost)
                                                    }
                                                    if (response.status == HttpStatusCode.OK) {
                                                        networkStatusMessage = "Confirmación 200 OK: Respuesta simulada válida."
                                                    } else {
                                                        networkStatusMessage = "Servidor rechazó la petición: ${response.status.value}"
                                                    }
                                                } catch (e: Exception) {
                                                    networkStatusMessage = "Fallo al enviar PUT: ${e.message}"
                                                } finally {
                                                    isNetworkLoading = false
                                                }
                                            }
                                        }
                                    },
                                    enabled = !isNetworkLoading && postIdInput.isNotBlank(), // UX: Deshabilitar botones en tránsito
                                    modifier = Modifier.weight(1f)
                                ) {
                                    Text("Actualización (PUT)")
                                }
                            }
                        }
                    }

                    Card(modifier = Modifier.fillMaxWidth()) {
                        Column(modifier = Modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(12.dp)) {
                            Text("Contenido del Recurso Editable", style = MaterialTheme.typography.titleSmall, fontWeight = FontWeight.Bold)

                            OutlinedTextField(
                                value = postTitle,
                                onValueChange = { postTitle = it },
                                label = { Text("Título de la publicación") },
                                enabled = !isNetworkLoading,
                                modifier = Modifier.fillMaxWidth()
                            )

                            OutlinedTextField(
                                value = postBody,
                                onValueChange = { postBody = it },
                                label = { Text("Cuerpo del mensaje") },
                                enabled = !isNetworkLoading,
                                modifier = Modifier.fillMaxWidth(),
                                maxLines = 4
                            )

                            Text(
                                text = networkStatusMessage,
                                color = MaterialTheme.colorScheme.primary,
                                style = MaterialTheme.typography.bodyMedium,
                                fontWeight = FontWeight.Medium
                            )
                        }
                    }

                } else {
                    // ==========================================================
                    // APARTADO PESTAÑA 02: MÓDULO 2 - ALMACENAMIENTO SEGURO
                    // ==========================================================
                    Card(
                        modifier = Modifier.fillMaxWidth(),
                        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant)
                    ) {
                        Column(modifier = Modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(12.dp)) {
                            Text("Persistencia por Criterio Tecnológico", style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold)

                            OutlinedTextField(
                                value = secretKeyInput,
                                onValueChange = { secretKeyInput = it },
                                label = { Text("Llave de acceso (Key)") },
                                modifier = Modifier.fillMaxWidth()
                            )

                            OutlinedTextField(
                                value = secretValueInput,
                                onValueChange = { secretValueInput = it },
                                label = { Text("Valor / Secreto Confidencial") },
                                modifier = Modifier.fillMaxWidth()
                            )

                            Box(modifier = Modifier.fillMaxWidth().wrapContentSize(Alignment.Center)) {
                                OutlinedButton(onClick = { isDropdownExpanded = true }, modifier = Modifier.fillMaxWidth()) {
                                    Text("Mecanismo: ${storageMechanisms[selectedMechanismIndex]}")
                                }
                                DropdownMenu(
                                    expanded = isDropdownExpanded,
                                    onDismissRequest = { isDropdownExpanded = false }
                                ) {
                                    storageMechanisms.forEachIndexed { index, name ->
                                        DropdownMenuItem(
                                            text = { Text(name) },
                                            onClick = {
                                                selectedMechanismIndex = index
                                                isDropdownExpanded = false
                                            }
                                        )
                                    }
                                }
                            }

                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.spacedBy(8.dp)
                            ) {
                                Button(
                                    onClick = {
                                        if (secretKeyInput.isNotBlank() && secretValueInput.isNotBlank()) {
                                            coroutineScope.launch {
                                                when (selectedMechanismIndex) {
                                                    0 -> storage.savePreference(secretKeyInput, secretValueInput)
                                                    1 -> storage.saveDataStore(secretKeyInput, secretValueInput)
                                                    2 -> storage.saveEncrypted(secretKeyInput, secretValueInput)
                                                }
                                                storageResultNotification = "Dato guardado con éxito transaccional directo."
                                                secretValueInput = "" // UX: Limpieza inmediata del campo de entrada secreta
                                            }
                                        }
                                    },
                                    modifier = Modifier.weight(1f)
                                ) {
                                    Text("Guardar")
                                }

                                Button(
                                    onClick = {
                                        if (secretKeyInput.isNotBlank()) {
                                            coroutineScope.launch {
                                                val retrieved: String? = when (selectedMechanismIndex) {
                                                    0 -> storage.getPreference(secretKeyInput)
                                                    1 -> storage.getDataStore(secretKeyInput)
                                                    2 -> storage.getEncrypted(secretKeyInput)
                                                    else -> null
                                                }

                                                if (retrieved != null) {
                                                    storageResultNotification = "Éxito: [$retrieved]"
                                                } else {
                                                    storageResultNotification = "Notificación genérica: La llave buscada no existe en este compartimento."
                                                }
                                            }
                                        }
                                    },
                                    modifier = Modifier.weight(1f)
                                ) {
                                    Text("Recuperar")
                                }
                            }

                            Text(
                                text = storageResultNotification,
                                color = MaterialTheme.colorScheme.secondary,
                                style = MaterialTheme.typography.bodyMedium,
                                fontWeight = FontWeight.Medium
                            )
                        }
                    }
                }
            }
        }
    }
}