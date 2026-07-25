package epn.edu.apprefugios

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
expect fun MapView(
    modifier: Modifier,
    initialLat: Double,
    initialLng: Double,
    shelters: List<Shelter>,
    onShelterClick: (Shelter) -> Unit
)
