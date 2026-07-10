package edu.epn.interapp

import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp

@Composable
fun App() {
    MaterialTheme {
        val platformActions = remember { getPlatformActions() }
        var phoneNumber by remember { mutableStateOf("") }

        Surface(
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colorScheme.background
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp)
                    .verticalScroll(rememberScrollState()),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(24.dp)
            ) {
                Text("TALLER 06: COMUNICACIÓN INTER-APP", style = MaterialTheme.typography.headlineMedium)

                // --- SECCIÓN A: MÓDULO DE ACCIONES SALIENTES (Delegación) ---
                Card(modifier = Modifier.fillMaxWidth()) {
                    Column(modifier = Modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(8.dp)) {
                        Text("MÓDULO: INTENTS SALIENTES", style = MaterialTheme.typography.titleMedium)
                        HorizontalDivider()
                        
                        // Panel 1: Teléfono (Intent.ACTION_DIAL)
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            OutlinedTextField(
                                value = phoneNumber,
                                onValueChange = { phoneNumber = it },
                                label = { Text("Teléfono: 0987654321") },
                                modifier = Modifier.weight(1f)
                            )
                            Button(onClick = { platformActions.openDialer(phoneNumber) }) {
                                Text("INICIAR DIAL")
                            }
                        }

                        Spacer(modifier = Modifier.height(8.dp))

                        // Panel 2: Foto (MediaStore.ACTION_IMAGE_CAPTURE)
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(16.dp)
                        ) {
                            Box(
                                modifier = Modifier
                                    .size(100.dp)
                                    .border(1.dp, Color.Gray)
                                    .padding(4.dp),
                                contentAlignment = Alignment.Center
                            ) {
                                if (AppState.outgoingPhoto != null) {
                                    Image(
                                        bitmap = AppState.outgoingPhoto!!,
                                        contentDescription = "Miniatura",
                                        modifier = Modifier.fillMaxSize(),
                                        contentScale = ContentScale.Crop
                                    )
                                } else {
                                    Text("Miniatura", style = MaterialTheme.typography.bodySmall)
                                }
                            }
                            Button(onClick = { platformActions.takePhoto() }) {
                                Text("TOMAR FOTO")
                            }
                        }
                    }
                }

                // --- SECCIÓN B: MÓDULO DE ACCIONES ENTRANTES (Filtros en el Manifest) ---
                Card(modifier = Modifier.fillMaxWidth()) {
                    Column(modifier = Modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(8.dp)) {
                        Text("MÓDULO: INTENTS ENTRANTES", style = MaterialTheme.typography.titleMedium)
                        HorizontalDivider()

                        val status = if (AppState.receivedText == null && AppState.receivedImage == null) {
                            "Estado: Esperando datos externos..."
                        } else {
                            "Estado: ¡Datos recibidos!"
                        }
                        Text(status, color = if (AppState.receivedText == null && AppState.receivedImage == null) Color.Gray else MaterialTheme.colorScheme.primary)

                        // Caso Text (Receptor de Chismes)
                        OutlinedTextField(
                            value = AppState.receivedText ?: "",
                            onValueChange = {},
                            readOnly = true,
                            label = { Text("Caja de Texto para texto recibido") },
                            modifier = Modifier.fillMaxWidth(),
                            minLines = 3
                        )

                        // Caso Image (Lector de Imágenes)
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(200.dp)
                                .border(1.dp, Color.Gray),
                            contentAlignment = Alignment.Center
                        ) {
                            if (AppState.receivedImage != null) {
                                Image(
                                    bitmap = AppState.receivedImage!!,
                                    contentDescription = "Imagen Recibida",
                                    modifier = Modifier.fillMaxSize(),
                                    contentScale = ContentScale.Fit
                                )
                            } else {
                                Text("Contenedor Dinámico para Imagen Recibida", textAlign = androidx.compose.ui.text.style.TextAlign.Center)
                            }
                        }
                        
                        Button(
                            onClick = { AppState.resetReceivedData() },
                            modifier = Modifier.align(Alignment.End),
                            enabled = AppState.receivedText != null || AppState.receivedImage != null
                        ) {
                            Text("LIMPIAR RECIBIDOS")
                        }
                    }
                }
            }
        }
    }
}
