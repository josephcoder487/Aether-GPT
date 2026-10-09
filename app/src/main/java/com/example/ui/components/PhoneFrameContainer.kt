package com.example.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.BatteryFull
import androidx.compose.material.icons.filled.SignalCellularAlt
import androidx.compose.material.icons.filled.Wifi
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ui.theme.Slate400
import com.example.ui.theme.Slate800
import com.example.ui.theme.Slate950
import kotlinx.coroutines.delay
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

@Composable
fun PhoneFrameContainer(
    isFrameEnabled: Boolean,
    modifier: Modifier = Modifier,
    content: @Composable () -> Unit
) {
    if (!isFrameEnabled) {
        Box(modifier = modifier.fillMaxSize()) {
            content()
        }
    } else {
        var currentTimeStr by remember {
            mutableStateOf(SimpleDateFormat("h:mm", Locale.getDefault()).format(Date()))
        }

        LaunchedEffect(Unit) {
            while (true) {
                delay(30000)
                currentTimeStr = SimpleDateFormat("h:mm", Locale.getDefault()).format(Date())
            }
        }

        Box(
            modifier = modifier
                .fillMaxSize()
                .background(Slate950)
                .padding(horizontal = 8.dp, vertical = 6.dp),
            contentAlignment = Alignment.Center
        ) {
            // Android Phone Device Bezel
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .clip(RoundedCornerShape(36.dp))
                    .border(3.5.dp, Slate800, RoundedCornerShape(36.dp))
                    .background(Color(0xFF070C18))
            ) {
                Column(modifier = Modifier.fillMaxSize()) {
                    // Dynamic Phone Status Bar with Notch & Camera Cutout
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 20.dp, vertical = 8.dp),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        // Clock
                        Text(
                            text = currentTimeStr,
                            fontSize = 12.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color.White
                        )

                        // Center Speaker Notch & Camera Cutout
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            // Speaker notch
                            Box(
                                modifier = Modifier
                                    .width(44.dp)
                                    .height(4.dp)
                                    .clip(RoundedCornerShape(2.dp))
                                    .background(Slate800)
                            )
                            // Camera cutout
                            Box(
                                modifier = Modifier
                                    .size(10.dp)
                                    .clip(CircleShape)
                                    .background(Color(0xFF030712))
                                    .border(1.dp, Slate800, CircleShape)
                            )
                        }

                        // Status icons: Cell, WiFi, Battery
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(4.dp)
                        ) {
                            Icon(
                                imageVector = Icons.Default.SignalCellularAlt,
                                contentDescription = "Cellular",
                                tint = Slate400,
                                modifier = Modifier.size(13.dp)
                            )
                            Icon(
                                imageVector = Icons.Default.Wifi,
                                contentDescription = "WiFi",
                                tint = Slate400,
                                modifier = Modifier.size(13.dp)
                            )
                            Icon(
                                imageVector = Icons.Default.BatteryFull,
                                contentDescription = "Battery",
                                tint = Slate400,
                                modifier = Modifier.size(15.dp)
                            )
                        }
                    }

                    // Content Area
                    Box(modifier = Modifier.weight(1f)) {
                        content()
                    }

                    // Android Gesture Navigation Pill at Bottom
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(bottom = 6.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        Box(
                            modifier = Modifier
                                .width(80.dp)
                                .height(4.dp)
                                .clip(RoundedCornerShape(2.dp))
                                .background(Slate400.copy(alpha = 0.6f))
                        )
                    }
                }
            }
        }
    }
}
