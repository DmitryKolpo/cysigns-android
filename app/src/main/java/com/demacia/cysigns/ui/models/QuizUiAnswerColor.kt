package com.demacia.cysigns.ui.models

import androidx.compose.ui.graphics.Color

enum class QuizUiAnswerColor {
    Neutral, Correct, Incorrect,
}

internal fun QuizUiAnswerColor.toColor(): Color {
    return when (this) {
        QuizUiAnswerColor.Neutral -> Color.White
        QuizUiAnswerColor.Correct -> Color.Green
        QuizUiAnswerColor.Incorrect -> Color.Red
    }
}