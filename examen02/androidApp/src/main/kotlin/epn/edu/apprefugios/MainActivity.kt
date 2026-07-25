package epn.edu.apprefugios

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.tooling.preview.Preview

class MainActivity : ComponentActivity() {
    private val sightingCaseState = mutableStateOf<SightingFollowupCase?>(null)

    override fun onCreate(savedInstanceState: Bundle?) {
        enableEdgeToEdge()
        super.onCreate(savedInstanceState)

        handleIntent(intent)

        setContent {
            App(
                sightingCase = sightingCaseState.value,
                onSendToReencuentro = { shelter, sightingCase ->
                    sendShelterMatchIntent(shelter, sightingCase)
                }
            )
        }
    }

    override fun onNewIntent(intent: Intent) {
        super.onNewIntent(intent)
        setIntent(intent)
        handleIntent(intent)
    }

    private fun handleIntent(intent: Intent?) {
        if (intent?.action == IntentContract.ACTION_SIGHTING_FOLLOWUP) {
            val extras = intent.extras ?: return
            val sightingCase = SightingFollowupCase(
                petId = extras.getString(IntentContract.EXTRA_PET_ID) ?: "",
                petName = extras.getString(IntentContract.EXTRA_PET_NAME) ?: "Desconocido",
                petType = extras.getString(IntentContract.EXTRA_PET_TYPE) ?: "otro",
                sightingLat = extras.getDouble(IntentContract.EXTRA_SIGHTING_LAT),
                sightingLng = extras.getDouble(IntentContract.EXTRA_SIGHTING_LNG),
                sightingDescription = extras.getString(IntentContract.EXTRA_SIGHTING_DESCRIPTION) ?: "",
                sightingAt = extras.getString(IntentContract.EXTRA_SIGHTING_AT) ?: "",
                contactPhone = extras.getString(IntentContract.EXTRA_CONTACT_PHONE) ?: ""
            )
            sightingCaseState.value = sightingCase
        }
    }

    private fun sendShelterMatchIntent(shelter: Shelter, sightingCase: SightingFollowupCase) {
        try {
            val intent = Intent(IntentContract.ACTION_SHELTER_MATCH).apply {
                addCategory(IntentContract.CATEGORY_DEFAULT)
                putExtra(IntentContract.EXTRA_PET_ID, sightingCase.petId)
                putExtra(IntentContract.EXTRA_PET_NAME, sightingCase.petName)
                putExtra(IntentContract.EXTRA_SHELTER_NAME, shelter.name)
                putExtra(IntentContract.EXTRA_SHELTER_ADDRESS, shelter.address)
                putExtra(IntentContract.EXTRA_SHELTER_LAT, shelter.lat)
                putExtra(IntentContract.EXTRA_SHELTER_LNG, shelter.lng)
                putExtra(IntentContract.EXTRA_SHELTER_PHONE, shelter.phone)
                putExtra(IntentContract.EXTRA_CONTACT_PHONE, sightingCase.contactPhone)
            }
            startActivity(intent)
        } catch (e: Exception) {
            // Manejar error si la App 4 no está instalada
            e.printStackTrace()
        }
    }
}

@Preview
@Composable
fun AppAndroidPreview() {
    App(sightingCase = null, onSendToReencuentro = { _, _ -> })
}
