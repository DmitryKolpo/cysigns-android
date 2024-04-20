package com.demacia.cysigns.features.quiz.data

import com.demacia.cysigns.data.Signs

data class QuizQuestion(
    val correctSign: Signs,
    val incorrectSigns: List<Signs>,
)