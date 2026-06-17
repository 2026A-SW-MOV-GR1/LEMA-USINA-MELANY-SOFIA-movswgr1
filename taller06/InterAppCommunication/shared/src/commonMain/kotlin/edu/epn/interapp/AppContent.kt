package edu.epn.interapp

import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import android.provider.MediaStore
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun AppContent(
    textoCompartido: String?,
    imagenCompartidaUri: Uri?,
    onClearData: () -> Unit
) {
    val context = LocalContext.current
    var inputTelefono by remember { mutableStateOf("") }
    var fotoThumbnail by remember { mutableStateOf<Bitmap?>(null) }

    // Capturador del resultado de la cámara (Foto Express)
    val camaraLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.StartActivityForResult()
    ) { result ->
        if (result.resultCode == android.activity.ComponentActivity.RESULT_OK) {
            val bitmap = result.data?.extras?.get("data") as? Bitmap
            fotoThumbnail = bitmap
        }
    }

    MaterialTheme {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
                .verticalScroll(rememberScrollState()),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(20.dp)
        ) {
            Text("Taller Práctico: Comunicación Inter-App", fontSize = 18.sp, style = MaterialTheme.typography.h6)

            // -----------------------------------------------------------
            // SECCIÓN A: MÓDULO DE INTENTS SALIENTES (DELEGACIÓN)
            // -----------------------------------------------------------
            Card(elevation = 4.dp, modifier = Modifier.fillMaxWidth()) {
                Column(modifier = Modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(12.dp)) {
                    Text("MÓDULO: INTENTS SALIENTES", style = MaterialTheme.typography.subtitle1, color = Color.Blue)

                    // Panel 1: El Llamador Misterioso
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        OutlinedTextField(
                            value = inputTelefono,
                            onValueChange = { inputTelefono = it },
                            label = { Text("Teléfono") },
                            modifier = Modifier.weight(1f)
                        )
                        Button(onClick = {
                            if (inputTelefono.isNotBlank()) {
                                val intentDial = Intent(Intent.ACTION_DIAL).apply {
                                    data = Uri.parse("tel:$inputTelefono")
                                }
                                context.startActivity(intentDial)
                            }
                        }) {
                            Text("INICIAR DIAL")
                        }
                    }

                    Divider()

                    // Panel 2: Foto Express
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(16.dp)
                    ) {
                        Box(
                            modifier = Modifier
                                .size(100.dp)
                                .border(1.dp, Color.Gray),
                            contentAlignment = Alignment.Center
                        ) {
                            if (fotoThumbnail != null) {
                                Image(bitmap = fotoThumbnail!!.asImageBitmap(), contentDescription = "Miniatura")
                            } else {
                                Text("[Miniatura]", fontSize = 12.sp, color = Color.Gray)
                            }
                        }
                        Button(onClick = {
                            val intentCamara = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
                            camaraLauncher.launch(intentCamara)
                        }) {
                            Text("TOMAR FOTO")
                        }
                    }
                }
            }

            // -----------------------------------------------------------
            // SECCIÓN B: MÓDULO DE INTENTS ENTRANTES (RECEPCIÓN)
            // -----------------------------------------------------------
            Card(elevation = 4.dp, modifier = Modifier.fillMaxWidth()) {
                Column(modifier = Modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(12.dp)) {
                    Text("MÓDULO: INTENTS ENTRANTES", style = MaterialTheme.typography.subtitle1, color = Color(0xFF388E3C))

                    val estadoText = if (textoCompartido != null || imagenCompartidaUri != null) {
                        "¡Datos Recibidos con Éxito!"
                    } else {
                        "Estado: Esperando datos externos..."
                    }
                    Text(estadoText, style = MaterialTheme.typography.body2)

                    // Caso Texto: Receptor de Chismes
                    if (textoCompartido != null) {
                        OutlinedTextField(
                            value = textoCompartido,
                            onValueChange = {},
                            readOnly = true,
                            label = { Text("Texto / Enlace Recibido") },
                            modifier = Modifier.fillMaxWidth()
                        )
                    }

                    // Caso Imagen: Lector de Imágenes
                    if (imagenCompartidaUri != null) {
                        Text("Contenedor Dinámico para Imagen Recibida:", fontSize = 12.sp)
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(150.dp)
                                .border(2.dp, Color.DarkGray),
                            contentAlignment = Alignment.Center
                        ) {
                            Text("Imagen detectada en ruta:\n$imagenCompartidaUri", fontSize = 11.sp, modifier = Modifier.padding(8.dp))
                        }
                    }

                    if (textoCompartido != null || imagenCompartidaUri != null) {
                        Button(
                            onClick = onClearData,
                            colors = ButtonDefaults.buttonColors(backgroundColor = Color.Red, contentColor = Color.White),
                            modifier = Modifier.align(Alignment.End)
                        ) {
                            Text("Limpiar")
                        }
                    }
                }
            }
        }
    }
}