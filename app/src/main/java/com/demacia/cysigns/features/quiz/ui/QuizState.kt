package com.demacia.cysigns.features.quiz.ui

import com.demacia.cysigns.data.Signs
import com.demacia.cysigns.features.quiz.data.QuizQuestion


data class QuizState(
    val allQuestions: List<QuizQuestion>,

    val currentQuestionIndex: Int = 0,
    val correctSign: Signs?,
    val incorrectSigns: List<Signs>,
    val shuffledSigns: List<Signs>,
    val selectedSign: Signs?,

    val correctAnswers: Int,
    val incorrectAnswers: Int,
) {
    companion object {
        fun default(): QuizState {
            return QuizState(
                allQuestions = emptyList(),
                currentQuestionIndex = 0,
                correctSign = null,
                incorrectSigns = emptyList(),
                shuffledSigns = emptyList(),
                selectedSign = null,
                correctAnswers = 0,
                incorrectAnswers = 0,
            )
        }
    }
}