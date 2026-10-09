package com.example.ui.screens

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
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
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
import androidx.compose.material.icons.automirrored.filled.Send
import androidx.compose.material.icons.filled.Android
import androidx.compose.material.icons.filled.AutoAwesome
import androidx.compose.material.icons.filled.Chat
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.CloseFullscreen
import androidx.compose.material.icons.filled.Headphones
import androidx.compose.material.icons.filled.History
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.filled.OpenInFull
import androidx.compose.material.icons.filled.PhoneAndroid
import androidx.compose.material.icons.filled.Psychology
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material.icons.filled.Smartphone
import androidx.compose.material.icons.filled.Tune
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.NavigationDrawerItem
import androidx.compose.material3.NavigationDrawerItemDefaults
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.R
import com.example.data.SampleData
import com.example.model.AiMessage
import com.example.ui.theme.AetherCoral
import com.example.ui.theme.BotBubbleLight
import com.example.ui.theme.LilacPastel
import com.example.ui.theme.LilacSoft
import com.example.ui.theme.OutfitFontFamily
import com.example.ui.theme.PlusJakartaSansFontFamily
import com.example.ui.theme.PurpleUserBubble
import com.example.ui.theme.Slate200
import com.example.ui.theme.Slate400
import com.example.ui.theme.Slate600
import com.example.ui.theme.Slate700
import com.example.ui.theme.Slate800
import com.example.ui.theme.Slate900
import com.example.ui.theme.Slate950
import com.example.ui.theme.TextBotDark
import com.example.ui.theme.ThemePurpleDarker
import com.example.ui.theme.ThemePurpleDeepBg
import com.example.ui.theme.ThemePurpleSurface
import kotlinx.coroutines.launch

@Composable
fun AiChatbotScreen(
    aiMessages: List<AiMessage>,
    isThinking: Boolean,
    isWidgetCardMode: Boolean,
    onSendPrompt: (String) -> Unit,
    onResetChat: () -> Unit,
    onToggleWidgetMode: () -> Unit,
    onSwitchToMessenger: () -> Unit,
    onToggleDeviceFrame: () -> Unit,
    isFrameEnabled: Boolean,
    onOpenApkModal: () -> Unit,
    onOpenSettings: () -> Unit,
    modifier: Modifier = Modifier
) {
    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    val coroutineScope = rememberCoroutineScope()
    var inputText by remember { mutableStateOf("") }
    val listState = rememberLazyListState()

    LaunchedEffect(aiMessages.size, isThinking) {
        if (aiMessages.isNotEmpty()) {
            listState.animateScrollToItem(aiMessages.size - 1)
        }
    }

    ModalNavigationDrawer(
        drawerState = drawerState,
        drawerContent = {
            ModalDrawerSheet(
                drawerContainerColor = ThemePurpleDarker,
                modifier = Modifier
                    .width(300.dp)
                    .fillMaxHeight()
                    .testTag("ai_drawer_sheet")
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(16.dp)
                ) {
                    // Drawer Header with Robot mascot from Image 2
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        Image(
                            painter = painterResource(id = R.drawable.avatar_bot_robot_1791528959072),
                            contentDescription = "Robot Mascot",
                            modifier = Modifier
                                .size(44.dp)
                                .clip(CircleShape)
                                .border(1.5.dp, LilacPastel, CircleShape),
                            contentScale = ContentScale.Crop
                        )

                        Column {
                            Text(
                                text = "Aether Bot",
                                fontFamily = OutfitFontFamily,
                                fontSize = 16.sp,
                                fontWeight = FontWeight.Bold,
                                color = Color.White
                            )
                            Text(
                                text = "AI Assistant • Online",
                                fontFamily = PlusJakartaSansFontFamily,
                                fontSize = 11.sp,
                                color = LilacPastel
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(18.dp))
                    HorizontalDivider(color = Slate800)
                    Spacer(modifier = Modifier.height(12.dp))

                    Text(
                        text = "CAPABILITIES",
                        fontFamily = OutfitFontFamily,
                        fontSize = 11.sp,
                        fontWeight = FontWeight.Bold,
                        color = LilacPastel.copy(alpha = 0.7f),
                        letterSpacing = 0.8.sp,
                        modifier = Modifier.padding(horizontal = 12.dp)
                    )

                    Spacer(modifier = Modifier.height(8.dp))

                    NavigationDrawerItem(
                        icon = { Icon(Icons.Default.Psychology, contentDescription = null, tint = LilacSoft) },
                        label = { Text("Customer Assistant", color = Color.White, fontSize = 13.sp, fontFamily = PlusJakartaSansFontFamily) },
                        selected = true,
                        onClick = { coroutineScope.launch { drawerState.close() } },
                        colors = NavigationDrawerItemDefaults.colors(
                            selectedContainerColor = ThemePurpleSurface
                        )
                    )

                    NavigationDrawerItem(
                        icon = { Icon(Icons.Default.Refresh, contentDescription = null, tint = Slate400) },
                        label = { Text("Reset Conversation", color = Slate200, fontSize = 13.sp, fontFamily = PlusJakartaSansFontFamily) },
                        selected = false,
                        onClick = {
                            coroutineScope.launch {
                                drawerState.close()
                                onResetChat()
                            }
                        }
                    )

                    NavigationDrawerItem(
                        icon = { Icon(Icons.Default.Tune, contentDescription = null, tint = Slate400) },
                        label = { Text("Response Settings", color = Slate200, fontSize = 13.sp, fontFamily = PlusJakartaSansFontFamily) },
                        selected = false,
                        onClick = { coroutineScope.launch { drawerState.close() } }
                    )

                    Spacer(modifier = Modifier.weight(1f))

                    // COVERT GATEWAY ITEM IN DRAWER (Maintains Gambit Architecture)
                    HorizontalDivider(color = Slate800)
                    Spacer(modifier = Modifier.height(10.dp))

                    Surface(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clip(RoundedCornerShape(12.dp))
                            .border(1.dp, AetherCoral.copy(alpha = 0.4f), RoundedCornerShape(12.dp))
                            .clickable {
                                coroutineScope.launch {
                                    drawerState.close()
                                    onSwitchToMessenger()
                                }
                            }
                            .testTag("drawer_covert_chats_gateway"),
                        color = ThemePurpleSurface
                    ) {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(12.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Box(
                                modifier = Modifier
                                    .size(36.dp)
                                    .clip(CircleShape)
                                    .background(AetherCoral.copy(alpha = 0.2f)),
                                contentAlignment = Alignment.Center
                            ) {
                                Icon(
                                    imageVector = Icons.Default.Chat,
                                    contentDescription = null,
                                    tint = AetherCoral,
                                    modifier = Modifier.size(20.dp)
                                )
                            }

                            Spacer(modifier = Modifier.width(10.dp))

                            Column(modifier = Modifier.weight(1f)) {
                                Row(verticalAlignment = Alignment.CenterVertically) {
                                    Text(
                                        text = "Chats Messenger",
                                        fontFamily = OutfitFontFamily,
                                        fontSize = 13.sp,
                                        fontWeight = FontWeight.Bold,
                                        color = Color.White
                                    )
                                    Spacer(modifier = Modifier.width(6.dp))
                                    Box(
                                        modifier = Modifier
                                            .size(6.dp)
                                            .clip(CircleShape)
                                            .background(Color(0xFF10B981))
                                    )
                                }
                                Text(
                                    text = "256-bit AES • Covert Layer",
                                    fontFamily = PlusJakartaSansFontFamily,
                                    fontSize = 10.sp,
                                    color = AetherCoral
                                )
                            }

                            Icon(
                                imageVector = Icons.Default.Lock,
                                contentDescription = null,
                                tint = AetherCoral,
                                modifier = Modifier.size(16.dp)
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(8.dp))
                }
            }
        }
    ) {
        // Deep purple outer background from Reference Image 2
        Box(
            modifier = modifier
                .fillMaxSize()
                .background(ThemePurpleDeepBg),
            contentAlignment = Alignment.Center
        ) {
            // Main Chatbot UI Card: Styled like Reference Image 1 with Image 2's theme
            Surface(
                modifier = if (isWidgetCardMode) {
                    Modifier
                        .fillMaxWidth(0.94f)
                        .fillMaxHeight(0.92f)
                        .shadow(elevation = 16.dp, shape = RoundedCornerShape(24.dp))
                        .clip(RoundedCornerShape(24.dp))
                        .border(1.dp, LilacPastel.copy(alpha = 0.3f), RoundedCornerShape(24.dp))
                } else {
                    Modifier.fillMaxSize()
                },
                color = Color.White,
                shape = if (isWidgetCardMode) RoundedCornerShape(24.dp) else RoundedCornerShape(0.dp)
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .imePadding()
                ) {
                    // Header matching Reference Image 1:
                    // Icon + "Finch from Sendbird" / "Aether AI" + Refresh, Expand, Close
                    Surface(
                        color = Color.White,
                        modifier = Modifier
                            .fillMaxWidth()
                            .border(
                                width = 1.dp,
                                color = Color(0xFFF0EBF8),
                                shape = RoundedCornerShape(topStart = 24.dp, topEnd = 24.dp)
                            )
                    ) {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(horizontal = 14.dp, vertical = 12.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            // Left Bot Icon from Image 2
                            Box(
                                modifier = Modifier
                                    .size(34.dp)
                                    .clip(CircleShape)
                                    .clickable { coroutineScope.launch { drawerState.open() } }
                                    .testTag("chatbot_avatar_button"),
                                contentAlignment = Alignment.Center
                            ) {
                                Image(
                                    painter = painterResource(id = R.drawable.avatar_bot_robot_1791528959072),
                                    contentDescription = "Bot Avatar",
                                    modifier = Modifier
                                        .fillMaxSize()
                                        .clip(CircleShape),
                                    contentScale = ContentScale.Crop
                                )

                                // Covert discreet dot indicator
                                Box(
                                    modifier = Modifier
                                        .align(Alignment.TopEnd)
                                        .size(7.dp)
                                        .clip(CircleShape)
                                        .background(AetherCoral)
                                        .clickable { onSwitchToMessenger() }
                                        .testTag("header_discreet_dot_trigger")
                                )
                            }

                            Spacer(modifier = Modifier.width(10.dp))

                            // Bot Title
                            Column(modifier = Modifier.weight(1f)) {
                                Text(
                                    text = "Aether from Sendbird",
                                    fontFamily = OutfitFontFamily,
                                    fontSize = 15.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = Color(0xFF1E132D)
                                )
                            }

                            // 3 Header Action Icons matching Image 1:
                            // 1. Refresh / Reset Conversation
                            IconButton(
                                onClick = onResetChat,
                                modifier = Modifier
                                    .size(32.dp)
                                    .testTag("chatbot_reset_button")
                            ) {
                                Icon(
                                    imageVector = Icons.Default.Refresh,
                                    contentDescription = "Restart chat",
                                    tint = Color(0xFF6B5885),
                                    modifier = Modifier.size(19.dp)
                                )
                            }

                            Spacer(modifier = Modifier.width(4.dp))

                            // 2. Expand / Fullscreen Toggle
                            IconButton(
                                onClick = onToggleWidgetMode,
                                modifier = Modifier
                                    .size(32.dp)
                                    .testTag("chatbot_expand_button")
                            ) {
                                Icon(
                                    imageVector = if (isWidgetCardMode) Icons.Default.OpenInFull else Icons.Default.CloseFullscreen,
                                    contentDescription = "Toggle full screen",
                                    tint = Color(0xFF6B5885),
                                    modifier = Modifier.size(17.dp)
                                )
                            }

                            Spacer(modifier = Modifier.width(4.dp))

                            // 3. Menu / Close / Covert trigger
                            IconButton(
                                onClick = { coroutineScope.launch { drawerState.open() } },
                                modifier = Modifier
                                    .size(32.dp)
                                    .testTag("chatbot_menu_button")
                            ) {
                                Icon(
                                    imageVector = Icons.Default.Close,
                                    contentDescription = "Menu / Close",
                                    tint = Color(0xFF6B5885),
                                    modifier = Modifier.size(18.dp)
                                )
                            }
                        }
                    }

                    HorizontalDivider(color = Color(0xFFF3EDF8), thickness = 1.dp)

                    // Conversation Message Stream
                    LazyColumn(
                        state = listState,
                        modifier = Modifier
                            .weight(1f)
                            .fillMaxWidth()
                            .padding(horizontal = 14.dp),
                        contentPadding = PaddingValues(vertical = 12.dp),
                        verticalArrangement = Arrangement.spacedBy(14.dp)
                    ) {
                        items(aiMessages, key = { it.id }) { msg ->
                            if (msg.isUser) {
                                UserMessageItem(message = msg)
                            } else {
                                BotMessageItem(message = msg)
                            }
                        }

                        if (isThinking) {
                            item {
                                BotThinkingItem()
                            }
                        }
                    }

                    // Quick Suggestion Chips (like the prompt chips in Image 1)
                    LazyRow(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 12.dp, vertical = 6.dp),
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        items(SampleData.promptSuggestions) { prompt ->
                            Surface(
                                modifier = Modifier
                                    .clip(RoundedCornerShape(16.dp))
                                    .border(1.dp, Color(0xFFDDD2ED), RoundedCornerShape(16.dp))
                                    .clickable { onSendPrompt(prompt) }
                                    .testTag("prompt_chip_${prompt.take(6).lowercase()}"),
                                color = Color(0xFFFAF7FD)
                            ) {
                                Text(
                                    text = prompt,
                                    fontFamily = PlusJakartaSansFontFamily,
                                    fontSize = 12.sp,
                                    color = Color(0xFF533F70),
                                    fontWeight = FontWeight.Medium,
                                    modifier = Modifier.padding(horizontal = 12.dp, vertical = 6.dp)
                                )
                            }
                        }
                    }

                    HorizontalDivider(color = Color(0xFFF3EDF8), thickness = 1.dp)

                    // Bottom Message Input Bar matching Reference Image 1
                    Surface(
                        color = Color.White,
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(horizontal = 12.dp, vertical = 8.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            OutlinedTextField(
                                value = inputText,
                                onValueChange = { inputText = it },
                                placeholder = {
                                    Text(
                                        "Type a message...",
                                        fontFamily = PlusJakartaSansFontFamily,
                                        fontSize = 13.sp,
                                        color = Color(0xFF9D8DB5)
                                    )
                                },
                                modifier = Modifier
                                    .weight(1f)
                                    .testTag("ai_prompt_text_field"),
                                maxLines = 3,
                                shape = RoundedCornerShape(22.dp),
                                keyboardOptions = KeyboardOptions(imeAction = ImeAction.Send),
                                keyboardActions = KeyboardActions(
                                    onSend = {
                                        if (inputText.isNotBlank()) {
                                            onSendPrompt(inputText)
                                            inputText = ""
                                        }
                                    }
                                ),
                                colors = OutlinedTextFieldDefaults.colors(
                                    focusedBorderColor = PurpleUserBubble,
                                    unfocusedBorderColor = Color(0xFFE6DDF2),
                                    focusedTextColor = Color(0xFF1E132D),
                                    unfocusedTextColor = Color(0xFF1E132D),
                                    focusedContainerColor = Color(0xFFFAF7FE),
                                    unfocusedContainerColor = Color(0xFFFAF7FE)
                                )
                            )

                            Spacer(modifier = Modifier.width(8.dp))

                            // Send Button: Vibrant Purple Circle with send arrow
                            IconButton(
                                onClick = {
                                    if (inputText.isNotBlank()) {
                                        onSendPrompt(inputText)
                                        inputText = ""
                                    }
                                },
                                enabled = inputText.isNotBlank(),
                                modifier = Modifier
                                    .size(42.dp)
                                    .clip(CircleShape)
                                    .background(if (inputText.isNotBlank()) PurpleUserBubble else Color(0xFFE4DBEF))
                                    .testTag("ai_send_prompt_button")
                            ) {
                                Icon(
                                    imageVector = Icons.AutoMirrored.Filled.Send,
                                    contentDescription = "Send",
                                    tint = if (inputText.isNotBlank()) Color.White else Color(0xFF9B8CB1),
                                    modifier = Modifier.size(18.dp)
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}

// User Message Item: Vibrant purple pill bubble on the right, matching Image 1
@Composable
private fun UserMessageItem(message: AiMessage) {
    Column(
        modifier = Modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.End
    ) {
        // Timestamp above or beside
        Text(
            text = message.timestamp,
            fontFamily = PlusJakartaSansFontFamily,
            fontSize = 10.sp,
            color = Color(0xFF9D8EB4),
            modifier = Modifier.padding(bottom = 3.dp, end = 4.dp)
        )

        // Vibrant Purple Pill Bubble
        Surface(
            modifier = Modifier
                .widthIn(max = 280.dp)
                .clip(RoundedCornerShape(20.dp))
                .shadow(elevation = 2.dp, shape = RoundedCornerShape(20.dp)),
            color = PurpleUserBubble
        ) {
            Text(
                text = message.text,
                fontFamily = PlusJakartaSansFontFamily,
                fontSize = 13.sp,
                fontWeight = FontWeight.Medium,
                color = Color.White,
                modifier = Modifier.padding(horizontal = 16.dp, vertical = 9.dp)
            )
        }
    }
}

// Bot Message Item: Small bot avatar + "Bot name" + light rounded card + timestamp, matching Image 1
@Composable
private fun BotMessageItem(message: AiMessage) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.Start,
        verticalAlignment = Alignment.Top
    ) {
        // Bot avatar beside the message
        Image(
            painter = painterResource(id = R.drawable.avatar_bot_robot_1791528959072),
            contentDescription = "Bot Avatar",
            modifier = Modifier
                .padding(top = 16.dp)
                .size(26.dp)
                .clip(CircleShape)
                .border(1.dp, LilacPastel, CircleShape),
            contentScale = ContentScale.Crop
        )

        Spacer(modifier = Modifier.width(8.dp))

        Column(modifier = Modifier.widthIn(max = 290.dp)) {
            // "Bot name" label above the card
            Text(
                text = "Bot name",
                fontFamily = OutfitFontFamily,
                fontSize = 11.sp,
                fontWeight = FontWeight.Medium,
                color = Color(0xFF86729F),
                modifier = Modifier.padding(start = 4.dp, bottom = 4.dp)
            )

            // Light gray-lavender speech card
            Surface(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(
                        RoundedCornerShape(
                            topStart = 4.dp,
                            topEnd = 16.dp,
                            bottomStart = 16.dp,
                            bottomEnd = 16.dp
                        )
                    )
                    .border(
                        1.dp,
                        Color(0xFFE9E2F3),
                        RoundedCornerShape(
                            topStart = 4.dp,
                            topEnd = 16.dp,
                            bottomStart = 16.dp,
                            bottomEnd = 16.dp
                        )
                    ),
                color = Color(0xFFF3EFF8)
            ) {
                Text(
                    text = message.text,
                    fontFamily = PlusJakartaSansFontFamily,
                    fontSize = 13.sp,
                    color = Color(0xFF231636),
                    lineHeight = 18.sp,
                    modifier = Modifier.padding(horizontal = 14.dp, vertical = 10.dp)
                )
            }

            // Timestamp below the card
            Text(
                text = message.timestamp,
                fontFamily = PlusJakartaSansFontFamily,
                fontSize = 10.sp,
                color = Color(0xFF9D8EB4),
                modifier = Modifier
                    .align(Alignment.End)
                    .padding(top = 3.dp, end = 4.dp)
            )
        }
    }
}

@Composable
private fun BotThinkingItem() {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.Start,
        verticalAlignment = Alignment.Top
    ) {
        Image(
            painter = painterResource(id = R.drawable.avatar_bot_robot_1791528959072),
            contentDescription = "Bot Avatar",
            modifier = Modifier
                .padding(top = 8.dp)
                .size(26.dp)
                .clip(CircleShape),
            contentScale = ContentScale.Crop
        )

        Spacer(modifier = Modifier.width(8.dp))

        Surface(
            modifier = Modifier.clip(RoundedCornerShape(14.dp)),
            color = Color(0xFFF3EFF8)
        ) {
            Row(
                modifier = Modifier.padding(horizontal = 14.dp, vertical = 8.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                CircularProgressIndicator(
                    modifier = Modifier.size(14.dp),
                    color = PurpleUserBubble,
                    strokeWidth = 2.dp
                )
                Text(
                    text = "Aether Bot is typing...",
                    fontFamily = PlusJakartaSansFontFamily,
                    fontSize = 12.sp,
                    color = Color(0xFF6B5885)
                )
            }
        }
    }
}
