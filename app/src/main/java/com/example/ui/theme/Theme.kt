package com.example.ui.theme

import android.app.Activity
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalView
import androidx.core.view.WindowCompat

private val PurpleThemeColorScheme = darkColorScheme(
    primary = PurpleUserBubble,
    onPrimary = Color.White,
    primaryContainer = LilacContainer,
    onPrimaryContainer = LilacSoft,
    secondary = LilacPastel,
    onSecondary = ThemePurpleDarker,
    secondaryContainer = ThemePurpleSurface,
    onSecondaryContainer = LilacSoft,
    tertiary = AetherCoral,
    background = ThemePurpleDeepBg,
    onBackground = Slate100,
    surface = ThemePurpleSurface,
    onSurface = Slate100,
    surfaceVariant = ThemePurpleDarker,
    onSurfaceVariant = Slate400,
    outline = LilacBorder.copy(alpha = 0.4f),
    error = NewsAlertBadge,
    onError = Color.White
)

@Composable
fun MyApplicationTheme(
    content: @Composable () -> Unit
) {
    val colorScheme = PurpleThemeColorScheme
    val view = LocalView.current
    if (!view.isInEditMode) {
        SideEffect {
            val window = (view.context as? Activity)?.window
            if (window != null) {
                window.statusBarColor = ThemePurpleDarker.toArgb()
                window.navigationBarColor = ThemePurpleDarker.toArgb()
                WindowCompat.getInsetsController(window, view).isAppearanceLightStatusBars = false
                WindowCompat.getInsetsController(window, view).isAppearanceLightNavigationBars = false
            }
        }
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}
