package cn.chitanda.music.ui.theme

import android.annotation.SuppressLint
import android.os.Build
import androidx.annotation.IntRange
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material.Colors
import androidx.compose.material3.ColorScheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import cn.chitanda.music.ui.monet.theme.MonetColor

private val DarkColorScheme = darkColorScheme()

private val LightColorScheme = lightColorScheme()

@SuppressLint("NewApi")
@Composable
fun MusicTheme(
    customColor: MonetColor? = null,
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable() () -> Unit
) {
    val dynamicColor = Build.VERSION.SDK_INT >= Build.VERSION_CODES.S
    val colors = when {
        dynamicColor && darkTheme && customColor == null -> dynamicDarkColorScheme(LocalContext.current)
        dynamicColor && !darkTheme && customColor == null -> dynamicLightColorScheme(LocalContext.current)
        customColor != null && darkTheme -> customColor.darkMonetColorScheme()
        customColor != null && !darkTheme -> customColor.lightMonetColorScheme()
        darkTheme -> DarkColorScheme
        else -> LightColorScheme
    }

    MaterialTheme(
        colorScheme = colors,
        typography = Typography,
    ) {
        androidx.compose.material.MaterialTheme(
            colors = Colors(
                primary = colors.primary,
                primaryVariant = colors.inversePrimary,
                secondary = colors.secondary,
                onSecondary = colors.onSecondary,
                secondaryVariant = colors.secondaryContainer,
                background = colors.background,
                onBackground = colors.onBackground,
                surface = colors.surface, error = colors.error,
                onPrimary = colors.onPrimary,
                onSurface = colors.onSurface,
                onError = colors.onError, isLight = darkTheme
            ),
            shapes = Shapes, content = content
        )
    }
}

private fun MonetColor.getMonetNeutralColor(
    @IntRange(from = 1, to = 2) type: Int,
    @IntRange(from = 50, to = 900) shade: Int
): Color {
    val monetColor = when (type) {
        1 -> neutral1[shade]
        else -> neutral2[shade]
    }?.toArgb() ?: throw Exception("Neutral$type shade $shade doesn't exist")
    return Color(monetColor)
}

private fun MonetColor.getMonetAccentColor(
    @IntRange(from = 1, to = 3) type: Int,
    @IntRange(from = 50, to = 900) shade: Int
): Color {
    val monetColor = when (type) {
        1 -> accent1[shade]
        2 -> accent2[shade]
        else -> accent3[shade]
    }?.toArgb() ?: throw Exception("Accent$type shade $shade doesn't exist")
    return Color(monetColor)
}

@Composable
fun MonetColor.lightMonetColorScheme(
    primary: Color = getMonetAccentColor(1, 700),
    onPrimary: Color = getMonetNeutralColor(1, 50),
    primaryContainer: Color = getMonetAccentColor(2, 100),
    onPrimaryContainer: Color = getMonetAccentColor(1, 900),
    inversePrimary: Color = getMonetAccentColor(1, 200),
    secondary: Color = getMonetAccentColor(2, 700),
    onSecondary: Color = getMonetNeutralColor(1, 50),
    secondaryContainer: Color = getMonetAccentColor(2, 100),
    onSecondaryContainer: Color = getMonetAccentColor(2, 900),
    tertiary: Color = getMonetAccentColor(3, 600),
    onTertiary: Color = getMonetNeutralColor(1, 50),
    tertiaryContainer: Color = getMonetAccentColor(3, 100),
    onTertiaryContainer: Color = getMonetAccentColor(3, 900),
    background: Color = getMonetNeutralColor(1, 50),
    onBackground: Color = getMonetNeutralColor(1, 900),
    surface: Color = getMonetNeutralColor(1, 50),
    onSurface: Color = getMonetNeutralColor(1, 900),
    surfaceVariant: Color = getMonetNeutralColor(2, 100),
    onSurfaceVariant: Color = getMonetNeutralColor(2, 700),
    inverseSurface: Color = getMonetNeutralColor(1, 800),
    inverseOnSurface: Color = getMonetNeutralColor(2, 50),
    outline: Color = getMonetAccentColor(2, 500),
): ColorScheme =
   lightColorScheme(
        primary = primary,
        onPrimary = onPrimary,
        primaryContainer = primaryContainer,
        onPrimaryContainer = onPrimaryContainer,
        inversePrimary = inversePrimary,
        secondary = secondary,
        onSecondary = onSecondary,
        secondaryContainer = secondaryContainer,
        onSecondaryContainer = onSecondaryContainer,
        tertiary = tertiary,
        onTertiary = onTertiary,
        tertiaryContainer = tertiaryContainer,
        onTertiaryContainer = onTertiaryContainer,
        background = background,
        onBackground = onBackground,
        surface = surface,
        onSurface = onSurface,
        surfaceVariant = surfaceVariant,
        onSurfaceVariant = onSurfaceVariant,
        inverseSurface = inverseSurface,
        inverseOnSurface = inverseOnSurface,
        outline = outline,
    )

/**
 * Any values that are not set will be chosen to best represent default values given by [dynamicDarkColorScheme][androidx.compose.material3.dynamicDarkColorScheme]
 * on Android 12+ devices
 */
@Composable
fun MonetColor.darkMonetColorScheme(
    primary: Color = getMonetAccentColor(1, 200),
    onPrimary: Color = getMonetAccentColor(1, 800),
    primaryContainer: Color = getMonetAccentColor(1, 600),
    onPrimaryContainer: Color = getMonetAccentColor(2, 100),
    inversePrimary: Color = getMonetAccentColor(1, 600),
    secondary: Color = getMonetAccentColor(2, 200),
    onSecondary: Color = getMonetAccentColor(2, 800),
    secondaryContainer: Color = getMonetAccentColor(2, 700),
    onSecondaryContainer: Color = getMonetAccentColor(2, 100),
    tertiary: Color = getMonetAccentColor(3, 200),
    onTertiary: Color = getMonetAccentColor(3, 700),
    tertiaryContainer: Color = getMonetAccentColor(3, 700),
    onTertiaryContainer: Color = getMonetAccentColor(3, 100),
    background: Color = getMonetNeutralColor(1, 900),
    onBackground: Color = getMonetNeutralColor(1, 100),
    surface: Color = getMonetNeutralColor(1, 900),
    onSurface: Color = getMonetNeutralColor(1, 100),
    surfaceVariant: Color = getMonetNeutralColor(2, 700),
    onSurfaceVariant: Color = getMonetNeutralColor(2, 200),
    inverseSurface: Color = getMonetNeutralColor(1, 100),
    inverseOnSurface: Color = getMonetNeutralColor(1, 800),
    outline: Color = getMonetNeutralColor(2, 500),
): ColorScheme =
   darkColorScheme(
        primary = primary,
        onPrimary = onPrimary,
        primaryContainer = primaryContainer,
        onPrimaryContainer = onPrimaryContainer,
        inversePrimary = inversePrimary,
        secondary = secondary,
        onSecondary = onSecondary,
        secondaryContainer = secondaryContainer,
        onSecondaryContainer = onSecondaryContainer,
        tertiary = tertiary,
        onTertiary = onTertiary,
        tertiaryContainer = tertiaryContainer,
        onTertiaryContainer = onTertiaryContainer,
        background = background,
        onBackground = onBackground,
        surface = surface,
        onSurface = onSurface,
        surfaceVariant = surfaceVariant,
        onSurfaceVariant = onSurfaceVariant,
        inverseSurface = inverseSurface,
        inverseOnSurface = inverseOnSurface,
        outline = outline,
    )