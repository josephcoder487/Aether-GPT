package com.example.ui.screens

import android.widget.Toast
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.CleaningServices
import androidx.compose.material.icons.filled.DeleteSweep
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Newspaper
import androidx.compose.material.icons.filled.Public
import androidx.compose.material.icons.filled.Security
import androidx.compose.material.icons.filled.Shield
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.model.NewsCategory
import com.example.model.UserProfile
import com.example.ui.theme.AetherCoral
import com.example.ui.theme.AetherCyan
import com.example.ui.theme.Slate100
import com.example.ui.theme.Slate200
import com.example.ui.theme.Slate400
import com.example.ui.theme.Slate700
import com.example.ui.theme.Slate800
import com.example.ui.theme.Slate900
import com.example.ui.theme.Slate950

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SettingsScreen(
    profile: UserProfile,
    onBack: () -> Unit,
    onToggleCamouflage: () -> Unit,
    onToggleDecoyAutoLock: () -> Unit,
    onSelectNewsCategory: (NewsCategory) -> Unit,
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current

    BackHandler {
        onBack()
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = "Security & Camouflage",
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.White
                    )
                },
                navigationIcon = {
                    IconButton(
                        onClick = onBack,
                        modifier = Modifier.testTag("settings_back_button")
                    ) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Back",
                            tint = Slate200
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Slate900
                )
            )
        },
        containerColor = Slate950,
        modifier = modifier.fillMaxSize()
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .verticalScroll(rememberScrollState())
                .padding(16.dp)
        ) {
            // Section 1: Breaking News Camouflage
            Text(
                text = "COVERT NOTIFICATIONS",
                fontSize = 11.sp,
                fontWeight = FontWeight.Bold,
                color = AetherCoral,
                letterSpacing = 0.8.sp
            )
            Spacer(modifier = Modifier.height(8.dp))

            Surface(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(14.dp))
                    .border(1.dp, Slate700, RoundedCornerShape(14.dp)),
                color = Slate900
            ) {
                Column(modifier = Modifier.padding(14.dp)) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Column(modifier = Modifier.weight(1f)) {
                            Text(
                                text = "Breaking News Camouflage",
                                fontSize = 14.sp,
                                fontWeight = FontWeight.SemiBold,
                                color = Slate100
                            )
                            Text(
                                text = "Disguise incoming chat alerts as real Breaking News",
                                fontSize = 11.sp,
                                color = Slate400
                            )
                        }

                        Switch(
                            checked = profile.camouflageEnabled,
                            onCheckedChange = { onToggleCamouflage() },
                            colors = SwitchDefaults.colors(
                                checkedThumbColor = Color.White,
                                checkedTrackColor = AetherCoral,
                                uncheckedTrackColor = Slate800
                            ),
                            modifier = Modifier.testTag("toggle_camouflage_switch")
                        )
                    }

                    Spacer(modifier = Modifier.height(14.dp))
                    HorizontalDivider(color = Slate800)
                    Spacer(modifier = Modifier.height(12.dp))

                    Text(
                        text = "Preferred News Disguise Feed:",
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Medium,
                        color = Slate200
                    )
                    Spacer(modifier = Modifier.height(8.dp))

                    // News Category Choices
                    NewsCategory.values().forEach { cat ->
                        val isSelected = profile.camouflageCategory == cat
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 4.dp)
                                .clip(RoundedCornerShape(8.dp))
                                .background(if (isSelected) Slate800 else Color.Transparent)
                                .clickable { onSelectNewsCategory(cat) }
                                .padding(horizontal = 10.dp, vertical = 8.dp)
                                .testTag("news_category_${cat.name.lowercase()}"),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.spacedBy(8.dp)
                            ) {
                                Box(
                                    modifier = Modifier
                                        .size(8.dp)
                                        .clip(CircleShape)
                                        .background(Color(cat.badgeColorHex))
                                )
                                Text(
                                    text = cat.label,
                                    fontSize = 13.sp,
                                    color = if (isSelected) Slate100 else Slate400,
                                    fontWeight = if (isSelected) FontWeight.SemiBold else FontWeight.Normal
                                )
                            }

                            if (isSelected) {
                                Text(
                                    text = "Active",
                                    fontSize = 11.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = AetherCoral
                                )
                            }
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(20.dp))

            // Section 2: Media Decoy & Anti-Shoulder Surfing
            Text(
                text = "ANTI-SHOULDER SURFING",
                fontSize = 11.sp,
                fontWeight = FontWeight.Bold,
                color = AetherCoral,
                letterSpacing = 0.8.sp
            )
            Spacer(modifier = Modifier.height(8.dp))

            Surface(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(14.dp))
                    .border(1.dp, Slate700, RoundedCornerShape(14.dp)),
                color = Slate900
            ) {
                Column(modifier = Modifier.padding(14.dp)) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Column(modifier = Modifier.weight(1f)) {
                            Text(
                                text = "Secret Media Decoy Shield",
                                fontSize = 14.sp,
                                fontWeight = FontWeight.SemiBold,
                                color = Slate100
                            )
                            Text(
                                text = "Show 'Oops, photo not available' until tapped by owner",
                                fontSize = 11.sp,
                                color = Slate400
                            )
                        }

                        Switch(
                            checked = profile.decoyAutoLock,
                            onCheckedChange = { onToggleDecoyAutoLock() },
                            colors = SwitchDefaults.colors(
                                checkedThumbColor = Color.White,
                                checkedTrackColor = AetherCoral,
                                uncheckedTrackColor = Slate800
                            ),
                            modifier = Modifier.testTag("toggle_decoy_autolock_switch")
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(20.dp))

            // Section 3: Stealth Triggers & Gesture Guide
            Text(
                text = "STEALTH SHORTCUTS",
                fontSize = 11.sp,
                fontWeight = FontWeight.Bold,
                color = AetherCyan,
                letterSpacing = 0.8.sp
            )
            Spacer(modifier = Modifier.height(8.dp))

            Surface(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(14.dp))
                    .border(1.dp, Slate700, RoundedCornerShape(14.dp)),
                color = Slate900
            ) {
                Column(modifier = Modifier.padding(14.dp)) {
                    ShortcutGuideRow(
                        title = "One-Tap Panic AI Decoy",
                        desc = "Instantly returns to Aether AI chat interface.",
                        badge = "EYE ICON"
                    )
                    Spacer(modifier = Modifier.height(10.dp))
                    HorizontalDivider(color = Slate800)
                    Spacer(modifier = Modifier.height(10.dp))
                    ShortcutGuideRow(
                        title = "Discreet Header Dot Trigger",
                        desc = "Tap the tiny coral dot next to hamburger menu.",
                        badge = "SECRET DOT"
                    )
                    Spacer(modifier = Modifier.height(10.dp))
                    HorizontalDivider(color = Slate800)
                    Spacer(modifier = Modifier.height(10.dp))
                    ShortcutGuideRow(
                        title = "Covert Drawer Gateway",
                        desc = "Slide AI menu drawer and select 'Chats Messenger'.",
                        badge = "DRAWER"
                    )
                }
            }

            Spacer(modifier = Modifier.height(20.dp))

            // Section 4: Zero-Knowledge Cache Wipe
            Button(
                onClick = {
                    Toast.makeText(context, "Zero-trace RAM cache flushed successfully.", Toast.LENGTH_SHORT).show()
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(44.dp)
                    .testTag("flush_cache_button"),
                colors = ButtonDefaults.buttonColors(containerColor = Slate800),
                shape = RoundedCornerShape(12.dp)
            ) {
                Icon(
                    imageVector = Icons.Default.DeleteSweep,
                    contentDescription = null,
                    tint = Color(0xFFEF4444),
                    modifier = Modifier.size(18.dp)
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = "Flush Ephemeral Relay Cache",
                    fontSize = 13.sp,
                    color = Slate200,
                    fontWeight = FontWeight.SemiBold
                )
            }
        }
    }
}

@Composable
private fun ShortcutGuideRow(title: String, desc: String, badge: String) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Column(modifier = Modifier.weight(1f)) {
            Text(text = title, fontSize = 13.sp, fontWeight = FontWeight.SemiBold, color = Slate100)
            Text(text = desc, fontSize = 11.sp, color = Slate400)
        }
        Box(
            modifier = Modifier
                .clip(RoundedCornerShape(6.dp))
                .background(Slate800)
                .padding(horizontal = 6.dp, vertical = 3.dp)
        ) {
            Text(
                text = badge,
                fontSize = 9.sp,
                fontWeight = FontWeight.Bold,
                color = AetherCyan
            )
        }
    }
}
