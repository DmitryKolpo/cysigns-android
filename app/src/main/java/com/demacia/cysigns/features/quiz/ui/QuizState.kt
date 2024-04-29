package com.demacia.cysigns.features.quiz.ui

import com.demacia.cysigns.data.Signs
import com.demacia.cysigns.features.quiz.data.QuizQuestion
import com.demacia.shared.components.game.GameMode


data class QuizState(
    val allQuestions: List<QuizQuestion>,
    val mode: Mode?,

    val currentQuestionIndex: Int = 0,
    val correctSign: Signs?,
    val incorrectSigns: List<Signs>,
    val shuffledSigns: List<Signs>,
    val selectedSign: Signs?,

    val correctAnswers: Int,
    val incorrectAnswers: Int,

    val isFinished: Boolean,
) {
    companion object {
        fun default(): QuizState {
            return QuizState(
                allQuestions = emptyList(),
                mode = null,
                currentQuestionIndex = 0,
                correctSign = null,
                incorrectSigns = emptyList(),
                shuffledSigns = emptyList(),
                selectedSign = null,
                correctAnswers = 0,
                incorrectAnswers = 0,
                isFinished = false,
            )
        }
    }
}

enum class Mode {
    ByPicture, ByName;

    companion object {
        fun GameMode.toUiMode(): Mode {
            return when (this) {
                GameMode.ByName -> ByName
                GameMode.ByPicture -> ByPicture
            }
        }
    }
}