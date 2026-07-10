package edu.epn.controladorapp

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import org.jetbrains.compose.resources.painterResource
import androidx.compose.ui.tooling.preview.Preview

@Composable
@Preview
fun App() {
    MaterialTheme {
        var count by rememberSaveable { mutableStateOf(0) }

        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.background),
            contentAlignment = Alignment.Center
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                // El círculo con el número
                Box(
                    modifier = Modifier
                        .size(200.dp)
                        .clip(CircleShape)
                        .background(MaterialTheme.colorScheme.primary),
                    contentAlignment = Alignment.Center
                ) {
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Text(
                            text = "CONTADOR",
                            color = MaterialTheme.colorScheme.onPrimary.copy(alpha = 0.8f),
                            style = MaterialTheme.typography.labelMedium
                        )
                        Text(
                            text = "$count",
                            color = MaterialTheme.colorScheme.onPrimary,
                            fontSize = 64.sp,
                            fontWeight = FontWeight.Bold
                        )
                    }
                }

                Spacer(modifier = Modifier.height(32.dp))

                // El botón de acción
                Button(
                    onClick = { count++ },
                    modifier = Modifier.height(56.dp).fillMaxWidth(0.5f),
                    shape = MaterialTheme.shapes.medium
                ) {
                    Text("SUMAR +1", fontSize = 18.sp)
                }
                
                Spacer(modifier = Modifier.height(16.dp))
                
                // Botón opcional para reiniciar (por si quieres probar más)
                TextButton(onClick = { count = 0 }) {
                    Text("Reiniciar", color = MaterialTheme.colorScheme.secondary)
                }
            }
        }
    }
}
