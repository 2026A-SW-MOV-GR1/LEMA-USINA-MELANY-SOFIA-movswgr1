package epn.edu.apprefugios

object IntentContract {
    // Paso 2 -> 3: Recibir de App 2 (Avistamientos)
    const val ACTION_SIGHTING_FOLLOWUP = "com.examenb2.petflow.action.SIGHTING_FOLLOWUP"
    const val CATEGORY_DEFAULT = "android.intent.category.DEFAULT"

    const val EXTRA_PET_ID = "pet_id"
    const val EXTRA_PET_NAME = "pet_name"
    const val EXTRA_PET_TYPE = "pet_type"
    const val EXTRA_SIGHTING_LAT = "sighting_lat"
    const val EXTRA_SIGHTING_LNG = "sighting_lng"
    const val EXTRA_SIGHTING_DESCRIPTION = "sighting_description"
    const val EXTRA_SIGHTING_AT = "sighting_at"
    const val EXTRA_CONTACT_PHONE = "contact_phone"

    // Paso 3 -> 4: Enviar a App 4 (Reencuentro)
    const val ACTION_SHELTER_MATCH = "com.examenb2.petflow.action.SHELTER_MATCH"
    
    const val EXTRA_SHELTER_NAME = "shelter_name"
    const val EXTRA_SHELTER_ADDRESS = "shelter_address"
    const val EXTRA_SHELTER_LAT = "shelter_lat"
    const val EXTRA_SHELTER_LNG = "shelter_lng"
    const val EXTRA_SHELTER_PHONE = "shelter_phone"
}
