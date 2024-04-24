package com.demacia.cysigns.features.quiz.ui

import androidx.annotation.DrawableRes

sealed interface QuizUiState {
    data object Loading : QuizUiState
    data class Content(
        @DrawableRes val image: Int,
        val answers: List<QuizViewModel.QuizUiAnswer>,
        val statistic: Statistic,
        val nextButtonEnabled: Boolean,
    ) : QuizUiState
}

data class Statistic(
    val current: Int,
    val total: Int,
    val correct: Int,
    val incorrect: Int,
)
