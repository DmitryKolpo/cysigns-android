package com.demacia.cysigns.features.quiz.ui

import com.demacia.cysigns.data.Signs
import com.demacia.cysigns.ui.models.QuizUiAnswerColor
import com.demacia.cysigns.ui.models.Statistic

internal fun QuizState.toUiState(): QuizUiState {
    fun getUiSignColor(
        currentSign: Signs,
        selectedSign: Signs?,
        correctSign: Signs,
    ): QuizUiAnswerColor {
        if (selectedSign == null) return QuizUiAnswerColor.Neutral

        if (currentSign == correctSign) return QuizUiAnswerColor.Correct
        if (currentSign == selectedSign) return QuizUiAnswerColor.Incorrect
        return QuizUiAnswerColor.Neutral
    }

    if (correctSign == null) return QuizUiState.Loading
    if (isFinished) return QuizUiState.Finished(getStatistics())

    return when (mode) {
        null -> QuizUiState.Loading
        Mode.ByPicture -> {
            QuizUiState.Content.ByPicture(
                image = correctSign.imageResId,
                answers = shuffledSigns.map {
                    QuizUiAnswer(
                        signName = it.signName,
                        signOrdinal = it.ordinal,
                        color = getUiSignColor(it, selectedSign, correctSign),
                    )
                },
                statistic = getStatistics(),
                nextButtonEnabled = selectedSign != null,
            )
        }

        Mode.ByName -> {
            QuizUiState.Content.ByName(
                name = correctSign.signName,
                answers = shuffledSigns.map {
                    QuizByNameUiAnswer(
                        signOrdinal = it.ordinal,
                        color = getUiSignColor(it, selectedSign, correctSign),
                        signImageRes = it.imageResId,
                    )
                },
                statistic = getStatistics(),
                nextButtonEnabled = selectedSign != null,
            )
        }
    }
}

private fun QuizState.getStatistics(): Statistic {
    return Statistic(
        current = currentQuestionIndex + 1,
        total = allQuestions.size,
        correct = correctAnswers,
        incorrect = incorrectAnswers,
    )
}