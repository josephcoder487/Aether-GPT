package com.example.ui.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Description
import androidx.compose.material.icons.filled.ErrorOutline
import androidx.compose.material.icons.filled.FileDownload
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.LockOpen
import androidx.compose.material.icons.filled.Mic
import androidx.compose.material.icons.filled.Pause
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material.icons.filled.WarningAmber
import androidx.compose.material.icons.filled.ZoomIn
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.model.ChatMessage
import com.example.model.MediaType
import com.example.ui.theme.AetherCoral
import com.example.ui.theme.AetherCyan
import com.example.ui.theme.DecoyBoxBg
import com.example.ui.theme.DecoyWarningAmber
import com.example.ui.theme.Slate200
import com.example.ui.theme.Slate400
import com.example.ui.theme.Slate600
import com.example.ui.theme.Slate700
import com.example.ui.theme.Slate800
import com.example.ui.theme.Slate900

@Composable
fun DecoyMediaContainer(
    message: ChatMessage,
    onToggleLock: () -> Unit,
    onOpenFullscreen: (ChatMessage) -> Unit,
    modifier: Modifier = Modifier
) {
    val mediaTypeName = when (message.mediaType) {
        MediaType.PHOTO -> "photo"
        MediaType.VIDEO -> "video"
        MediaType.AUDIO -> "audio"
        MediaType.DOCUMENT -> "document"
        MediaType.NONE -> "media"
    }

    if (message.isDecoyLocked) {
        // Anti-shoulder-surfing Decoy State
        Surface(
            modifier = modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(12.dp))
                .background(DecoyBoxBg)
                .border(1.dp, Slate700.copy(alpha = 0.5f), RoundedCornerShape(12.dp))
                .testTag("decoy_box_locked"),
            color = DecoyBoxBg
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(12.dp)
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Box(
                        modifier = Modifier
                            .size(34.dp)
                            .clip(CircleShape)
                            .background(DecoyWarningAmber.copy(alpha = 0.15f))
                            .clickable { onToggleLock() }
                            .testTag("decoy_secret_badge_trigger"),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            imageVector = Icons.Default.WarningAmber,
                            contentDescription = "Decoy Warning",
                            tint = DecoyWarningAmber,
                            modifier = Modifier.size(20.dp)
                        )
                    }

                    Spacer(modifier = Modifier.width(10.dp))

                    Column(modifier = Modifier.weight(1f)) {
                        Text(
                            text = "Oops, this $mediaTypeName is no longer available",
                            fontSize = 13.sp,
                            fontWeight = FontWeight.SemiBold,
                            color = Slate200
                        )
                        Text(
                            text = "Media expired or removed by sender",
                            fontSize = 11.sp,
                            color = Slate400
                        )
                    }

                    // Hidden keyword "Details" which reveals real media
                    Text(
                        text = "Details",
                        fontSize = 11.sp,
                        fontWeight = FontWeight.Medium,
                        color = Slate400,
                        modifier = Modifier
                            .clip(RoundedCornerShape(6.dp))
                            .background(Slate800)
                            .clickable { onToggleLock() }
                            .padding(horizontal = 8.dp, vertical = 4.dp)
                            .testTag("decoy_details_unlock_button")
                    )
                }
            }
        }
    } else {
        // Revealed Genuine Media Container
        Surface(
            modifier = modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(14.dp))
                .background(Slate900)
                .border(1.dp, AetherCoral.copy(alpha = 0.5f), RoundedCornerShape(14.dp))
                .testTag("decoy_box_unlocked"),
            color = Slate900
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp)
            ) {
                // Header with Re-lock shield action
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 6.dp, vertical = 4.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(6.dp)
                    ) {
                        Box(
                            modifier = Modifier
                                .size(8.dp)
                                .clip(CircleShape)
                                .background(AetherCoral)
                        )
                        Text(
                            text = "DECOY UNLOCKED",
                            fontSize = 10.sp,
                            fontWeight = FontWeight.Bold,
                            color = AetherCoral,
                            letterSpacing = 0.8.sp
                        )
                    }

                    // Quick conceal button to hide back behind decoy
                    Row(
                        modifier = Modifier
                            .clip(RoundedCornerShape(6.dp))
                            .background(Slate800)
                            .clickable { onToggleLock() }
                            .padding(horizontal = 8.dp, vertical = 3.dp)
                            .testTag("decoy_rehide_button"),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(4.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Default.VisibilityOff,
                            contentDescription = "Hide Media",
                            tint = Slate200,
                            modifier = Modifier.size(13.dp)
                        )
                        Text(
                            text = "Conceal",
                            fontSize = 11.sp,
                            color = Slate200,
                            fontWeight = FontWeight.Medium
                        )
                    }
                }

                Spacer(modifier = Modifier.height(6.dp))

                // Media Specific Body
                when (message.mediaType) {
                    MediaType.PHOTO -> {
                        PhotoMediaContent(
                            message = message,
                            onOpenFullscreen = { onOpenFullscreen(message) }
                        )
                    }
                    MediaType.AUDIO -> {
                        AudioMediaContent(message = message)
                    }
                    MediaType.DOCUMENT -> {
                        DocumentMediaContent(message = message)
                    }
                    else -> {
                        Text(
                            text = message.text,
                            color = Slate200,
                            fontSize = 13.sp,
                            modifier = Modifier.padding(8.dp)
                        )
                    }
                }
            }
        }
    }
}

@Composable
private fun PhotoMediaContent(
    message: ChatMessage,
    onOpenFullscreen: () -> Unit
) {
    Column {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .aspectRatio(16f / 10f)
                .clip(RoundedCornerShape(10.dp))
                .clickable { onOpenFullscreen() }
                .testTag("decoy_photo_preview")
        ) {
            if (message.mediaDrawableRes != null) {
                Image(
                    painter = painterResource(id = message.mediaDrawableRes),
                    contentDescription = message.mediaTitle.ifBlank { "Covert Photo" },
                    modifier = Modifier.matchParentSize(),
                    contentScale = ContentScale.Crop
                )
            } else {
                Box(
                    modifier = Modifier
                        .matchParentSize()
                        .background(
                            Brush.linearGradient(
                                listOf(Color(0xFF0F2027), Color(0xFF203A43), Color(0xFF2C5364))
                            )
                        )
                )
            }

            // Zoom pill
            Box(
                modifier = Modifier
                    .align(Alignment.BottomEnd)
                    .padding(8.dp)
                    .clip(RoundedCornerShape(8.dp))
                    .background(Color.Black.copy(alpha = 0.7f))
                    .padding(horizontal = 8.dp, vertical = 4.dp)
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(4.dp)
                ) {
                    Icon(
                        imageVector = Icons.Default.ZoomIn,
                        contentDescription = "Zoom",
                        tint = Color.White,
                        modifier = Modifier.size(14.dp)
                    )
                    Text(
                        text = "Expand",
                        color = Color.White,
                        fontSize = 10.sp,
                        fontWeight = FontWeight.Medium
                    )
                }
            }
        }

        if (message.mediaTitle.isNotBlank()) {
            Spacer(modifier = Modifier.height(6.dp))
            Text(
                text = message.mediaTitle,
                fontSize = 12.sp,
                fontWeight = FontWeight.SemiBold,
                color = Slate200,
                modifier = Modifier.padding(horizontal = 4.dp)
            )
            if (message.mediaSubtitle.isNotBlank()) {
                Text(
                    text = message.mediaSubtitle,
                    fontSize = 10.sp,
                    color = Slate400,
                    modifier = Modifier.padding(horizontal = 4.dp)
                )
            }
        }
    }
}

@Composable
private fun AudioMediaContent(message: ChatMessage) {
    var isPlaying by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(10.dp))
            .background(Slate800)
            .padding(10.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .size(40.dp)
                    .clip(CircleShape)
                    .background(AetherCoral)
                    .clickable { isPlaying = !isPlaying }
                    .testTag("decoy_audio_play_button"),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = if (isPlaying) Icons.Default.Pause else Icons.Default.PlayArrow,
                    contentDescription = if (isPlaying) "Pause" else "Play",
                    tint = Color.White,
                    modifier = Modifier.size(24.dp)
                )
            }

            Spacer(modifier = Modifier.width(12.dp))

            // Animated Simulated Audio Waveform
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = message.mediaTitle.ifBlank { "Encrypted Voice Note" },
                    fontSize = 12.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = Slate200
                )
                Spacer(modifier = Modifier.height(4.dp))

                // Simulated Waveform Bars
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(20.dp),
                    horizontalArrangement = Arrangement.spacedBy(3.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    val heights = listOf(8, 14, 18, 10, 16, 12, 19, 15, 9, 13, 17, 11, 14, 18, 8, 12)
                    heights.forEachIndexed { idx, h ->
                        val barHeight = if (isPlaying && (idx % 2 == 0)) (h * 1.2f).coerceAtMost(20f) else h.toFloat()
                        Box(
                            modifier = Modifier
                                .weight(1f)
                                .height(barHeight.dp)
                                .clip(RoundedCornerShape(2.dp))
                                .background(if (isPlaying) AetherCoral else AetherCoral.copy(alpha = 0.5f))
                        )
                    }
                }
            }
        }

        Spacer(modifier = Modifier.height(4.dp))
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = if (isPlaying) "0:14 / 0:34" else "0:00 / 0:34",
                fontSize = 10.sp,
                color = Slate400
            )
            Text(
                text = "Secure AAC Stream",
                fontSize = 10.sp,
                color = AetherCyan
            )
        }
    }
}

@Composable
private fun DocumentMediaContent(message: ChatMessage) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(10.dp))
            .background(Slate800)
            .padding(10.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(
            modifier = Modifier
                .size(42.dp)
                .clip(RoundedCornerShape(8.dp))
                .background(Color(0xFFEF4444).copy(alpha = 0.18f)),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                imageVector = Icons.Default.Description,
                contentDescription = "PDF Document",
                tint = Color(0xFFEF4444),
                modifier = Modifier.size(24.dp)
            )
        }

        Spacer(modifier = Modifier.width(12.dp))

        Column(modifier = Modifier.weight(1f)) {
            Text(
                text = message.mediaTitle.ifBlank { "Operation_Brief.pdf" },
                fontSize = 13.sp,
                fontWeight = FontWeight.SemiBold,
                color = Slate200
            )
            Text(
                text = message.mediaSubtitle.ifBlank { "1.8 MB • Encrypted PDF" },
                fontSize = 11.sp,
                color = Slate400
            )
        }

        IconButton(
            onClick = { /* simulated download */ },
            modifier = Modifier.size(36.dp)
        ) {
            Icon(
                imageVector = Icons.Default.FileDownload,
                contentDescription = "Download Document",
                tint = AetherCoral,
                modifier = Modifier.size(20.dp)
            )
        }
    }
}
