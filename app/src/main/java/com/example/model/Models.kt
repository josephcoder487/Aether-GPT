package com.example.model

enum class AppMode {
    AI_CHATBOT,
    MESSENGER
}

enum class ChatCategory(val label: String) {
    ALL("All"),
    PERSONAL("Personal"),
    DESIGN("Design"),
    WORK("Work"),
    FAVOURITES("Favourites")
}

enum class NewsCategory(val label: String, val badgeColorHex: Long) {
    TECH("TECH WIRE", 0xFF38BDF8),
    WORLD("GLOBAL DISPATCH", 0xFFEF4444),
    BUSINESS("MARKETS & CAPITAL", 0xFF2563EB),
    SPORTS("ARENA LIVE", 0xFF10B981)
}

enum class MediaType {
    NONE,
    PHOTO,
    VIDEO,
    AUDIO,
    DOCUMENT
}

data class ChatMessage(
    val id: String,
    val senderId: String,
    val text: String,
    val timestamp: String,
    val isFromMe: Boolean,
    val mediaType: MediaType = MediaType.NONE,
    val mediaTitle: String = "",
    val mediaSubtitle: String = "",
    val mediaDrawableRes: Int? = null,
    val isDecoyLocked: Boolean = true,
    val isRead: Boolean = true
)

data class Contact(
    val id: String,
    val name: String,
    val username: String,
    val avatarDrawableRes: Int? = null,
    val avatarColorHex: Long = 0xFFF0643B,
    val isOnline: Boolean = true,
    val statusMessage: String = "Active now",
    val category: ChatCategory = ChatCategory.PERSONAL,
    val lastMessage: String = "",
    val lastTimestamp: String = "Just now",
    val unreadCount: Int = 0
)

data class AiMessage(
    val id: String,
    val text: String,
    val isUser: Boolean,
    val timestamp: String,
    val isStreaming: Boolean = false
)

data class CamouflageAlert(
    val id: String,
    val headline: String,
    val source: String,
    val category: NewsCategory,
    val targetContactId: String,
    val senderName: String,
    val hiddenMessageText: String,
    val timestamp: String = "Just now"
)

data class UserProfile(
    val name: String = "Alex Vance",
    val username: String = "alex_vance",
    val email: String = "alex.vance@aether.internal",
    val avatarColorHex: Long = 0xFFF0643B,
    val inviteCode: String = "aether_79x_gambit",
    val camouflageCategory: NewsCategory = NewsCategory.TECH,
    val camouflageEnabled: Boolean = true,
    val decoyAutoLock: Boolean = true
)
