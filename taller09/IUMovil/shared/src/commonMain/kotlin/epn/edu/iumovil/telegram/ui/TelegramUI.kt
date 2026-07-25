package epn.edu.iumovil.telegram.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.Help
import androidx.compose.material.icons.automirrored.filled.Message
import androidx.compose.material.icons.automirrored.filled.Sort
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import epn.edu.iumovil.telegram.model.Chat
import epn.edu.iumovil.telegram.model.Contact
import epn.edu.iumovil.telegram.model.MessageStatus
import epn.edu.iumovil.telegram.model.SampleData

val TelegramBlue = Color(0xFF24A1DE)
val TelegramLightBlue = Color(0xFFE3F2FD)
val TelegramGray = Color(0xFF8E8E93)
val TelegramLightGray = Color(0xFFF1F1F1)
val TelegramBadgeGray = Color(0xFFB0BEC5)
val TelegramLiveRed = Color(0xFFE91E63)

@Composable
fun TelegramApp() {
    var selectedTab by remember { mutableIntStateOf(0) }
    
    Scaffold(
        topBar = {
            when (selectedTab) {
                0 -> TelegramTopBar()
                1 -> StandardTopBar("Contactos")
                2 -> StandardTopBar("Ajustes")
                3 -> {} // Perfil has its own header
            }
        }
    ) { paddingValues ->
        Box(modifier = Modifier.padding(paddingValues).fillMaxSize().background(Color.White)) {
            when (selectedTab) {
                0 -> ChatScreen()
                1 -> ContactScreen()
                2 -> SettingsScreen()
                3 -> ProfileScreen()
            }
            
            // Botones flotantes (Cámara y Nuevo Chat)
            if (selectedTab == 0) {
                Column(
                    modifier = Modifier
                        .align(Alignment.BottomEnd)
                        .padding(bottom = 100.dp, end = 16.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    SmallFloatingActionButton(
                        onClick = { },
                        containerColor = Color.White,
                        contentColor = Color.Black,
                        shape = CircleShape,
                        modifier = Modifier.size(40.dp).shadow(4.dp, CircleShape)
                    ) {
                        Icon(Icons.Default.CameraAlt, contentDescription = "Camera", modifier = Modifier.size(20.dp))
                    }
                    FloatingActionButton(
                        onClick = { },
                        containerColor = TelegramBlue,
                        contentColor = Color.White,
                        shape = CircleShape,
                        modifier = Modifier.size(56.dp)
                    ) {
                        Icon(Icons.Default.AddComment, contentDescription = "New Chat")
                    }
                }
            }

            // Barra de navegación flotante
            Box(modifier = Modifier.align(Alignment.BottomCenter)) {
                CustomNavigationBar(selectedTab) { selectedTab = it }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun StandardTopBar(title: String) {
    TopAppBar(
        title = { Text(title, fontWeight = FontWeight.Bold) },
        actions = {
            IconButton(onClick = {}) {
                Icon(Icons.Default.Search, contentDescription = "Search")
            }
        },
        colors = TopAppBarDefaults.topAppBarColors(containerColor = Color.White)
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TelegramTopBar() {
    Column(modifier = Modifier.background(Color.White)) {
        TopAppBar(
            title = {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    // Profile Story circles like in screenshot
                    Row(modifier = Modifier.padding(end = 12.dp)) {
                        StoryCircle(Color(0xFFF06292))
                        StoryCircle(Color(0xFFFFB74D), modifier = Modifier.offset(x = (-8).dp))
                        StoryCircle(Color(0xFF64B5F6), modifier = Modifier.offset(x = (-16).dp))
                    }
                    Text("Telegram", fontWeight = FontWeight.Bold, color = TelegramBlue, fontSize = 20.sp)
                    Icon(Icons.Default.KeyboardArrowDown, contentDescription = null, tint = TelegramGray, modifier = Modifier.size(20.dp))
                }
            },
            actions = {
                IconButton(onClick = {}) {
                    Icon(Icons.Default.MoreVert, contentDescription = "More", tint = Color.Black)
                }
            },
            colors = TopAppBarDefaults.topAppBarColors(containerColor = Color.White)
        )
        SearchBar(placeholder = "Search Chats")
        CategoryTabs()
    }
}

@Composable
fun StoryCircle(color: Color, modifier: Modifier = Modifier) {
    Box(
        modifier = modifier
            .size(32.dp)
            .border(2.dp, Color.White, CircleShape)
            .padding(1.dp)
            .border(1.5.dp, Color(0xFFF06292), CircleShape)
            .padding(2.dp)
            .clip(CircleShape)
            .background(color)
    )
}

@Composable
fun CategoryTabs() {
    val categories = listOf("All", "Groups", "WORK", "Bots")
    val counts = listOf(8, 6, 2, 0)
    
    LazyRow(
        modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp, vertical = 8.dp),
        horizontalArrangement = Arrangement.spacedBy(24.dp)
    ) {
        items(categories.size) { index ->
            Row(verticalAlignment = Alignment.CenterVertically) {
                Text(
                    categories[index],
                    color = if (index == 0) TelegramBlue else TelegramGray,
                    fontWeight = FontWeight.Bold,
                    fontSize = 15.sp
                )
                if (counts[index] > 0) {
                    Spacer(modifier = Modifier.width(4.dp))
                    Box(
                        modifier = Modifier
                            .size(18.dp)
                            .clip(CircleShape)
                            .background(if (index == 0) TelegramBlue else TelegramGray.copy(alpha = 0.5f)),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(counts[index].toString(), color = Color.White, fontSize = 11.sp, fontWeight = FontWeight.Bold)
                    }
                }
            }
        }
    }
}

@Composable
fun CustomNavigationBar(selectedTab: Int, onTabSelected: (Int) -> Unit) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 12.dp, end = 12.dp, bottom = 4.dp), // Ajustado más abajo
        contentAlignment = Alignment.BottomCenter
    ) {
        Card(
            shape = RoundedCornerShape(40.dp),
            modifier = Modifier
                .height(72.dp)
                .fillMaxWidth()
                .shadow(8.dp, RoundedCornerShape(40.dp)),
            colors = CardDefaults.cardColors(containerColor = Color.White.copy(alpha = 0.95f))
        ) {
            Row(
                modifier = Modifier.fillMaxSize().padding(horizontal = 4.dp),
                horizontalArrangement = Arrangement.SpaceAround,
                verticalAlignment = Alignment.CenterVertically
            ) {
                NavigationItem(
                    selected = selectedTab == 0,
                    icon = Icons.AutoMirrored.Filled.Message,
                    label = "Chats",
                    badgeCount = 165,
                    onClick = { onTabSelected(0) }
                )
                NavigationItem(
                    selected = selectedTab == 1,
                    icon = Icons.Default.AccountCircle,
                    label = "Contactos",
                    onClick = { onTabSelected(1) }
                )
                NavigationItem(
                    selected = selectedTab == 2,
                    icon = Icons.Default.Settings,
                    label = "Ajustes",
                    onClick = { onTabSelected(2) }
                )
                NavigationItem(
                    selected = selectedTab == 3,
                    isProfile = true,
                    label = "Perfil",
                    onClick = { onTabSelected(3) }
                )
            }
        }
    }
}

@Composable
fun NavigationItem(
    selected: Boolean,
    icon: ImageVector? = null,
    label: String,
    badgeCount: Int = 0,
    isProfile: Boolean = false,
    onClick: () -> Unit
) {
    val contentColor = if (selected) TelegramBlue else Color.Black
    
    Box(
        modifier = Modifier
            .clip(RoundedCornerShape(30.dp))
            .clickable { onClick() }
            .padding(horizontal = 16.dp, vertical = 8.dp),
        contentAlignment = Alignment.Center
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Box(contentAlignment = Alignment.TopEnd) {
                if (isProfile) {
                    Box(
                        modifier = Modifier
                            .size(28.dp)
                            .clip(CircleShape)
                            .background(Color.LightGray)
                    ) // Placeholder for profile pic
                } else if (icon != null) {
                    Icon(
                        icon,
                        contentDescription = label,
                        tint = contentColor,
                        modifier = Modifier.size(28.dp)
                    )
                }
                
                if (badgeCount > 0) {
                    Box(
                        modifier = Modifier
                            .offset(x = 8.dp, y = (-4).dp)
                            .size(18.dp)
                            .clip(CircleShape)
                            .background(TelegramBlue)
                            .border(2.dp, Color.White, CircleShape),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(badgeCount.toString(), color = Color.White, fontSize = 10.sp, fontWeight = FontWeight.Bold)
                    }
                }
            }
            Text(label, color = contentColor, fontSize = 12.sp)
        }
        
        if (selected) {
            Box(
                modifier = Modifier
                    .matchParentSize()
                    .background(TelegramBlue.copy(alpha = 0.05f))
            )
        }
    }
}

@Composable
fun ChatScreen() {
    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        contentPadding = PaddingValues(start = 16.dp, end = 16.dp, bottom = 140.dp) // Padding extra para que la barra no tape el último chat
    ) {
        items(SampleData.chats) { chat ->
            ChatItem(chat)
        }
    }
}

@Composable
fun ChatItem(chat: Chat) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { }
            .padding(vertical = 12.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        // Avatar with status
        Box(contentAlignment = Alignment.BottomEnd) {
            val avatarColor = remember(chat.name) {
                val colors = listOf(Color(0xFFE57373), Color(0xFFF06292), Color(0xFFBA68C8), Color(0xFF9575CD), Color(0xFF7986CB), Color(0xFF64B5F6), Color(0xFF4FC3F7), Color(0xFF4DB6AC), Color(0xFF81C784), Color(0xFFAED581), Color(0xFFFFB74D))
                colors[chat.id % colors.size]
            }

            Box(
                modifier = Modifier
                    .size(56.dp)
                    .clip(CircleShape)
                    .background(
                        if (chat.name == "Saved Messages") TelegramBlue 
                        else avatarColor.copy(alpha = 0.2f)
                    ),
                contentAlignment = Alignment.Center
            ) {
                if (chat.name == "Saved Messages") {
                    Icon(Icons.Default.BookmarkBorder, contentDescription = null, tint = Color.White, modifier = Modifier.size(30.dp))
                } else {
                    Text(chat.name.take(1).uppercase(), color = avatarColor, fontSize = 22.sp, fontWeight = FontWeight.Bold)
                }
            }
            
            if (chat.isOnline) {
                Box(
                    modifier = Modifier
                        .size(14.dp)
                        .clip(CircleShape)
                        .background(Color.White)
                        .padding(2.dp)
                        .clip(CircleShape)
                        .background(Color(0xFF4CAF50))
                )
            }
            
            if (chat.isLive) {
                Box(
                    modifier = Modifier
                        .offset(y = 4.dp)
                        .clip(RoundedCornerShape(4.dp))
                        .background(TelegramLiveRed)
                        .padding(horizontal = 4.dp, vertical = 1.dp)
                ) {
                    Text("LIVE", color = Color.White, fontSize = 8.sp, fontWeight = FontWeight.Bold)
                }
            }
        }
        
        Spacer(modifier = Modifier.width(16.dp))
        
        Column(modifier = Modifier.weight(1f)) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Text(
                    text = chat.name,
                    fontWeight = FontWeight.Bold,
                    fontSize = 17.sp,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                    modifier = Modifier.weight(1f)
                )
                if (chat.isVerified) {
                    Icon(Icons.Default.Star, contentDescription = null, tint = TelegramBlue, modifier = Modifier.size(16.dp).padding(start = 4.dp))
                }
                
                Row(verticalAlignment = Alignment.CenterVertically) {
                    if (chat.messageStatus == MessageStatus.READ) {
                        Icon(Icons.Default.DoneAll, contentDescription = null, tint = Color(0xFF4CAF50), modifier = Modifier.size(16.dp))
                    }
                    if (chat.isPinned) {
                        Icon(Icons.Default.PushPin, contentDescription = null, tint = TelegramGray.copy(alpha = 0.5f), modifier = Modifier.size(14.dp).padding(start = 4.dp))
                    }
                    Text(chat.time, fontSize = 13.sp, color = TelegramGray, modifier = Modifier.padding(start = 4.dp))
                }
            }
            
            Row(verticalAlignment = Alignment.CenterVertically) {
                Text(
                    chat.lastMessage,
                    fontSize = 15.sp,
                    color = TelegramGray,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                    modifier = Modifier.weight(1f)
                )
                if (chat.unreadCount > 0) {
                    Box(
                        modifier = Modifier
                            .size(20.dp)
                            .clip(CircleShape)
                            .background(if (chat.isMuted) TelegramGray.copy(alpha = 0.3f) else TelegramBlue),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            chat.unreadCount.toString(), 
                            color = Color.White, 
                            fontSize = 12.sp, 
                            fontWeight = FontWeight.Bold
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun SearchBar(placeholder: String) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 8.dp)
            .height(44.dp)
            .clip(RoundedCornerShape(12.dp))
            .background(TelegramLightGray)
            .padding(horizontal = 12.dp),
        contentAlignment = Alignment.CenterStart
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Icon(Icons.Default.Search, contentDescription = null, tint = TelegramGray, modifier = Modifier.size(20.dp))
            Spacer(modifier = Modifier.width(8.dp))
            Text(placeholder, color = TelegramGray, fontSize = 16.sp)
        }
    }
}

@Composable
fun ContactScreen() {
    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        contentPadding = PaddingValues(bottom = 140.dp)
    ) {
        item { SearchBar("Buscar contactos") }
        item {
            ContactOption(Icons.Default.PersonAddAlt1, "Invitar amigos", Color(0xFF2196F3))
            ContactOption(Icons.Default.Call, "Llamadas recientes", Color(0xFF4CAF50))
            Spacer(modifier = Modifier.height(8.dp))
        }
        items(SampleData.contacts) { contact ->
            ContactItem(contact)
        }
    }
}

@Composable
fun ContactOption(icon: ImageVector, title: String, iconColor: Color) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { }
            .padding(horizontal = 16.dp, vertical = 12.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(
            modifier = Modifier
                .size(40.dp)
                .clip(RoundedCornerShape(12.dp))
                .background(iconColor.copy(alpha = 0.1f)),
            contentAlignment = Alignment.Center
        ) {
            Icon(icon, contentDescription = null, tint = iconColor)
        }
        Spacer(modifier = Modifier.width(16.dp))
        Text(title, fontSize = 16.sp)
    }
}

@Composable
fun ContactItem(contact: Contact) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { }
            .padding(horizontal = 16.dp, vertical = 8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        val avatarColor = remember(contact.name) {
            val colors = listOf(Color(0xFFE57373), Color(0xFFF06292), Color(0xFFBA68C8), Color(0xFF9575CD), Color(0xFF7986CB), Color(0xFF64B5F6), Color(0xFF4FC3F7), Color(0xFF4DB6AC), Color(0xFF81C784), Color(0xFFAED581), Color(0xFFFFB74D))
            colors[contact.name.length % colors.size]
        }

        Box(
            modifier = Modifier
                .size(48.dp)
                .clip(CircleShape)
                .background(avatarColor.copy(alpha = 0.2f)),
            contentAlignment = Alignment.Center
        ) {
            Text(contact.name.take(1).uppercase(), color = avatarColor, fontWeight = FontWeight.Bold)
        }
        Spacer(modifier = Modifier.width(16.dp))
        Column {
            Text(contact.name, fontWeight = FontWeight.Bold, fontSize = 16.sp)
            Text(contact.status, fontSize = 13.sp, color = TelegramGray)
        }
    }
}

@Composable
fun SettingsScreen() {
    LazyColumn(
        modifier = Modifier.fillMaxSize().background(TelegramLightGray),
        contentPadding = PaddingValues(bottom = 100.dp)
    ) {
        item {
            Column(
                modifier = Modifier.fillMaxWidth().background(Color.White).padding(vertical = 24.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Box(contentAlignment = Alignment.BottomEnd) {
                    Box(
                        modifier = Modifier.size(100.dp).clip(CircleShape).background(Color.LightGray),
                        contentAlignment = Alignment.Center
                    ) {
                         // Simulación de la foto de perfil basada en las imágenes
                         Icon(Icons.Default.Person, contentDescription = null, modifier = Modifier.size(60.dp), tint = Color.Gray)
                    }
                    Box(
                        modifier = Modifier
                            .size(32.dp)
                            .clip(CircleShape)
                            .background(TelegramBlue)
                            .border(2.dp, Color.White, CircleShape),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(Icons.Default.CameraAlt, contentDescription = null, tint = Color.White, modifier = Modifier.size(16.dp))
                    }
                }
                
                Spacer(modifier = Modifier.height(12.dp))
                Text("Melany", fontWeight = FontWeight.Bold, fontSize = 24.sp)
                Text("+593 987323479", color = TelegramGray, fontSize = 16.sp)
            }
        }
        
        item { 
            SettingsGroup {
                SettingsItem(Icons.Default.AccountCircle, "Cuenta", "Número, nombre de usuario, biografía", Color(0xFF2196F3))
                SettingsItem(Icons.Default.ChatBubble, "Ajustes de chats", "Fondo, modo nocturno, animaciones", Color(0xFFFF9800))
                SettingsItem(Icons.Default.Lock, "Privacidad y seguridad", "Últ. vez, dispositivos, llaves de acceso", Color(0xFF4CAF50))
                SettingsItem(Icons.Default.Notifications, "Notificaciones", "Sonidos, llamadas, distintivos", Color(0xFFE91E63))
                SettingsItem(Icons.Default.DataUsage, "Datos y almacenamiento", "Ajustes de la descarga de la multimedia", Color(0xFF2196F3))
                SettingsItem(Icons.Default.Folder, "Carpetas de chats", "Ordena los chats en carpetas", Color(0xFF03A9F4))
                SettingsItem(Icons.Default.Devices, "Dispositivos", "Gestiona los dispositivos conectados", Color(0xFF00BCD4))
                SettingsItem(Icons.Default.BatteryFull, "Ahorro de energía", "Reduce el consumo con carga baja", Color(0xFFFF5722))
                SettingsItem(Icons.Default.Language, "Idioma", "Español", Color(0xFF9C27B0))
            }
        }

        item {
            SettingsGroup {
                SettingsItem(Icons.Default.Stars, "Telegram Premium", null, Color(0xFF673AB7))
                SettingsItem(Icons.Default.Star, "Estrellas de Telegram", null, Color(0xFFFFC107))
                SettingsItem(Icons.Default.BusinessCenter, "Telegram Business", null, Color(0xFFE91E63))
                SettingsItem(Icons.Default.CardGiftcard, "Enviar un regalo", null, Color(0xFFFF9800))
            }
        }

        item {
            Text(
                "Ayuda",
                modifier = Modifier.padding(start = 32.dp, top = 16.dp, bottom = 8.dp),
                color = TelegramBlue,
                fontWeight = FontWeight.Bold,
                fontSize = 14.sp
            )
            SettingsGroup {
                SettingsItem(Icons.Default.QuestionAnswer, "Hacer una pregunta", null, Color(0xFFFF9800))
                SettingsItem(Icons.AutoMirrored.Filled.Help, "Preguntas frecuentes", null, Color(0xFF03A9F4))
                SettingsItem(Icons.Default.Info, "Aprende sobre Telegram", null, Color(0xFF9C27B0))
                SettingsItem(Icons.Default.PrivacyTip, "Política de privacidad", null, Color(0xFF4CAF50))
            }
        }

        item {
            Column(
                modifier = Modifier.fillMaxWidth().padding(vertical = 16.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text("Telegram para Android v12.8.3 (6922)", color = TelegramGray, fontSize = 12.sp)
                Text("store bundled arm64-v8a", color = TelegramGray, fontSize = 12.sp)
            }
        }
    }
}

@Composable
fun ProfileScreen() {
    LazyColumn(
        modifier = Modifier.fillMaxSize().background(Color.White),
        contentPadding = PaddingValues(bottom = 100.dp)
    ) {
        item {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(350.dp)
            ) {
                // Imagen de fondo (Placeholder)
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(
                            brush = Brush.verticalGradient(
                                colors = listOf(Color.Transparent, Color.Black.copy(alpha = 0.6f)),
                                startY = 300f
                            )
                        )
                ) {
                    Box(modifier = Modifier.fillMaxSize().background(Color.LightGray))
                }
                
                Column(
                    modifier = Modifier
                        .align(Alignment.BottomStart)
                        .padding(16.dp)
                ) {
                    Text(
                        "Melany",
                        color = Color.White,
                        fontSize = 28.sp,
                        fontWeight = FontWeight.Bold
                    )
                    Text(
                        "en línea",
                        color = Color.White.copy(alpha = 0.8f),
                        fontSize = 16.sp
                    )
                }
                
                // Iconos superiores
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 40.dp, start = 16.dp, end = 16.dp),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = null, tint = Color.White)
                    Row {
                        Icon(Icons.Default.Search, contentDescription = null, tint = Color.White)
                        Spacer(modifier = Modifier.width(16.dp))
                        Icon(Icons.Default.MoreVert, contentDescription = null, tint = Color.White)
                    }
                }
            }
        }
        
        item {
            Column(modifier = Modifier.padding(16.dp)) {
                Text("Información", color = TelegramBlue, fontWeight = FontWeight.Bold, fontSize = 15.sp)
                Spacer(modifier = Modifier.height(16.dp))
                
                ProfileDetailItem("+593 987323479", "Móvil")
                HorizontalDivider(modifier = Modifier.padding(vertical = 12.dp), color = TelegramLightGray)
                ProfileDetailItem("@melany_sofia", "Nombre de usuario")
                HorizontalDivider(modifier = Modifier.padding(vertical = 12.dp), color = TelegramLightGray)
                ProfileDetailItem("Ingeniería en Sistemas | EPN", "Bio")
            }
        }
    }
}

@Composable
fun ProfileDetailItem(value: String, label: String) {
    Column {
        Text(value, fontSize = 17.sp, color = Color.Black)
        Text(label, fontSize = 14.sp, color = TelegramGray)
    }
}

@Composable
fun SettingsGroup(content: @Composable ColumnScope.() -> Unit) {
    Card(
        modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp, vertical = 8.dp),
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White)
    ) {
        Column(content = content)
    }
}

@Composable
fun SettingsItem(icon: ImageVector, title: String, subtitle: String? = null, iconColor: Color) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { }
            .padding(16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(
            modifier = Modifier
                .size(32.dp)
                .clip(RoundedCornerShape(8.dp))
                .background(iconColor),
            contentAlignment = Alignment.Center
        ) {
            Icon(icon, contentDescription = null, tint = Color.White, modifier = Modifier.size(20.dp))
        }
        Spacer(modifier = Modifier.width(16.dp))
        Column {
            Text(title, fontSize = 16.sp)
            if (subtitle != null) {
                Text(subtitle, fontSize = 13.sp, color = TelegramGray)
            }
        }
    }
}
