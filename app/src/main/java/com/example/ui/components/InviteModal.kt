package com.example.ui.components

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.widget.Toast
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.ContentCopy
import androidx.compose.material.icons.filled.PersonAdd
import androidx.compose.material.icons.filled.QrCode
import androidx.compose.material.icons.filled.Security
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import com.example.ui.theme.AetherCoral
import com.example.ui.theme.AetherCyan
import com.example.ui.theme.Slate200
import com.example.ui.theme.Slate400
import com.example.ui.theme.Slate700
import com.example.ui.theme.Slate800
import com.example.ui.theme.Slate900

@Composable
fun InviteModal(
    inviteCode: String,
    onDismiss: () -> Unit,
    onSimulateAccept: () -> Unit
) {
    val context = LocalContext.current
    var isCopied by remember { mutableStateOf(false) }
    val inviteUrl = "https://aether.chat/invite?u=$inviteCode"

    Dialog(onDismissRequest = onDismiss) {
        Surface(
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(24.dp))
                .border(1.dp, Slate700, RoundedCornerShape(24.dp))
                .testTag("invite_modal_dialog"),
            color = Slate900,
            tonalElevation = 8.dp
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(20.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                // Header
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        Box(
                            modifier = Modifier
                                .size(34.dp)
                                .clip(CircleShape)
                                .background(AetherCoral.copy(alpha = 0.15f)),
                            contentAlignment = Alignment.Center
                        ) {
                            Icon(
                                imageVector = Icons.Default.PersonAdd,
                                contentDescription = null,
                                tint = AetherCoral,
                                modifier = Modifier.size(18.dp)
                            )
                        }
                        Text(
                            text = "Covert Friend Invite",
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Bold,
                            color = Slate200
                        )
                    }

                    IconButton(
                        onClick = onDismiss,
                        modifier = Modifier.size(32.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Default.Close,
                            contentDescription = "Close",
                            tint = Slate400,
                            modifier = Modifier.size(18.dp)
                        )
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))

                // Stylized Secure QR Code
                Box(
                    modifier = Modifier
                        .size(150.dp)
                        .clip(RoundedCornerShape(16.dp))
                        .background(Color.White)
                        .padding(12.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Canvas(modifier = Modifier.size(126.dp)) {
                        val gridSize = 9
                        val cellSize = size.width / gridSize

                        // Draw QR pattern simulation with corner markers
                        for (i in 0 until gridSize) {
                            for (j in 0 until gridSize) {
                                val isCornerMarker =
                                    (i < 3 && j < 3) || (i < 3 && j > 5) || (i > 5 && j < 3)
                                val isOuterCorner =
                                    isCornerMarker && (i == 0 || i == 2 || j == 0 || j == 2 || i == 6 || i == 8 || j == 6 || j == 8)
                                val isCenterCorner =
                                    (i == 1 && j == 1) || (i == 1 && j == 7) || (i == 7 && j == 1)

                                val shouldFill = isOuterCorner || isCenterCorner ||
                                        ((i + j * 3 + (inviteCode.hashCode() % 7)) % 3 == 0)

                                if (shouldFill) {
                                    drawRect(
                                        color = if (isCenterCorner) Color(0xFFF0643B) else Color(0xFF0F172A),
                                        topLeft = Offset(i * cellSize, j * cellSize),
                                        size = Size(cellSize * 0.9f, cellSize * 0.9f)
                                    )
                                }
                            }
                        }
                    }
                }

                Spacer(modifier = Modifier.height(12.dp))

                Text(
                    text = "Scan with Aether or another device to establish an encrypted relay.",
                    fontSize = 11.sp,
                    color = Slate400,
                    lineHeight = 15.sp,
                    modifier = Modifier.padding(horizontal = 8.dp)
                )

                Spacer(modifier = Modifier.height(14.dp))

                // Copyable Link Container
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(12.dp))
                        .background(Slate800)
                        .border(1.dp, Slate700, RoundedCornerShape(12.dp))
                        .padding(horizontal = 12.dp, vertical = 8.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column(modifier = Modifier.weight(1f)) {
                        Text(
                            text = "SHAREABLE LINK",
                            fontSize = 9.sp,
                            fontWeight = FontWeight.Bold,
                            color = AetherCoral,
                            letterSpacing = 0.5.sp
                        )
                        Text(
                            text = inviteUrl,
                            fontSize = 11.sp,
                            color = Slate200,
                            maxLines = 1
                        )
                    }

                    Spacer(modifier = Modifier.width(6.dp))

                    IconButton(
                        onClick = {
                            val clipboard =
                                context.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
                            clipboard.setPrimaryClip(ClipData.newPlainText("Aether Invite", inviteUrl))
                            isCopied = true
                            Toast.makeText(context, "Invite link copied to clipboard!", Toast.LENGTH_SHORT).show()
                        },
                        modifier = Modifier
                            .size(34.dp)
                            .testTag("copy_invite_link_button")
                    ) {
                        Icon(
                            imageVector = if (isCopied) Icons.Default.Check else Icons.Default.ContentCopy,
                            contentDescription = "Copy",
                            tint = if (isCopied) Color(0xFF10B981) else AetherCoral,
                            modifier = Modifier.size(18.dp)
                        )
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))

                // Action: Simulate friend accepting
                Button(
                    onClick = onSimulateAccept,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(44.dp)
                        .testTag("simulate_friend_accept_button"),
                    colors = ButtonDefaults.buttonColors(containerColor = AetherCoral),
                    shape = RoundedCornerShape(12.dp)
                ) {
                    Icon(
                        imageVector = Icons.Default.Security,
                        contentDescription = null,
                        modifier = Modifier.size(16.dp)
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = "Simulate Friend Acceptance",
                        fontSize = 13.sp,
                        fontWeight = FontWeight.SemiBold
                    )
                }
            }
        }
    }
}
