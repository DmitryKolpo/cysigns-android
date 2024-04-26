package com.demacia.cysigns.features.quiz.ui

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import com.demacia.cysigns.ui.models.QuizUiAnswerColor
import com.demacia.cysigns.ui.models.Statistic

sealed interface QuizUiState {
    data object Loading : QuizUiState

    sealed class Content(
        val statistic: Statistic,
        val nextButtonEnabled: Boolean,
    ): QuizUiState {
        class ByPicture(
            @DrawableRes val image: Int,
            val answers: List<QuizUiAnswer>,
            statistic: Statistic,
            nextButtonEnabled: Boolean,
        ) : Content(statistic, nextButtonEnabled)

        class ByName(
            @StringRes val name: Int,
            val answers: List<QuizByNameUiAnswer>,
            statistic: Statistic,
            nextButtonEnabled: Boolean,
        ) : Content(statistic, nextButtonEnabled)
    }

    data class Finished(
        val statistic: Statistic,
    ) : QuizUiState
}

data class QuizUiAnswer(
    val signOrdinal: Int,
    val color: QuizUiAnswerColor,
    @StringRes val signName: Int,
)

data class QuizByNameUiAnswer(
    val signOrdinal: Int,
    val color: QuizUiAnswerColor,
    @DrawableRes val signImageRes: Int,
)