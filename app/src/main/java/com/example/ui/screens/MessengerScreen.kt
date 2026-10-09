package com.example.ui.screens

import androidx.activity.compose.BackHandler
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.Send
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.AttachFile
import androidx.compose.material.icons.filled.Call
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Description
import androidx.compose.material.icons.filled.DoneAll
import androidx.compose.material.icons.filled.EmojiEmotions
import androidx.compose.material.icons.filled.FiberManualRecord
import androidx.compose.material.icons.filled.Image
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Mic
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.filled.Newspaper
import androidx.compose.material.icons.filled.NotificationsActive
import androidx.compose.material.icons.filled.PersonAdd
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Security
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.filled.SmartToy
import androidx.compose.material.icons.filled.Stop
import androidx.compose.material.icons.filled.Videocam
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.Badge
import androidx.compose.material3.BadgedBox
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.ScrollableTabRow
import androidx.compose.material3.Surface
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRowDefaults
import androidx.compose.material3.TabRowDefaults.tabIndicatorOffset
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.R
import com.example.model.ChatCategory
import com.example.model.ChatMessage
import com.example.model.Contact
import com.example.model.MediaType
import com.example.model.UserProfile
import com.example.ui.components.DecoyMediaContainer
import com.example.ui.theme.AetherCoral
import com.example.ui.theme.AetherCoralContainer
import com.example.ui.theme.AetherCyan
import com.example.ui.theme.MessageBubbleIncoming
import com.example.ui.theme.MessageBubbleOutgoing
import com.example.ui.theme.NewsAlertBadge
import com.example.ui.theme.Slate100
import com.example.ui.theme.Slate200
import com.example.ui.theme.Slate400
import com.example.ui.theme.Slate600
import com.example.ui.theme.Slate700
import com.example.ui.theme.Slate800
import com.example.ui.theme.Slate900
import com.example.ui.theme.Slate950

@Composable
fun MessengerScreen(
    contacts: List<Contact>,
    activeContactId: String?,
    messages: List<ChatMessage>,
    selectedCategory: ChatCategory,
    searchQuery: String,
    userProfile: UserProfile,
    isRecordingVoice: Boolean,
    voiceSeconds: Int,
    onSelectContact: (String) -> Unit,
    onBackToList: () -> Unit,
    onCategorySelected: (ChatCategory) -> Unit,
    onSearchChanged: (String) -> Unit,
    onSendMessage: (String, MediaType, String, String, Int?) -> Unit,
    onToggleDecoyLock: (String) -> Unit,
    onOpenLightbox: (ChatMessage) -> Unit,
    onSwitchToAiDecoy: () -> Unit,
    onSimulateNewsAlert: () -> Unit,
    onOpenInviteModal: () -> Unit,
    onOpenProfileDialog: () -> Unit,
    onOpenSettings: () -> Unit,
    onStartVoiceRecord: () -> Unit,
    onFinishVoiceRecord: () -> Unit,
    onCancelVoiceRecord: () -> Unit,
    modifier: Modifier = Modifier
) {
    var inConversationRoom by remember { mutableStateOf(false) }

    val activeContact = contacts.find { it.id == activeContactId }

    if (inConversationRoom && activeContact != null) {
        BackHandler {
            inConversationRoom = false
            onBackToList()
        }

        ChatRoomView(
            contact = activeContact,
            messages = messages,
            isRecordingVoice = isRecordingVoice,
            voiceSeconds = voiceSeconds,
            onBack = {
                inConversationRoom = false
                onBackToList()
            },
            onSendMessage = onSendMessage,
            onToggleDecoyLock = onToggleDecoyLock,
            onOpenLightbox = onOpenLightbox,
            onSwitchToAiDecoy = onSwitchToAiDecoy,
            onStartVoiceRecord = onStartVoiceRecord,
            onFinishVoiceRecord = onFinishVoiceRecord,
            onCancelVoiceRecord = onCancelVoiceRecord
        )
    } else {
        ChatsListView(
            contacts = contacts,
            selectedCategory = selectedCategory,
            searchQuery = searchQuery,
            userProfile = userProfile,
            onSelectContact = { id ->
                onSelectContact(id)
                inConversationRoom = true
            },
            onCategorySelected = onCategorySelected,
            onSearchChanged = onSearchChanged,
            onSwitchToAiDecoy = onSwitchToAiDecoy,
            onSimulateNewsAlert = onSimulateNewsAlert,
            onOpenInviteModal = onOpenInviteModal,
            onOpenProfileDialog = onOpenProfileDialog,
            onOpenSettings = onOpenSettings,
            modifier = modifier
        )
    }
}

@Composable
private fun ChatsListView(
    contacts: List<Contact>,
    selectedCategory: ChatCategory,
    searchQuery: String,
    userProfile: UserProfile,
    onSelectContact: (String) -> Unit,
    onCategorySelected: (ChatCategory) -> Unit,
    onSearchChanged: (String) -> Unit,
    onSwitchToAiDecoy: () -> Unit,
    onSimulateNewsAlert: () -> Unit,
    onOpenInviteModal: () -> Unit,
    onOpenProfileDialog: () -> Unit,
    onOpenSettings: () -> Unit,
    modifier: Modifier = Modifier
) {
    val filteredContacts = contacts.filter { contact ->
        val matchesCategory = when (selectedCategory) {
            ChatCategory.ALL -> true
            else -> contact.category == selectedCategory
        }
        val matchesQuery = searchQuery.isBlank() ||
                contact.name.contains(searchQuery, ignoreCase = true) ||
                contact.username.contains(searchQuery, ignoreCase = true) ||
                contact.lastMessage.contains(searchQuery, ignoreCase = true)

        matchesCategory && matchesQuery
    }

    Scaffold(
        topBar = {
            Surface(
                color = Slate900,
                tonalElevation = 6.dp,
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 14.dp, vertical = 10.dp)
                ) {
                    // Header Bar with "Chats" + stealth panic decoy icon + simulated alert button
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            // User avatar trigger
                            Box(
                                modifier = Modifier
                                    .size(34.dp)
                                    .clip(CircleShape)
                                    .background(Color(userProfile.avatarColorHex))
                                    .clickable { onOpenProfileDialog() }
                                    .testTag("messenger_profile_avatar_button"),
                                contentAlignment = Alignment.Center
                            ) {
                                Text(
                                    text = userProfile.name.take(1).uppercase(),
                                    fontSize = 15.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = Color.White
                                )
                            }

                            Text(
                                text = "Chats",
                                fontSize = 20.sp,
                                fontWeight = FontWeight.Bold,
                                color = Color.White
                            )

                            // Encrypted status badge
                            Box(
                                modifier = Modifier
                                    .clip(RoundedCornerShape(6.dp))
                                    .background(AetherCoral.copy(alpha = 0.15f))
                                    .padding(horizontal = 6.dp, vertical = 2.dp)
                            ) {
                                Text(
                                    text = "E2EE",
                                    fontSize = 9.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = AetherCoral
                                )
                            }
                        }

                        // Right header actions
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(4.dp)
                        ) {
                            // Simulate News Alert Trigger
                            IconButton(
                                onClick = onSimulateNewsAlert,
                                modifier = Modifier.testTag("simulate_news_alert_button")
                            ) {
                                Icon(
                                    imageVector = Icons.Default.NotificationsActive,
                                    contentDescription = "Simulate News Camouflage Alert",
                                    tint = NewsAlertBadge,
                                    modifier = Modifier.size(20.dp)
                                )
                            }

                            // Invite Friend
                            IconButton(
                                onClick = onOpenInviteModal,
                                modifier = Modifier.testTag("messenger_invite_friend_button")
                            ) {
                                Icon(
                                    imageVector = Icons.Default.PersonAdd,
                                    contentDescription = "Invite Contact",
                                    tint = Slate200,
                                    modifier = Modifier.size(20.dp)
                                )
                            }

                            // Settings
                            IconButton(
                                onClick = onOpenSettings,
                                modifier = Modifier.testTag("messenger_settings_button")
                            ) {
                                Icon(
                                    imageVector = Icons.Default.Settings,
                                    contentDescription = "Settings",
                                    tint = Slate200,
                                    modifier = Modifier.size(20.dp)
                                )
                            }

                            // QUICK "AI DECOY" STEALTH ICON (Panic switch)
                            Surface(
                                modifier = Modifier
                                    .clip(RoundedCornerShape(10.dp))
                                    .background(AetherCyan.copy(alpha = 0.15f))
                                    .clickable { onSwitchToAiDecoy() }
                                    .testTag("messenger_panic_ai_decoy_button"),
                                color = AetherCyan.copy(alpha = 0.15f)
                            ) {
                                Row(
                                    modifier = Modifier.padding(horizontal = 8.dp, vertical = 6.dp),
                                    verticalAlignment = Alignment.CenterVertically,
                                    horizontalArrangement = Arrangement.spacedBy(4.dp)
                                ) {
                                    Icon(
                                        imageVector = Icons.Default.SmartToy,
                                        contentDescription = "Decoy AI Stealth",
                                        tint = AetherCyan,
                                        modifier = Modifier.size(16.dp)
                                    )
                                    Text(
                                        text = "AI Decoy",
                                        fontSize = 11.sp,
                                        fontWeight = FontWeight.Bold,
                                        color = AetherCyan
                                    )
                                }
                            }
                        }
                    }

                    Spacer(modifier = Modifier.height(10.dp))

                    // Real-time Search Bar
                    OutlinedTextField(
                        value = searchQuery,
                        onValueChange = onSearchChanged,
                        placeholder = { Text("Search encrypted chats & media...", fontSize = 13.sp, color = Slate400) },
                        leadingIcon = {
                            Icon(
                                imageVector = Icons.Default.Search,
                                contentDescription = null,
                                tint = Slate400,
                                modifier = Modifier.size(18.dp)
                            )
                        },
                        singleLine = true,
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(48.dp)
                            .testTag("messenger_search_input"),
                        shape = RoundedCornerShape(14.dp),
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedBorderColor = AetherCoral,
                            unfocusedBorderColor = Slate800,
                            focusedTextColor = Slate100,
                            unfocusedTextColor = Slate100,
                            focusedContainerColor = Slate800,
                            unfocusedContainerColor = Slate800
                        )
                    )

                    Spacer(modifier = Modifier.height(10.dp))

                    // Category Filter Tabs
                    ScrollableTabRow(
                        selectedTabIndex = ChatCategory.values().indexOf(selectedCategory),
                        edgePadding = 0.dp,
                        containerColor = Color.Transparent,
                        contentColor = AetherCoral,
                        divider = {},
                        indicator = { tabPositions ->
                            val currentTab = tabPositions[ChatCategory.values().indexOf(selectedCategory)]
                            TabRowDefaults.SecondaryIndicator(
                                Modifier.tabIndicatorOffset(currentTab),
                                color = AetherCoral,
                                height = 3.dp
                            )
                        }
                    ) {
                        ChatCategory.values().forEach { category ->
                            val isSelected = category == selectedCategory
                            Tab(
                                selected = isSelected,
                                onClick = { onCategorySelected(category) },
                                modifier = Modifier.testTag("category_tab_${category.name.lowercase()}"),
                                text = {
                                    Text(
                                        text = category.label,
                                        fontSize = 12.sp,
                                        fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Medium,
                                        color = if (isSelected) AetherCoral else Slate400
                                    )
                                }
                            )
                        }
                    }
                }
            }
        },
        containerColor = Slate950,
        modifier = modifier.fillMaxSize()
    ) { innerPadding ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding),
            contentPadding = PaddingValues(vertical = 8.dp)
        ) {
            items(filteredContacts, key = { it.id }) { contact ->
                ContactListItem(
                    contact = contact,
                    onClick = { onSelectContact(contact.id) }
                )
            }
        }
    }
}

@Composable
private fun ContactListItem(
    contact: Contact,
    onClick: () -> Unit
) {
    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() }
            .testTag("contact_item_${contact.username}"),
        color = Color.Transparent
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 14.dp, vertical = 11.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Avatar with Online dot
            Box(modifier = Modifier.size(48.dp)) {
                if (contact.avatarDrawableRes != null) {
                    Image(
                        painter = painterResource(id = contact.avatarDrawableRes),
                        contentDescription = contact.name,
                        modifier = Modifier
                            .fillMaxSize()
                            .clip(CircleShape),
                        contentScale = ContentScale.Crop
                    )
                } else {
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .clip(CircleShape)
                            .background(Color(contact.avatarColorHex)),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = contact.name.take(1).uppercase(),
                            fontSize = 18.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color.White
                        )
                    }
                }

                // Online indicator dot
                if (contact.isOnline) {
                    Box(
                        modifier = Modifier
                            .align(Alignment.BottomEnd)
                            .size(12.dp)
                            .clip(CircleShape)
                            .background(Color(0xFF10B981))
                            .border(2.dp, Slate950, CircleShape)
                    )
                }
            }

            Spacer(modifier = Modifier.width(12.dp))

            // Contact Info & Last Message
            Column(modifier = Modifier.weight(1f)) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = contact.name,
                        fontSize = 14.sp,
                        fontWeight = FontWeight.SemiBold,
                        color = Slate100,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                    Text(
                        text = contact.lastTimestamp,
                        fontSize = 11.sp,
                        color = if (contact.unreadCount > 0) AetherCoral else Slate400
                    )
                }

                Spacer(modifier = Modifier.height(3.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = contact.lastMessage,
                        fontSize = 12.sp,
                        color = if (contact.unreadCount > 0) Slate200 else Slate400,
                        fontWeight = if (contact.unreadCount > 0) FontWeight.Medium else FontWeight.Normal,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis,
                        modifier = Modifier.weight(1f)
                    )

                    if (contact.unreadCount > 0) {
                        Spacer(modifier = Modifier.width(8.dp))
                        Box(
                            modifier = Modifier
                                .size(18.dp)
                                .clip(CircleShape)
                                .background(AetherCoral),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(
                                text = contact.unreadCount.toString(),
                                fontSize = 10.sp,
                                fontWeight = FontWeight.Bold,
                                color = Color.White
                            )
                        }
                    }
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun ChatRoomView(
    contact: Contact,
    messages: List<ChatMessage>,
    isRecordingVoice: Boolean,
    voiceSeconds: Int,
    onBack: () -> Unit,
    onSendMessage: (String, MediaType, String, String, Int?) -> Unit,
    onToggleDecoyLock: (String) -> Unit,
    onOpenLightbox: (ChatMessage) -> Unit,
    onSwitchToAiDecoy: () -> Unit,
    onStartVoiceRecord: () -> Unit,
    onFinishVoiceRecord: () -> Unit,
    onCancelVoiceRecord: () -> Unit
) {
    var inputText by remember { mutableStateOf("") }
    var showAttachmentMenu by remember { mutableStateOf(false) }
    var showEmojiBar by remember { mutableStateOf(false) }
    val listState = rememberLazyListState()

    LaunchedEffect(messages.size) {
        if (messages.isNotEmpty()) {
            listState.animateScrollToItem(messages.size - 1)
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(10.dp)
                    ) {
                        // Avatar
                        Box(modifier = Modifier.size(38.dp)) {
                            if (contact.avatarDrawableRes != null) {
                                Image(
                                    painter = painterResource(id = contact.avatarDrawableRes),
                                    contentDescription = contact.name,
                                    modifier = Modifier
                                        .fillMaxSize()
                                        .clip(CircleShape),
                                    contentScale = ContentScale.Crop
                                )
                            } else {
                                Box(
                                    modifier = Modifier
                                        .fillMaxSize()
                                        .clip(CircleShape)
                                        .background(Color(contact.avatarColorHex)),
                                    contentAlignment = Alignment.Center
                                ) {
                                    Text(
                                        text = contact.name.take(1).uppercase(),
                                        fontSize = 15.sp,
                                        fontWeight = FontWeight.Bold,
                                        color = Color.White
                                    )
                                }
                            }
                            if (contact.isOnline) {
                                Box(
                                    modifier = Modifier
                                        .align(Alignment.BottomEnd)
                                        .size(10.dp)
                                        .clip(CircleShape)
                                        .background(Color(0xFF10B981))
                                        .border(1.5.dp, Slate900, CircleShape)
                                )
                            }
                        }

                        Column {
                            Text(
                                text = contact.name,
                                fontSize = 14.sp,
                                fontWeight = FontWeight.Bold,
                                color = Color.White
                            )
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Icon(
                                    imageVector = Icons.Default.Lock,
                                    contentDescription = null,
                                    tint = AetherCoral,
                                    modifier = Modifier.size(10.dp)
                                )
                                Spacer(modifier = Modifier.width(3.dp))
                                Text(
                                    text = contact.statusMessage,
                                    fontSize = 10.sp,
                                    color = Slate400
                                )
                            }
                        }
                    }
                },
                navigationIcon = {
                    IconButton(
                        onClick = onBack,
                        modifier = Modifier.testTag("chat_room_back_button")
                    ) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Back",
                            tint = Slate200
                        )
                    }
                },
                actions = {
                    // Audio / Video call simulation
                    IconButton(onClick = {}) {
                        Icon(Icons.Default.Call, contentDescription = "Voice Call", tint = Slate200, modifier = Modifier.size(20.dp))
                    }
                    IconButton(onClick = {}) {
                        Icon(Icons.Default.Videocam, contentDescription = "Video Call", tint = Slate200, modifier = Modifier.size(20.dp))
                    }
                    // Instant Panic Decoy Button
                    IconButton(
                        onClick = onSwitchToAiDecoy,
                        modifier = Modifier.testTag("chat_room_panic_button")
                    ) {
                        Icon(
                            imageVector = Icons.Default.VisibilityOff,
                            contentDescription = "Panic Hide to AI",
                            tint = AetherCoral,
                            modifier = Modifier.size(20.dp)
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Slate900
                )
            )
        },
        containerColor = Slate950
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .imePadding()
        ) {
            // Chat Bubble Stream
            LazyColumn(
                state = listState,
                modifier = Modifier
                    .weight(1f)
                    .fillMaxWidth()
                    .padding(horizontal = 12.dp),
                contentPadding = PaddingValues(vertical = 12.dp),
                verticalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                items(messages, key = { it.id }) { msg ->
                    ChatBubbleItem(
                        message = msg,
                        onToggleLock = { onToggleDecoyLock(msg.id) },
                        onOpenLightbox = { onOpenLightbox(msg) }
                    )
                }
            }

            // Quick Emoji Picker Bar (Toggleable)
            AnimatedVisibility(visible = showEmojiBar) {
                Surface(
                    color = Slate900,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    LazyRow(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 12.dp, vertical = 6.dp),
                        horizontalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        val emojis = listOf("🔒", "⚡", "👍", "🔥", "🤫", "🎯", "👁️", "🛡️", "🕵️")
                        items(emojis) { emoji ->
                            Text(
                                text = emoji,
                                fontSize = 22.sp,
                                modifier = Modifier
                                    .clickable {
                                        inputText += emoji
                                    }
                                    .padding(4.dp)
                            )
                        }
                    }
                }
            }

            // Interactive Message Bar
            Surface(
                color = Slate900,
                modifier = Modifier.fillMaxWidth()
            ) {
                Column {
                    if (isRecordingVoice) {
                        // Voice Recording In-Progress Banner
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(horizontal = 14.dp, vertical = 10.dp),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Box(
                                    modifier = Modifier
                                        .size(10.dp)
                                        .clip(CircleShape)
                                        .background(AetherCoral)
                                )
                                Spacer(modifier = Modifier.width(8.dp))
                                Text(
                                    text = "Recording Voice Note: ${String.format("%02d:%02d", voiceSeconds / 60, voiceSeconds % 60)}",
                                    color = AetherCoral,
                                    fontSize = 13.sp,
                                    fontWeight = FontWeight.SemiBold
                                )
                            }

                            Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                                IconButton(
                                    onClick = onCancelVoiceRecord,
                                    modifier = Modifier.size(34.dp)
                                ) {
                                    Icon(Icons.Default.Stop, contentDescription = "Cancel", tint = Slate400)
                                }
                                IconButton(
                                    onClick = onFinishVoiceRecord,
                                    modifier = Modifier
                                        .size(34.dp)
                                        .clip(CircleShape)
                                        .background(AetherCoral)
                                        .testTag("finish_voice_record_button")
                                ) {
                                    Icon(Icons.AutoMirrored.Filled.Send, contentDescription = "Send Audio", tint = Color.White, modifier = Modifier.size(16.dp))
                                }
                            }
                        }
                    } else {
                        // Standard Message Input Row
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(horizontal = 8.dp, vertical = 6.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            // Attachment trigger with Dropdown Menu
                            Box {
                                IconButton(
                                    onClick = { showAttachmentMenu = !showAttachmentMenu },
                                    modifier = Modifier.testTag("chat_attachment_button")
                                ) {
                                    Icon(
                                        imageVector = Icons.Default.AttachFile,
                                        contentDescription = "Attach",
                                        tint = if (showAttachmentMenu) AetherCoral else Slate400
                                    )
                                }

                                DropdownMenu(
                                    expanded = showAttachmentMenu,
                                    onDismissRequest = { showAttachmentMenu = false },
                                    modifier = Modifier
                                        .background(Slate900)
                                        .border(1.dp, Slate700, RoundedCornerShape(8.dp))
                                ) {
                                    DropdownMenuItem(
                                        text = { Text("Send Blueprint Photo (Decoy)", color = Slate200, fontSize = 12.sp) },
                                        leadingIcon = { Icon(Icons.Default.Image, contentDescription = null, tint = AetherCoral) },
                                        onClick = {
                                            showAttachmentMenu = false
                                            onSendMessage(
                                                "",
                                                MediaType.PHOTO,
                                                "Arctic Facility Schematic",
                                                "4.2 MB • High Resolution",
                                                R.drawable.secret_blueprint_photo_1791527187115
                                            )
                                        }
                                    )
                                    DropdownMenuItem(
                                        text = { Text("Send Encrypted Document", color = Slate200, fontSize = 12.sp) },
                                        leadingIcon = { Icon(Icons.Default.Description, contentDescription = null, tint = Color(0xFFEF4444)) },
                                        onClick = {
                                            showAttachmentMenu = false
                                            onSendMessage(
                                                "",
                                                MediaType.DOCUMENT,
                                                "Operation_Gambit_Brief.pdf",
                                                "1.8 MB • Verified SHA-256",
                                                null
                                            )
                                        }
                                    )
                                }
                            }

                            // Emoji trigger
                            IconButton(onClick = { showEmojiBar = !showEmojiBar }) {
                                Icon(
                                    imageVector = Icons.Default.EmojiEmotions,
                                    contentDescription = "Emojis",
                                    tint = if (showEmojiBar) AetherCoral else Slate400
                                )
                            }

                            // Text Field
                            OutlinedTextField(
                                value = inputText,
                                onValueChange = { inputText = it },
                                placeholder = { Text("Secret message...", fontSize = 13.sp, color = Slate400) },
                                modifier = Modifier
                                    .weight(1f)
                                    .testTag("chat_message_input"),
                                maxLines = 4,
                                shape = RoundedCornerShape(20.dp),
                                keyboardOptions = KeyboardOptions(imeAction = ImeAction.Send),
                                keyboardActions = KeyboardActions(
                                    onSend = {
                                        if (inputText.isNotBlank()) {
                                            onSendMessage(inputText, MediaType.NONE, "", "", null)
                                            inputText = ""
                                        }
                                    }
                                ),
                                colors = OutlinedTextFieldDefaults.colors(
                                    focusedBorderColor = AetherCoral,
                                    unfocusedBorderColor = Slate800,
                                    focusedTextColor = Color.White,
                                    unfocusedTextColor = Slate100,
                                    focusedContainerColor = Slate800,
                                    unfocusedContainerColor = Slate800
                                )
                            )

                            Spacer(modifier = Modifier.width(6.dp))

                            // Send or Mic Button
                            if (inputText.isNotBlank()) {
                                IconButton(
                                    onClick = {
                                        onSendMessage(inputText, MediaType.NONE, "", "", null)
                                        inputText = ""
                                    },
                                    modifier = Modifier
                                        .size(42.dp)
                                        .clip(CircleShape)
                                        .background(AetherCoral)
                                        .testTag("chat_send_button")
                                ) {
                                    Icon(
                                        imageVector = Icons.AutoMirrored.Filled.Send,
                                        contentDescription = "Send",
                                        tint = Color.White,
                                        modifier = Modifier.size(18.dp)
                                    )
                                }
                            } else {
                                IconButton(
                                    onClick = onStartVoiceRecord,
                                    modifier = Modifier
                                        .size(42.dp)
                                        .clip(CircleShape)
                                        .background(Slate800)
                                        .testTag("chat_voice_record_button")
                                ) {
                                    Icon(
                                        imageVector = Icons.Default.Mic,
                                        contentDescription = "Record Voice Note",
                                        tint = AetherCoral,
                                        modifier = Modifier.size(20.dp)
                                    )
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun ChatBubbleItem(
    message: ChatMessage,
    onToggleLock: () -> Unit,
    onOpenLightbox: () -> Unit
) {
    val isFromMe = message.isFromMe

    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = if (isFromMe) Arrangement.End else Arrangement.Start
    ) {
        Column(
            modifier = Modifier.widthIn(max = 300.dp),
            horizontalAlignment = if (isFromMe) Alignment.End else Alignment.Start
        ) {
            if (message.mediaType != MediaType.NONE) {
                // Media Decoy Box
                DecoyMediaContainer(
                    message = message,
                    onToggleLock = onToggleLock,
                    onOpenFullscreen = { onOpenLightbox() }
                )
            }

            if (message.text.isNotBlank()) {
                Surface(
                    modifier = Modifier
                        .clip(
                            RoundedCornerShape(
                                topStart = 16.dp,
                                topEnd = 16.dp,
                                bottomStart = if (isFromMe) 16.dp else 4.dp,
                                bottomEnd = if (isFromMe) 4.dp else 16.dp
                            )
                        )
                        .then(
                            if (!isFromMe) Modifier.border(1.dp, Slate700, RoundedCornerShape(16.dp))
                            else Modifier
                        ),
                    color = if (isFromMe) MessageBubbleOutgoing else MessageBubbleIncoming
                ) {
                    Text(
                        text = message.text,
                        fontSize = 13.sp,
                        color = if (isFromMe) Color.White else Slate100,
                        lineHeight = 17.sp,
                        modifier = Modifier.padding(horizontal = 12.dp, vertical = 9.dp)
                    )
                }
            }

            // Timestamp and read receipt
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(4.dp),
                modifier = Modifier.padding(top = 2.dp, start = 4.dp, end = 4.dp)
            ) {
                Text(
                    text = message.timestamp,
                    fontSize = 9.sp,
                    color = Slate400
                )
                if (isFromMe) {
                    Icon(
                        imageVector = Icons.Default.DoneAll,
                        contentDescription = "Read",
                        tint = AetherCyan,
                        modifier = Modifier.size(12.dp)
                    )
                }
            }
        }
    }
}
