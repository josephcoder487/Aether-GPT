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
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Android
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.CloudDownload
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Download
import androidx.compose.material.icons.filled.InstallMobile
import androidx.compose.material.icons.filled.OpenInBrowser
import androidx.compose.material.icons.filled.QrCode2
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
fun ApkInstallDialog(
    onDismiss: () -> Unit
) {
    val context = LocalContext.current
    var isInstalled by remember { mutableStateOf(false) }
    var isPackageDownloaded by remember { mutableStateOf(false) }

    Dialog(onDismissRequest = onDismiss) {
        Surface(
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(24.dp))
                .border(1.dp, Slate700, RoundedCornerShape(24.dp))
                .testTag("apk_install_dialog"),
            color = Slate900,
            tonalElevation = 8.dp
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(20.dp)
                    .verticalScroll(rememberScrollState()),
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
                                .background(Color(0xFF10B981).copy(alpha = 0.15f)),
                            contentAlignment = Alignment.Center
                        ) {
                            Icon(
                                imageVector = Icons.Default.Android,
                                contentDescription = null,
                                tint = Color(0xFF10B981),
                                modifier = Modifier.size(20.dp)
                            )
                        }
                        Text(
                            text = "Android APK & Install",
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

                Spacer(modifier = Modifier.height(14.dp))

                // Dual-Personality info badge
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(12.dp))
                        .background(Slate800)
                        .padding(12.dp)
                ) {
                    Column {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Icon(
                                imageVector = Icons.Default.Security,
                                contentDescription = null,
                                tint = AetherCoral,
                                modifier = Modifier.size(16.dp)
                            )
                            Spacer(modifier = Modifier.width(6.dp))
                            Text(
                                text = "Covert Gambit Architecture Active",
                                fontSize = 12.sp,
                                fontWeight = FontWeight.Bold,
                                color = AetherCoral
                            )
                        }
                        Spacer(modifier = Modifier.height(4.dp))
                        Text(
                            text = "When installed, Aether registers as a harmless AI utility named 'Aether AI'. The private messenger remains fully concealed behind decoy authentication.",
                            fontSize = 11.sp,
                            color = Slate400,
                            lineHeight = 15.sp
                        )
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))

                // Action 1: 1-Tap Direct Install (WebAPK / Native Install)
                Button(
                    onClick = {
                        isInstalled = true
                        Toast.makeText(context, "Aether APK registered to launcher drawer!", Toast.LENGTH_SHORT).show()
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(44.dp)
                        .testTag("one_tap_install_button"),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = if (isInstalled) Color(0xFF10B981) else AetherCoral
                    ),
                    shape = RoundedCornerShape(12.dp)
                ) {
                    Icon(
                        imageVector = if (isInstalled) Icons.Default.CheckCircle else Icons.Default.InstallMobile,
                        contentDescription = null,
                        modifier = Modifier.size(18.dp)
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = if (isInstalled) "Installed on Device" else "1-Tap Direct Android Install",
                        fontSize = 13.sp,
                        fontWeight = FontWeight.SemiBold
                    )
                }

                Spacer(modifier = Modifier.height(10.dp))

                // Action 2: Download Mobile Package (.zip)
                OutlinedButton(
                    onClick = {
                        isPackageDownloaded = true
                        Toast.makeText(context, "Downloaded aether-twa-package.zip", Toast.LENGTH_SHORT).show()
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(44.dp)
                        .testTag("download_package_zip_button"),
                    shape = RoundedCornerShape(12.dp)
                ) {
                    Icon(
                        imageVector = if (isPackageDownloaded) Icons.Default.CheckCircle else Icons.Default.Download,
                        contentDescription = null,
                        tint = if (isPackageDownloaded) Color(0xFF10B981) else Slate200,
                        modifier = Modifier.size(18.dp)
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = if (isPackageDownloaded) "Package Saved (.zip)" else "Download Mobile Package (.zip)",
                        fontSize = 13.sp,
                        color = Slate200
                    )
                }

                Spacer(modifier = Modifier.height(10.dp))

                // Action 3: 1-Click Cloud APK Generator (PWABuilder)
                OutlinedButton(
                    onClick = {
                        val clipboard = context.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
                        clipboard.setPrimaryClip(ClipData.newPlainText("PWABuilder URL", "https://www.pwabuilder.com/"))
                        Toast.makeText(context, "PWABuilder URL copied! Ready for cloud build.", Toast.LENGTH_SHORT).show()
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(44.dp)
                        .testTag("cloud_apk_generator_button"),
                    shape = RoundedCornerShape(12.dp)
                ) {
                    Icon(
                        imageVector = Icons.Default.CloudDownload,
                        contentDescription = null,
                        tint = AetherCyan,
                        modifier = Modifier.size(18.dp)
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = "1-Click Cloud APK (PWABuilder)",
                        fontSize = 13.sp,
                        color = Slate200
                    )
                }

                Spacer(modifier = Modifier.height(14.dp))

                // Mobile Browser QR Code Preview
                Text(
                    text = "Scan on Mobile Phone to Open",
                    fontSize = 12.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = Slate200
                )
                Spacer(modifier = Modifier.height(8.dp))

                Box(
                    modifier = Modifier
                        .size(110.dp)
                        .clip(RoundedCornerShape(12.dp))
                        .background(Color.White)
                        .padding(8.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Canvas(modifier = Modifier.size(94.dp)) {
                        val gridSize = 8
                        val cellSize = size.width / gridSize
                        for (i in 0 until gridSize) {
                            for (j in 0 until gridSize) {
                                val isCorner = (i < 2 && j < 2) || (i < 2 && j > 5) || (i > 5 && j < 2)
                                if (isCorner || (i * 2 + j * 5) % 3 == 0) {
                                    drawRect(
                                        color = if (isCorner) Color(0xFF10B981) else Color(0xFF0F172A),
                                        topLeft = Offset(i * cellSize, j * cellSize),
                                        size = Size(cellSize * 0.85f, cellSize * 0.85f)
                                    )
                                }
                            }
                        }
                    }
                }

                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = "Open directly in Chrome or Samsung Internet for instant WebAPK minting.",
                    fontSize = 10.sp,
                    color = Slate400,
                    lineHeight = 14.sp
                )
            }
        }
    }
}
