package epn.edu.apprefugios

data class SightingFollowupCase(
    val petId: String,
    val petName: String,
    val petType: String,
    val sightingLat: Double,
    val sightingLng: Double,
    val sightingDescription: String,
    val sightingAt: String,
    val contactPhone: String
)

data class Shelter(
    val id: String,
    val name: String,
    val address: String,
    val lat: Double,
    val lng: Double,
    val phone: String? = null
)
