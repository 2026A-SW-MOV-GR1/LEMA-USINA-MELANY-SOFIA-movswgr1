package epn.edu.apprefugios

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.Marker
import com.google.maps.android.compose.MarkerState
import com.google.maps.android.compose.rememberCameraPositionState

@Composable
actual fun MapView(
    modifier: Modifier,
    initialLat: Double,
    initialLng: Double,
    shelters: List<Shelter>,
    onShelterClick: (Shelter) -> Unit
) {
    val initialPos = LatLng(initialLat, initialLng)
    val cameraPositionState = rememberCameraPositionState {
        position = CameraPosition.fromLatLngZoom(initialPos, 15f)
    }

    GoogleMap(
        modifier = modifier,
        cameraPositionState = cameraPositionState
    ) {
        // Marcador del avistamiento
        Marker(
            state = MarkerState(position = initialPos),
            title = "Último avistamiento",
            snippet = "Punto de referencia"
        )

        // Marcadores de refugios
        shelters.forEach { shelter ->
            Marker(
                state = MarkerState(position = LatLng(shelter.lat, shelter.lng)),
                title = shelter.name,
                snippet = shelter.address,
                onClick = {
                    onShelterClick(shelter)
                    true
                }
            )
        }
    }
}
