package com.demacia.cysigns.features.quiz.domain

import com.demacia.cysigns.data.Signs
import com.demacia.cysigns.features.quiz.data.QuizQuestion
import javax.inject.Inject

class QuizRepository @Inject constructor() {

    fun getRandomQuestion(): QuizQuestion {
        val shuffledSigns = Signs.entries.toMutableList().shuffled()
        return QuizQuestion(
            correctSign = shuffledSigns[0],
            incorrectSigns = shuffledSigns.takeLast(3),
        )
    }

    fun getQuestions(): List<QuizQuestion> {
        return Signs.entries.map {
            QuizQuestion(
                correctSign = it,
                incorrectSigns = getRandomSigns(exceptSign = it),
            )
        }
    }

    private fun getRandomSigns(
        exceptSign: Signs,
        count: Int = 3,
    ): List<Signs> {
        val otherSigns = Signs.entries.toMutableList().apply { remove(exceptSign) }
        return otherSigns.shuffled().take(count)
    }
}