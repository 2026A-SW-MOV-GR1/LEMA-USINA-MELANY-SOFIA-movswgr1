package epn.edu.iumovil.telegram.model

data class Chat(
    val id: Int,
    val name: String,
    val lastMessage: String,
    val time: String,
    val unreadCount: Int = 0,
    val isOnline: Boolean = false,
    val imageUrl: String? = null,
    val isMuted: Boolean = false,
    val isPinned: Boolean = false,
    val isVerified: Boolean = false,
    val isLive: Boolean = false,
    val messageStatus: MessageStatus = MessageStatus.NONE
)

enum class MessageStatus {
    NONE, SENT, DELIVERED, READ
}

data class Contact(
    val id: Int,
    val name: String,
    val status: String,
    val isOnline: Boolean = false,
    val imageUrl: String? = null,
    val letter: String? = null
)

data class Channel(
    val id: Int,
    val name: String,
    val subscribers: String,
    val description: String
)

object SampleData {
    val chats = listOf(
        Chat(0, "Saved Messages", "IMG_420.png", "Fri", isPinned = true),
        Chat(1, "Emma Torreaux", "Bob says hi.", "9:41", isOnline = true, isVerified = true, messageStatus = MessageStatus.READ),
        Chat(2, "Roberto", "Say hello to Emma.", "9:41", unreadCount = 1),
        Chat(3, "8Bit Times", "8Bit Times started a Live Stream", "9:41", isMuted = true, isLive = true, unreadCount = 1),
        Chat(4, "Digital Nomads", "Jennie: We just reached 2,500 membe...", "9:22", isMuted = true),
        Chat(5, "Penelope", "Table for four, 2 PM. Be there.", "9:12", unreadCount = 1)
    )

    val contacts = listOf(
        Contact(1, "Contacto 1", "últ. vez hace unas semanas"),
        Contact(2, "Contacto 2", "últ. vez el 2 de jun a las 15:34"),
        Contact(3, "Contacto 3", "últ. vez el 27 de mar a las 00:00"),
        Contact(4, "Contacto 4", "últ. vez a las 23:03"),
        Contact(5, "Contacto 5", "últ. vez hace unos días"),
        Contact(6, "Contacto 6", "últ. vez el 31 de mar a las 08:30"),
        Contact(7, "Contacto 7", "últ. vez a las 22:28")
    )

    val channels = listOf(
        Channel(1, "Kotlin Weekly", "15k subscribers", "Stay updated with Kotlin"),
        Channel(2, "Android Devs", "100k subscribers", "Official Android channel")
    )
}
