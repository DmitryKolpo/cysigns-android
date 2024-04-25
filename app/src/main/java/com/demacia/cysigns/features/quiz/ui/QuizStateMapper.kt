package com.demacia.cysigns.features.quiz.ui

import com.demacia.cysigns.data.Signs

internal fun QuizState.toUiState(): QuizUiState {
    fun getUiSignColor(
        currentSign: Signs,
        selectedSign: Signs?,
        correctSign: Signs,
    ): QuizViewModel.QuizUiAnswerColor {
        if (selectedSign == null) return QuizViewModel.QuizUiAnswerColor.Neutral

        if (currentSign == correctSign) return QuizViewModel.QuizUiAnswerColor.Correct
        if (currentSign == selectedSign) return QuizViewModel.QuizUiAnswerColor.Incorrect
        return QuizViewModel.QuizUiAnswerColor.Neutral
    }

    if (correctSign == null) return QuizUiState.Loading
    if (isFinished) return QuizUiState.Finished(getStatistics())

    val uiAnswers = shuffledSigns.map {
        QuizViewModel.QuizUiAnswer(
            signName = it.signName,
            signOrdinal = it.ordinal,
            color = getUiSignColor(it, selectedSign, correctSign),
        )
    }

    return QuizUiState.Content(
        image = correctSign.imageResId,
        answers = uiAnswers,
        statistic = getStatistics(),
        nextButtonEnabled = selectedSign != null,
    )
}

private fun QuizState.getStatistics(): Statistic {
    return Statistic(
        current = currentQuestionIndex + 1,
        total = allQuestions.size,
        correct = correctAnswers,
        incorrect = incorrectAnswers,
    )
}