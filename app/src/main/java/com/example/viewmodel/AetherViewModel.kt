package com.example.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.R
import com.example.data.SampleData
import com.example.model.AiMessage
import com.example.model.AppMode
import com.example.model.CamouflageAlert
import com.example.model.ChatCategory
import com.example.model.ChatMessage
import com.example.model.Contact
import com.example.model.MediaType
import com.example.model.NewsCategory
import com.example.model.UserProfile
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import java.util.UUID

data class AetherUiState(
    val appMode: AppMode = AppMode.AI_CHATBOT,
    val contacts: List<Contact> = SampleData.initialContacts,
    val activeContactId: String = "c_elena",
    val messagesByContact: Map<String, List<ChatMessage>> = mapOf(
        "c_elena" to SampleData.getInitialMessagesForElena()
    ),
    val aiMessages: List<AiMessage> = SampleData.initialAiMessages,
    val isAiThinking: Boolean = false,
    val selectedCategory: ChatCategory = ChatCategory.ALL,
    val searchQuery: String = "",
    val userProfile: UserProfile = UserProfile(),
    val activeCamouflageAlert: CamouflageAlert? = null,
    val isDeviceFrameEnabled: Boolean = false,
    val showInviteModal: Boolean = false,
    val showProfileDialog: Boolean = false,
    val showApkInstallModal: Boolean = false,
    val showSettingsScreen: Boolean = false,
    val activeLightboxMessage: ChatMessage? = null,
    val isRecordingVoice: Boolean = false,
    val voiceRecordingSeconds: Int = 0,
    val isWidgetCardMode: Boolean = true
)

class AetherViewModel : ViewModel() {

    private val _uiState = MutableStateFlow(AetherUiState())
    val uiState: StateFlow<AetherUiState> = _uiState.asStateFlow()

    private fun getCurrentTime(): String {
        return SimpleDateFormat("h:mm a", Locale.getDefault()).format(Date())
    }

    fun switchToMessenger() {
        _uiState.update { it.copy(appMode = AppMode.MESSENGER, showSettingsScreen = false) }
    }

    fun switchToAiDecoy() {
        _uiState.update { it.copy(appMode = AppMode.AI_CHATBOT, showSettingsScreen = false) }
    }

    fun toggleAppMode() {
        _uiState.update {
            if (it.appMode == AppMode.AI_CHATBOT) {
                it.copy(appMode = AppMode.MESSENGER, showSettingsScreen = false)
            } else {
                it.copy(appMode = AppMode.AI_CHATBOT, showSettingsScreen = false)
            }
        }
    }

    fun selectContact(contactId: String) {
        _uiState.update { state ->
            val updatedContacts = state.contacts.map {
                if (it.id == contactId) it.copy(unreadCount = 0) else it
            }
            state.copy(
                activeContactId = contactId,
                contacts = updatedContacts
            )
        }
    }

    fun setSearchQuery(query: String) {
        _uiState.update { it.copy(searchQuery = query) }
    }

    fun setSelectedCategory(category: ChatCategory) {
        _uiState.update { it.copy(selectedCategory = category) }
    }

    fun toggleDeviceFrame() {
        _uiState.update { it.copy(isDeviceFrameEnabled = !it.isDeviceFrameEnabled) }
    }

    fun toggleWidgetCardMode() {
        _uiState.update { it.copy(isWidgetCardMode = !it.isWidgetCardMode) }
    }

    fun resetAiChat() {
        _uiState.update { it.copy(aiMessages = SampleData.initialAiMessages, isAiThinking = false) }
    }

    fun setShowInviteModal(show: Boolean) {
        _uiState.update { it.copy(showInviteModal = show) }
    }

    fun setShowProfileDialog(show: Boolean) {
        _uiState.update { it.copy(showProfileDialog = show) }
    }

    fun setShowApkInstallModal(show: Boolean) {
        _uiState.update { it.copy(showApkInstallModal = show) }
    }

    fun setShowSettingsScreen(show: Boolean) {
        _uiState.update { it.copy(showSettingsScreen = show) }
    }

    fun openLightbox(message: ChatMessage?) {
        _uiState.update { it.copy(activeLightboxMessage = message) }
    }

    fun toggleDecoyLock(messageId: String) {
        val contactId = _uiState.value.activeContactId
        _uiState.update { state ->
            val currentList = state.messagesByContact[contactId] ?: emptyList()
            val updatedList = currentList.map {
                if (it.id == messageId) it.copy(isDecoyLocked = !it.isDecoyLocked) else it
            }
            state.copy(
                messagesByContact = state.messagesByContact + (contactId to updatedList)
            )
        }
    }

    fun sendChatMessage(
        text: String,
        mediaType: MediaType = MediaType.NONE,
        mediaTitle: String = "",
        mediaSubtitle: String = "",
        drawableRes: Int? = null
    ) {
        if (text.isBlank() && mediaType == MediaType.NONE) return

        val contactId = _uiState.value.activeContactId
        val timeNow = getCurrentTime()
        val newMessage = ChatMessage(
            id = UUID.randomUUID().toString(),
            senderId = "user",
            text = text,
            timestamp = timeNow,
            isFromMe = true,
            mediaType = mediaType,
            mediaTitle = mediaTitle,
            mediaSubtitle = mediaSubtitle,
            mediaDrawableRes = drawableRes,
            isDecoyLocked = mediaType != MediaType.NONE,
            isRead = true
        )

        _uiState.update { state ->
            val currentList = state.messagesByContact[contactId] ?: emptyList()
            val updatedList = currentList + newMessage
            val updatedContacts = state.contacts.map {
                if (it.id == contactId) {
                    it.copy(
                        lastMessage = if (mediaType != MediaType.NONE) "[$mediaType attachment]" else text,
                        lastTimestamp = timeNow
                    )
                } else it
            }
            state.copy(
                messagesByContact = state.messagesByContact + (contactId to updatedList),
                contacts = updatedContacts
            )
        }

        // Automatic simulated reply from contact after a brief pause
        viewModelScope.launch {
            delay(1600)
            val replyText = generateSimulatedContactReply(contactId, text)
            val replyMessage = ChatMessage(
                id = UUID.randomUUID().toString(),
                senderId = contactId,
                text = replyText,
                timestamp = getCurrentTime(),
                isFromMe = false,
                isRead = true
            )
            _uiState.update { state ->
                val currentList = state.messagesByContact[contactId] ?: emptyList()
                val updatedList = currentList + replyMessage
                val updatedContacts = state.contacts.map {
                    if (it.id == contactId) {
                        it.copy(
                            lastMessage = replyText,
                            lastTimestamp = getCurrentTime()
                        )
                    } else it
                }
                state.copy(
                    messagesByContact = state.messagesByContact + (contactId to updatedList),
                    contacts = updatedContacts
                )
            }
        }
    }

    private fun generateSimulatedContactReply(contactId: String, userText: String): String {
        return when (contactId) {
            "c_elena" -> "Understood Alex. The decoy protocol is holding. Let me know when you review the sector blueprint."
            "c_marcus" -> "Copy that. Route cleared through node 4. Will stand by for next sync."
            "c_sora" -> "Looks sharp! I'm testing the high-contrast variant now."
            "c_cipher" -> "Signal received. Transmission scrubbed."
            else -> "Message received. Encryption handshake verified."
        }
    }

    fun startVoiceRecording() {
        _uiState.update { it.copy(isRecordingVoice = true, voiceRecordingSeconds = 0) }
        viewModelScope.launch {
            while (_uiState.value.isRecordingVoice) {
                delay(1000)
                _uiState.update { it.copy(voiceRecordingSeconds = it.voiceRecordingSeconds + 1) }
            }
        }
    }

    fun cancelVoiceRecording() {
        _uiState.update { it.copy(isRecordingVoice = false, voiceRecordingSeconds = 0) }
    }

    fun finishVoiceRecording() {
        val duration = _uiState.value.voiceRecordingSeconds
        _uiState.update { it.copy(isRecordingVoice = false, voiceRecordingSeconds = 0) }
        val formattedDuration = String.format(Locale.getDefault(), "%02d:%02d", duration / 60, duration % 60)
        sendChatMessage(
            text = "",
            mediaType = MediaType.AUDIO,
            mediaTitle = "Voice Memo ($formattedDuration)",
            mediaSubtitle = "Simulated Audio Note • Decoy Protected"
        )
    }

    fun sendAiPrompt(promptText: String) {
        if (promptText.isBlank()) return
        val userMsg = AiMessage(
            id = UUID.randomUUID().toString(),
            text = promptText,
            isUser = true,
            timestamp = getCurrentTime()
        )
        _uiState.update {
            it.copy(
                aiMessages = it.aiMessages + userMsg,
                isAiThinking = true
            )
        }

        viewModelScope.launch {
            delay(1200)
            val aiResponse = generateIntelligentAiResponse(promptText)
            val aiMsg = AiMessage(
                id = UUID.randomUUID().toString(),
                text = aiResponse,
                isUser = false,
                timestamp = getCurrentTime()
            )
            _uiState.update {
                it.copy(
                    aiMessages = it.aiMessages + aiMsg,
                    isAiThinking = false
                )
            }
        }
    }

    private fun generateIntelligentAiResponse(prompt: String): String {
        val lower = prompt.lowercase()
        return when {
            lower.contains("what is an ai chatbot") || (lower.contains("chatbot") && lower.contains("what")) -> {
                "An AI chatbot is a software program that simulates human conversation with an end user. They can provide customer service, recommend products, generate leads, and improve the customer experience. Would you like to know about their benefits?"
            }
            lower == "yes" || lower == "yes please" || lower.contains("benefits") || lower.contains("benefit") -> {
                "AI chatbots can enhance customer service by providing 24/7 support, reducing wait times, and personalizing interactions. They can handle multiple queries simultaneously, improving efficiency and customer satisfaction. Would you like to know more?"
            }
            lower.contains("how does it work") -> {
                "AI chatbots work by processing user inputs using Natural Language Processing (NLP) and Large Language Models (LLMs). They parse intent, extract key entities, retrieve contextual knowledge, and generate human-like coherent responses in milliseconds."
            }
            lower.contains("quantum") -> {
                "### Quantum Computing & Superposition\n\nQuantum computers leverage **qubits** which exploit quantum mechanical phenomena such as **superposition** and **entanglement**.\n\n* **Superposition:** A qubit can exist in a linear combination of states $|0\\rangle$ and $|1\\rangle$.\n* **Entanglement:** Correlation between multiple qubits enables exponential state space processing ($2^n$).\n* **Applications:** Molecular modeling, Shor's algorithm for prime factorization, and quantum cryptography protocols."
            }
            lower.contains("brainstorm") -> {
                "### Strategic Brainstorming: 5 Tactical Ideas\n\n1. **Dynamic Ephemeral Nodes:** Self-terminating chat sessions with scheduled zero-knowledge memory flushes.\n2. **Acoustic Watermarking:** Hiding steganographic keys in ambient low-frequency audio tracks.\n3. **Decoy Error Injection:** Camouflaging genuine payload transfers as standard HTTP 404 or 503 error logs.\n4. **Contextual Shuffling:** Reordering chat bubbles randomly when the proximity sensor detects a face nearby.\n5. **Hardware Micro-Attestation:** Biometric validation combined with accelerometer tilt angles."
            }
            lower.contains("email") -> {
                "**Subject:** Update on Q4 Infrastructure Modernization\n\nHi Team,\n\nI wanted to share a brief update on our core architecture deliverables. The primary migration milestones for the security and relay pipelines are on track for completion by Friday.\n\nPlease review the attached specifications and flag any dependency conflicts before tomorrow's standup.\n\nBest regards,\nAlex"
            }
            lower.contains("security") || lower.contains("audit") -> {
                "### Security Audit Overview\n\n* **Encryption Layer:** AES-256-GCM symmetric session keys with Curve25519 key exchange.\n* **Threat Vector Analysis:** Shoulder-surfing mitigated via decoy media shielding.\n* **Notification Leakage:** System-level notification sniffing nullified by external Breaking News camouflage heuristics.\n* **Zero Footprint:** Ephemeral memory cache purged upon app backgrounding."
            }
            lower.contains("kotlin") || lower.contains("code") -> {
                "```kotlin\n// Kotlin Coroutines Flow Producer\nsuspend fun observeSecureStream(): Flow<DataState> = flow {\n    emit(DataState.Connecting)\n    val packet = secureRelay.receivePayload()\n    emit(DataState.Decrypted(packet))\n}.flowOn(Dispatchers.IO)\n```\nThis ensures safe asynchronous consumption without blocking the Android UI thread."
            }
            else -> {
                "I have analyzed your request regarding \"$prompt\".\n\nAs Aether AI (v4.5 Flash), I synthesize contextual information using multi-turn reasoning. I can assist in drafting technical briefs, reviewing architecture schemas, or analyzing complex workflows. Let me know if you would like me to expand on any specific section!"
            }
        }
    }

    fun simulateCamouflageNewsAlert() {
        val pool = SampleData.newsCamouflagePool
        val category = _uiState.value.userProfile.camouflageCategory
        val matchedAlert = pool.find { it.category == category } ?: pool.first()
        val currentAlert = matchedAlert.copy(
            id = UUID.randomUUID().toString(),
            timestamp = "Just now"
        )

        _uiState.update { state ->
            // Also append a secret message into that contact's chat
            val contactId = currentAlert.targetContactId
            val currentList = state.messagesByContact[contactId] ?: emptyList()
            val covertMessage = ChatMessage(
                id = UUID.randomUUID().toString(),
                senderId = contactId,
                text = currentAlert.hiddenMessageText,
                timestamp = getCurrentTime(),
                isFromMe = false,
                isRead = false
            )
            val updatedContacts = state.contacts.map {
                if (it.id == contactId) {
                    it.copy(
                        lastMessage = currentAlert.hiddenMessageText,
                        lastTimestamp = getCurrentTime(),
                        unreadCount = it.unreadCount + 1
                    )
                } else it
            }
            state.copy(
                activeCamouflageAlert = currentAlert,
                messagesByContact = state.messagesByContact + (contactId to (currentList + covertMessage)),
                contacts = updatedContacts
            )
        }

        // Auto dismiss after 7 seconds if not tapped
        viewModelScope.launch {
            delay(7000)
            if (_uiState.value.activeCamouflageAlert?.id == currentAlert.id) {
                dismissCamouflageAlert()
            }
        }
    }

    fun dismissCamouflageAlert() {
        _uiState.update { it.copy(activeCamouflageAlert = null) }
    }

    fun tapCamouflageAlert(alert: CamouflageAlert) {
        _uiState.update { state ->
            val updatedContacts = state.contacts.map {
                if (it.id == alert.targetContactId) it.copy(unreadCount = 0) else it
            }
            state.copy(
                activeCamouflageAlert = null,
                appMode = AppMode.MESSENGER,
                activeContactId = alert.targetContactId,
                contacts = updatedContacts,
                showSettingsScreen = false
            )
        }
    }

    fun updateProfile(name: String, username: String, email: String, colorHex: Long) {
        _uiState.update {
            it.copy(
                userProfile = it.userProfile.copy(
                    name = name.ifBlank { it.userProfile.name },
                    username = username.ifBlank { it.userProfile.username },
                    email = email.ifBlank { it.userProfile.email },
                    avatarColorHex = colorHex
                )
            )
        }
    }

    fun setCamouflageCategory(category: NewsCategory) {
        _uiState.update {
            it.copy(userProfile = it.userProfile.copy(camouflageCategory = category))
        }
    }

    fun toggleCamouflageEnabled() {
        _uiState.update {
            it.copy(userProfile = it.userProfile.copy(camouflageEnabled = !it.userProfile.camouflageEnabled))
        }
    }

    fun toggleDecoyAutoLock() {
        _uiState.update {
            it.copy(userProfile = it.userProfile.copy(decoyAutoLock = !it.userProfile.decoyAutoLock))
        }
    }

    fun simulateFriendAcceptInvite() {
        val newContact = Contact(
            id = "c_rayner",
            name = "Agent Rayner",
            username = "rayner_7x",
            avatarDrawableRes = null,
            avatarColorHex = 0xFF14B8A6,
            isOnline = true,
            statusMessage = "Joined via Invite Token",
            category = ChatCategory.PERSONAL,
            lastMessage = "Handshake verified. I'm connected to your private node.",
            lastTimestamp = getCurrentTime(),
            unreadCount = 1
        )
        val initialMsg = ChatMessage(
            id = UUID.randomUUID().toString(),
            senderId = "c_rayner",
            text = "Handshake verified. I'm connected to your private node.",
            timestamp = getCurrentTime(),
            isFromMe = false,
            isRead = false
        )

        _uiState.update { state ->
            val alreadyExists = state.contacts.any { it.id == newContact.id }
            if (alreadyExists) state else {
                state.copy(
                    contacts = listOf(newContact) + state.contacts,
                    messagesByContact = state.messagesByContact + (newContact.id to listOf(initialMsg)),
                    showInviteModal = false
                )
            }
        }

        // Trigger camouflage news alert for the new connection!
        val welcomeAlert = CamouflageAlert(
            id = UUID.randomUUID().toString(),
            headline = "Global Satellite Constellation Achieves Zero-Latency Uplink",
            source = "TECH DISPATCH",
            category = NewsCategory.TECH,
            targetContactId = "c_rayner",
            senderName = "Agent Rayner",
            hiddenMessageText = "Handshake verified. Connected to your private node.",
            timestamp = "Just now"
        )
        _uiState.update { it.copy(activeCamouflageAlert = welcomeAlert) }
    }
}
