package com.maheswara660.vidora.core.ui.theme

import androidx.compose.ui.graphics.Color

data class AccentCombination(
    val primary: Color,
    val secondary: Color,
    val name: String
)

val CustomAccents = listOf(
    AccentCombination(Color(0xFF0D47A1), Color(0xFF1565C0), "Midnight Blue"),
    AccentCombination(Color(0xFF1B5E20), Color(0xFF2E7D32), "Forest Green"),
    AccentCombination(Color(0xFFE65100), Color(0xFFEF6C00), "Sunset Orange"),
    AccentCombination(Color(0xFF4A148C), Color(0xFF6A1B9A), "Royal Violet"),
    AccentCombination(Color(0xFFB71C1C), Color(0xFFC62828), "Ruby Red"),
    AccentCombination(Color(0xFF006064), Color(0xFF00838F), "Ocean Deep"),
    AccentCombination(Color(0xFF880E4F), Color(0xFFAD1457), "Berry Punch"),
    AccentCombination(Color(0xFF7E5700), Color(0xFF7E5700), "Golden Sand"),
    AccentCombination(Color(0xFF3E2723), Color(0xFF4E342E), "Coffee Bean")
)
