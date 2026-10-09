package com.example

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.model.AppMode
import com.example.ui.components.ApkInstallDialog
import com.example.ui.components.CamouflageBanner
import com.example.ui.components.InviteModal
import com.example.ui.components.MediaLightboxDialog
import com.example.ui.components.PhoneFrameContainer
import com.example.ui.components.ProfileDialog
import com.example.ui.screens.AiChatbotScreen
import com.example.ui.screens.MessengerScreen
import com.example.ui.screens.SettingsScreen
import com.example.ui.theme.MyApplicationTheme
import com.example.ui.theme.Slate950
import com.example.viewmodel.AetherViewModel

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MyApplicationTheme {
                AetherApp()
            }
        }
    }
}

@Composable
fun AetherApp(
    viewModel: AetherViewModel = viewModel()
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    Surface(
        modifier = Modifier.fillMaxSize(),
        color = Slate950
    ) {
        PhoneFrameContainer(
            isFrameEnabled = uiState.isDeviceFrameEnabled,
            modifier = Modifier.fillMaxSize()
        ) {
            Box(modifier = Modifier.fillMaxSize()) {
                // Main Content Screen Switching
                when {
                    uiState.showSettingsScreen -> {
                        SettingsScreen(
                            profile = uiState.userProfile,
                            onBack = { viewModel.setShowSettingsScreen(false) },
                            onToggleCamouflage = { viewModel.toggleCamouflageEnabled() },
                            onToggleDecoyAutoLock = { viewModel.toggleDecoyAutoLock() },
                            onSelectNewsCategory = { viewModel.setCamouflageCategory(it) }
                        )
                    }

                    uiState.appMode == AppMode.AI_CHATBOT -> {
                        AiChatbotScreen(
                            aiMessages = uiState.aiMessages,
                            isThinking = uiState.isAiThinking,
                            isWidgetCardMode = uiState.isWidgetCardMode,
                            onSendPrompt = { viewModel.sendAiPrompt(it) },
                            onResetChat = { viewModel.resetAiChat() },
                            onToggleWidgetMode = { viewModel.toggleWidgetCardMode() },
                            onSwitchToMessenger = { viewModel.switchToMessenger() },
                            onToggleDeviceFrame = { viewModel.toggleDeviceFrame() },
                            isFrameEnabled = uiState.isDeviceFrameEnabled,
                            onOpenApkModal = { viewModel.setShowApkInstallModal(true) },
                            onOpenSettings = { viewModel.setShowSettingsScreen(true) }
                        )
                    }

                    uiState.appMode == AppMode.MESSENGER -> {
                        val activeMessages = uiState.messagesByContact[uiState.activeContactId] ?: emptyList()
                        MessengerScreen(
                            contacts = uiState.contacts,
                            activeContactId = uiState.activeContactId,
                            messages = activeMessages,
                            selectedCategory = uiState.selectedCategory,
                            searchQuery = uiState.searchQuery,
                            userProfile = uiState.userProfile,
                            isRecordingVoice = uiState.isRecordingVoice,
                            voiceSeconds = uiState.voiceRecordingSeconds,
                            onSelectContact = { viewModel.selectContact(it) },
                            onBackToList = { /* already handled inside screen */ },
                            onCategorySelected = { viewModel.setSelectedCategory(it) },
                            onSearchChanged = { viewModel.setSearchQuery(it) },
                            onSendMessage = { text, mediaType, title, subtitle, resId ->
                                viewModel.sendChatMessage(
                                    text = text,
                                    mediaType = mediaType,
                                    mediaTitle = title,
                                    mediaSubtitle = subtitle,
                                    drawableRes = resId
                                )
                            },
                            onToggleDecoyLock = { viewModel.toggleDecoyLock(it) },
                            onOpenLightbox = { viewModel.openLightbox(it) },
                            onSwitchToAiDecoy = { viewModel.switchToAiDecoy() },
                            onSimulateNewsAlert = { viewModel.simulateCamouflageNewsAlert() },
                            onOpenInviteModal = { viewModel.setShowInviteModal(true) },
                            onOpenProfileDialog = { viewModel.setShowProfileDialog(true) },
                            onOpenSettings = { viewModel.setShowSettingsScreen(true) },
                            onStartVoiceRecord = { viewModel.startVoiceRecording() },
                            onFinishVoiceRecord = { viewModel.finishVoiceRecording() },
                            onCancelVoiceRecord = { viewModel.cancelVoiceRecording() }
                        )
                    }
                }

                // Overlay Camouflage News Alert Banner at top
                CamouflageBanner(
                    alert = uiState.activeCamouflageAlert,
                    onTapAlert = { viewModel.tapCamouflageAlert(it) },
                    onDismiss = { viewModel.dismissCamouflageAlert() },
                    modifier = Modifier
                        .align(Alignment.TopCenter)
                        .statusBarsPadding()
                        .padding(top = 4.dp)
                )

                // Dialogs & Modals
                if (uiState.showInviteModal) {
                    InviteModal(
                        inviteCode = uiState.userProfile.inviteCode,
                        onDismiss = { viewModel.setShowInviteModal(false) },
                        onSimulateAccept = { viewModel.simulateFriendAcceptInvite() }
                    )
                }

                if (uiState.showProfileDialog) {
                    ProfileDialog(
                        profile = uiState.userProfile,
                        onDismiss = { viewModel.setShowProfileDialog(false) },
                        onSave = { name, username, email, colorHex ->
                            viewModel.updateProfile(name, username, email, colorHex)
                        }
                    )
                }

                if (uiState.showApkInstallModal) {
                    ApkInstallDialog(
                        onDismiss = { viewModel.setShowApkInstallModal(false) }
                    )
                }

                if (uiState.activeLightboxMessage != null) {
                    MediaLightboxDialog(
                        message = uiState.activeLightboxMessage!!,
                        onDismiss = { viewModel.openLightbox(null) },
                        onRelockDecoy = {
                            viewModel.toggleDecoyLock(uiState.activeLightboxMessage!!.id)
                        }
                    )
                }
            }
        }
    }
}
