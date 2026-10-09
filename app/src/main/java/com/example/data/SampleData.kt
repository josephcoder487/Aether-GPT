package com.example.data

import com.example.R
import com.example.model.AiMessage
import com.example.model.CamouflageAlert
import com.example.model.ChatCategory
import com.example.model.ChatMessage
import com.example.model.Contact
import com.example.model.MediaType
import com.example.model.NewsCategory

object SampleData {

    val initialContacts = listOf(
        Contact(
            id = "c_elena",
            name = "Elena Vance",
            username = "elena_v",
            avatarDrawableRes = R.drawable.avatar_elena_1791527208779,
            avatarColorHex = 0xFFF0643B,
            isOnline = true,
            statusMessage = "Encrypted end-to-end",
            category = ChatCategory.PERSONAL,
            lastMessage = "Sector 7B schematic is attached. Check the decoy box.",
            lastTimestamp = "11:42 AM",
            unreadCount = 2
        ),
        Contact(
            id = "c_marcus",
            name = "Marcus Thorne",
            username = "m_thorne",
            avatarDrawableRes = null,
            avatarColorHex = 0xFF0284C7,
            isOnline = true,
            statusMessage = "At safehouse alpha",
            category = ChatCategory.WORK,
            lastMessage = "Packet delivery confirmed through secondary relay.",
            lastTimestamp = "10:15 AM",
            unreadCount = 0
        ),
        Contact(
            id = "c_sora",
            name = "Sora Takahashi",
            username = "sora_t",
            avatarDrawableRes = null,
            avatarColorHex = 0xFF9333EA,
            isOnline = false,
            statusMessage = "Away until 14:00",
            category = ChatCategory.DESIGN,
            lastMessage = "Updated the dark stealth palette prototypes.",
            lastTimestamp = "Yesterday",
            unreadCount = 0
        ),
        Contact(
            id = "c_cipher",
            name = "Cipher Node 4",
            username = "zero_trace",
            avatarDrawableRes = null,
            avatarColorHex = 0xFF10B981,
            isOnline = true,
            statusMessage = "Zero logs active",
            category = ChatCategory.FAVOURITES,
            lastMessage = "Disguise protocol verified. News camouflage armed.",
            lastTimestamp = "Yesterday",
            unreadCount = 0
        ),
        Contact(
            id = "c_julian",
            name = "Dr. Julian Croft",
            username = "julian_c",
            avatarDrawableRes = null,
            avatarColorHex = 0xFFD97706,
            isOnline = false,
            statusMessage = "Offline",
            category = ChatCategory.WORK,
            lastMessage = "The cryptographic proofs are compiling cleanly.",
            lastTimestamp = "Oct 7",
            unreadCount = 0
        ),
        Contact(
            id = "c_maya",
            name = "Maya Lin",
            username = "maya_l",
            avatarDrawableRes = null,
            avatarColorHex = 0xFFEC4899,
            isOnline = true,
            statusMessage = "Reviewing design assets",
            category = ChatCategory.DESIGN,
            lastMessage = "Loved the coral accent styling on the new messenger header!",
            lastTimestamp = "Oct 6",
            unreadCount = 0
        )
    )

    fun getInitialMessagesForElena(): List<ChatMessage> = listOf(
        ChatMessage(
            id = "m1",
            senderId = "c_elena",
            text = "Alex, are you on a secure device right now?",
            timestamp = "11:38 AM",
            isFromMe = false
        ),
        ChatMessage(
            id = "m2",
            senderId = "user",
            text = "Yes, Aether Gambit disguise is active. What do you have?",
            timestamp = "11:39 AM",
            isFromMe = true
        ),
        ChatMessage(
            id = "m3",
            senderId = "c_elena",
            text = "I intercepted the blueprints for the arctic facility server array.",
            timestamp = "11:40 AM",
            isFromMe = false
        ),
        ChatMessage(
            id = "m4",
            senderId = "c_elena",
            text = "Sending it under our covert decoy shield now.",
            timestamp = "11:41 AM",
            isFromMe = false,
            mediaType = MediaType.PHOTO,
            mediaTitle = "Cybernetic Facility Blueprint",
            mediaSubtitle = "4.2 MB • High Resolution",
            mediaDrawableRes = R.drawable.secret_blueprint_photo_1791527187115,
            isDecoyLocked = true
        ),
        ChatMessage(
            id = "m5",
            senderId = "c_elena",
            text = "Also recorded a quick audio note explaining the primary access tunnel codes.",
            timestamp = "11:42 AM",
            isFromMe = false,
            mediaType = MediaType.AUDIO,
            mediaTitle = "Voice Dispatch #072",
            mediaSubtitle = "0:34 • Encrypted Voice Stream",
            isDecoyLocked = true
        ),
        ChatMessage(
            id = "m6",
            senderId = "c_elena",
            text = "Remember: if anyone steps behind you, let the decoy container protect it.",
            timestamp = "11:42 AM",
            isFromMe = false
        )
    )

    val promptSuggestions = listOf(
        "What is an ai chatbot?",
        "Key benefits",
        "How does it work?",
        "Explain quantum computing",
        "Draft an email",
        "Security features"
    )

    val initialAiMessages = listOf(
        AiMessage(
            id = "ai_1",
            text = "What is an ai chatbot?",
            isUser = true,
            timestamp = "8:54 PM"
        ),
        AiMessage(
            id = "ai_2",
            text = "An AI chatbot is a software program that simulates human conversation with an end user. They can provide customer service, recommend products, generate leads, and improve the customer experience. Would you like to know about their benefits?",
            isUser = false,
            timestamp = "8:55 PM"
        ),
        AiMessage(
            id = "ai_3",
            text = "Yes",
            isUser = true,
            timestamp = "8:55 PM"
        ),
        AiMessage(
            id = "ai_4",
            text = "AI chatbots can enhance customer service by providing 24/7 support, reducing wait times, and personalizing interactions. They can handle multiple queries simultaneously, improving efficiency and customer satisfaction. Would you like to know more?",
            isUser = false,
            timestamp = "8:56 PM"
        )
    )

    val newsCamouflagePool = listOf(
        CamouflageAlert(
            id = "news_1",
            headline = "Semiconductor Foundry Announces Next-Gen Optical Interconnects",
            source = "REUTERS TECH",
            category = NewsCategory.TECH,
            targetContactId = "c_elena",
            senderName = "Elena Vance",
            hiddenMessageText = "Meeting moved to 14:00 at the safe drop.",
            timestamp = "Just now"
        ),
        CamouflageAlert(
            id = "news_2",
            headline = "Global Renewable Energy Coalition Finalizes Trans-Pacific Pact",
            source = "WORLD DISPATCH",
            category = NewsCategory.WORLD,
            targetContactId = "c_marcus",
            senderName = "Marcus Thorne",
            hiddenMessageText = "Node 7 has rebooted cleanly. Check the logs.",
            timestamp = "Just now"
        ),
        CamouflageAlert(
            id = "news_3",
            headline = "Market Indices Rally 2.4% Following Central Bank Liquidity Report",
            source = "FINANCIAL WIRE",
            category = NewsCategory.BUSINESS,
            targetContactId = "c_cipher",
            senderName = "Cipher Node 4",
            hiddenMessageText = "Encrypted key rotation complete.",
            timestamp = "Just now"
        ),
        CamouflageAlert(
            id = "news_4",
            headline = "Championship Finals Go Down to the Wire in Dramatic Double Overtime",
            source = "SPORTS CENTRAL",
            category = NewsCategory.SPORTS,
            targetContactId = "c_sora",
            senderName = "Sora Takahashi",
            hiddenMessageText = "Sent you the revised stealth mockup.",
            timestamp = "Just now"
        )
    )
}
