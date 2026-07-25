package epn.edu.apprefugios

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun App(
    sightingCase: SightingFollowupCase?,
    onSendToReencuentro: (Shelter, SightingFollowupCase) -> Unit
) {
    // Estado local para simulación si no viene un caso externo
    var demoCase by remember { mutableStateOf<SightingFollowupCase?>(null) }
    val activeCase = sightingCase ?: demoCase
    
    // Estado para mostrar mensaje de éxito
    var showSuccessDialog by remember { mutableStateOf(false) }
    var selectedShelterName by remember { mutableStateOf("") }

    // Paleta de colores alineada con App 4 (Verdes y Azules)
    val customColors = lightColorScheme(
        primary = Color(0xFF4CAF50), 
        onPrimary = Color.White,
        secondary = Color(0xFF1976D2), 
        surface = Color(0xFFF1F8E9), 
        background = Color(0xFFF1F8E9)
    )

    MaterialTheme(colorScheme = customColors) {
        if (showSuccessDialog) {
            AlertDialog(
                onDismissRequest = { 
                    showSuccessDialog = false 
                    demoCase = null // Volver a la interfaz inicial tras la simulación
                },
                confirmButton = {
                    TextButton(onClick = { 
                        showSuccessDialog = false 
                        demoCase = null // Volver a la interfaz inicial tras la simulación
                    }) {
                        Text("Aceptar", color = Color(0xFF4CAF50))
                    }
                },
                title = { Text("¡Envío Exitoso!") },
                text = { Text("Se ha notificado al refugio $selectedShelterName sobre el caso de ${activeCase?.petName}. La App 4 (Reencuentro) debería abrirse.") },
                icon = { Text("✅", fontSize = 32.sp) },
                containerColor = Color.White
            )
        }

        Scaffold(
            topBar = {
                CenterAlignedTopAppBar(
                    title = {
                        Text(
                            text = "App 3: Refugios (KMP)",
                            style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold)
                        )
                    },
                    colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                        containerColor = Color(0xFFC8E6C9), 
                        titleContentColor = Color.Black
                    )
                )
            }
        ) { paddingValues ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues)
                    .background(Color(0xFFF1F8E9))
            ) {
                if (activeCase == null) {
                    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                        Column(horizontalAlignment = Alignment.CenterHorizontally) {
                            Text(
                                text = "Esperando Intent de App 2...",
                                style = MaterialTheme.typography.bodyLarge,
                                color = Color.Gray,
                                modifier = Modifier.padding(bottom = 16.dp)
                            )
                            Button(
                                onClick = {
                                    demoCase = SightingFollowupCase(
                                        petId = "thor-123",
                                        petName = "Thor",
                                        petType = "Perro",
                                        sightingLat = -0.2764,
                                        sightingLng = -78.5468,
                                        sightingDescription = "Joaquín Gutiérrez y José Peralta, Quito",
                                        sightingAt = "2024-05-25T08:30:00",
                                        contactPhone = "0991234567"
                                    )
                                },
                                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF4CAF50))
                            ) {
                                Text("Simular Caso de Prueba")
                            }
                        }
                    }
                } else {
                    // Tarjeta Principal (Compactada para dar espacio al mapa)
                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 16.dp, vertical = 8.dp),
                        colors = CardDefaults.cardColors(containerColor = Color.White),
                        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
                        shape = MaterialTheme.shapes.medium
                    ) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier.padding(8.dp)
                        ) {
                            // Icono más pequeño a la izquierda
                            Surface(
                                color = Color(0xFF66BB6A),
                                shape = MaterialTheme.shapes.small,
                                modifier = Modifier.size(60.dp)
                            ) {
                                Box(contentAlignment = Alignment.Center) {
                                    Text(text = "🐾", fontSize = 24.sp)
                                }
                            }

                            Column(modifier = Modifier.padding(start = 12.dp)) {
                                Text(
                                    text = "Buscando para ${activeCase.petName}",
                                    style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold)
                                )
                                Text(
                                    text = "📍 ${activeCase.sightingDescription}",
                                    style = MaterialTheme.typography.bodySmall,
                                    maxLines = 1
                                )
                                Text(
                                    text = "📞 ${activeCase.contactPhone}",
                                    style = MaterialTheme.typography.bodySmall
                                )
                            }
                        }
                    }

                    // Mapa interactivo (Con más peso)
                    val shelters = remember(activeCase) {
                        listOf(
                            Shelter("1", "Refugio San Roque", "Quito, Calle de los Milagros", activeCase.sightingLat + 0.005, activeCase.sightingLng + 0.005, "0987654321"),
                            Shelter("2", "Hogar Canino Sur", "Sect. Solanda", activeCase.sightingLat - 0.004, activeCase.sightingLng + 0.002, "0912345678"),
                            Shelter("3", "Rescate Animal UIO", "Av. Maldonado y Peralta", activeCase.sightingLat + 0.001, activeCase.sightingLng - 0.003)
                        )
                    }

                    var selectedShelter by remember { mutableStateOf<Shelter?>(null) }

                    Box(modifier = Modifier.weight(2f).padding(horizontal = 16.dp)) {
                        MapView(
                            modifier = Modifier.fillMaxSize(),
                            initialLat = activeCase.sightingLat,
                            initialLng = activeCase.sightingLng,
                            shelters = shelters,
                            onShelterClick = { selectedShelter = it }
                        )
                    }

                    // Panel inferior de selección (Compacto)
                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp),
                        colors = CardDefaults.cardColors(containerColor = Color.White),
                        elevation = CardDefaults.cardElevation(defaultElevation = 8.dp)
                    ) {
                        Column(modifier = Modifier.padding(12.dp)) {
                            Text(
                                text = "Refugios Cercanos",
                                style = MaterialTheme.typography.titleSmall.copy(color = Color(0xFF2E7D32), fontWeight = FontWeight.Bold)
                            )
                            
                            LazyColumn(modifier = Modifier.heightIn(max = 100.dp)) {
                                items(shelters) { shelter ->
                                    ShelterRow(
                                        shelter = shelter,
                                        isSelected = selectedShelter?.id == shelter.id,
                                        onSelect = { selectedShelter = shelter }
                                    )
                                }
                            }

                            Button(
                                onClick = { 
                                    if (selectedShelter != null) {
                                        selectedShelterName = selectedShelter!!.name
                                        onSendToReencuentro(selectedShelter!!, activeCase)
                                        showSuccessDialog = true
                                    }
                                },
                                enabled = selectedShelter != null,
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(top = 8.dp)
                                    .height(48.dp),
                                colors = ButtonDefaults.buttonColors(
                                    containerColor = Color(0xFF1976D2),
                                    disabledContainerColor = Color.LightGray
                                ),
                                shape = MaterialTheme.shapes.medium
                            ) {
                                Text(
                                    if (selectedShelter != null) "ENVIAR A REENCUENTRO" else "SELECCIONA UN REFUGIO",
                                    style = MaterialTheme.typography.labelLarge
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun ShelterRow(
    shelter: Shelter,
    isSelected: Boolean,
    onSelect: () -> Unit
) {
    Surface(
        onClick = onSelect,
        color = if (isSelected) Color(0xFFE8F5E9) else Color.Transparent,
        modifier = Modifier.fillMaxWidth()
    ) {
        Row(
            modifier = Modifier.padding(8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            RadioButton(
                selected = isSelected,
                onClick = onSelect,
                colors = RadioButtonDefaults.colors(selectedColor = Color(0xFF4CAF50))
            )
            Column(modifier = Modifier.padding(start = 8.dp)) {
                Text(text = "🏠 " + shelter.name, style = MaterialTheme.typography.bodyLarge.copy(fontWeight = FontWeight.Medium))
                Text(text = shelter.address, style = MaterialTheme.typography.bodySmall, color = Color.Gray)
            }
        }
    }
}
