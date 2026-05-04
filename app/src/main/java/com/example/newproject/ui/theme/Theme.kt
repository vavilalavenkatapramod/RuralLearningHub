package com.example.newproject.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

private val DarkColorScheme = darkColorScheme(
    primary = LeafLight,
    secondary = DeepSaffron,
    tertiary = RiverBlue,
    background = ForestNight,
    surface = Color(0xFF183A32),
    surfaceVariant = Color(0xFF22463D),
    primaryContainer = ClayGreen,
    secondaryContainer = SoilBrown,
    tertiaryContainer = Color(0xFF1E4149),
    onPrimary = ForestNight,
    onSecondary = Color.White,
    onTertiary = Color.White,
    onBackground = Color(0xFFF4F7F2),
    onSurface = Color(0xFFF4F7F2),
    onSurfaceVariant = Color(0xFFD2E0D8),
    onPrimaryContainer = Color(0xFFF3FFF8),
    onSecondaryContainer = Color(0xFFFFE8D5),
    onTertiaryContainer = Color(0xFFD8F1F8)
)

private val LightColorScheme = lightColorScheme(
    primary = ClayGreen,
    secondary = DeepSaffron,
    tertiary = RiverBlue,
    background = WhiteSand,
    surface = Color.White,
    surfaceVariant = MilletCream,
    primaryContainer = FieldMist,
    secondaryContainer = SunriseTint,
    tertiaryContainer = Color(0xFFD4EAF0),
    onPrimary = Color.White,
    onSecondary = Color.White,
    onTertiary = Color.White,
    onBackground = Color(0xFF1A1D1B),
    onSurface = Color(0xFF1A1D1B),
    onSurfaceVariant = Color(0xFF5A635E),
    onPrimaryContainer = Color(0xFF12372D),
    onSecondaryContainer = Color(0xFF5D3300),
    onTertiaryContainer = Color(0xFF11363D)
)

@Composable
fun NewProjectTheme(
    darkTheme: Boolean = false,
    content: @Composable () -> Unit
) {
    val colorScheme = if (darkTheme) DarkColorScheme else LightColorScheme

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}
