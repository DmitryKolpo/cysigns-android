package com.demacia.cysigns.features.quiz.ui

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.demacia.cysigns.data.Signs
import com.demacia.cysigns.features.quiz.data.QuizQuestion
import com.demacia.cysigns.features.quiz.domain.QuizRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.updateAndGet
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class QuizViewModel @Inject constructor(
    private val quizRepository: QuizRepository,
) : ViewModel() {

    private val state = MutableStateFlow(QuizState(null, emptyList(), emptyList(), null))
    val uiState: Flow<QuizUiState> = state
        .map { it.toUiState() }
        .flowOn(Dispatchers.Default)

    private val _effect: Channel<UiEffect> = Channel()
    val effect = _effect.receiveAsFlow()

    init {
        sendEvent(Event.Internal.OnInit)
    }

    fun sendEvent(event: Event) {
        viewModelScope.launch {
            when (event) {
                is Event.Internal -> handleInternalEvent(event)
                is Event.Ui -> handleUiEvent(event)
            }
        }
    }

    private suspend fun handleUiEvent(event: Event.Ui) {
        when (event) {
            is Event.Ui.OnSignClick -> checkAnswer(event.signOrdinal)
        }
    }

    private suspend fun handleInternalEvent(event: Event.Internal) {
        when (event) {
            Event.Internal.OnInit -> loadNextQuestion()
        }
    }

    private suspend fun loadNextQuestion() {
        val question = quizRepository.getRandomQuestion()
        reduce(Action.SetQuestion(question))
    }

    private suspend fun checkAnswer(signOrdinal: Int) {
        val selectedSign = Signs.entries.toTypedArray()[signOrdinal]
        val correctSign = state.value.correctSign
        val isCorrectAnswer = correctSign == selectedSign

        reduce(Action.SetSelectedAnswer(selectedSign))
        if (isCorrectAnswer) {
            delay(1000)
        } else {
            delay(2000)
        }
        loadNextQuestion()
    }

    private suspend fun reduce(action: Action) {
        val newValue = when (action) {
            is Action.SetQuestion -> state.updateAndGet {
                it.copy(
                    correctSign = action.question.correctSign,
                    incorrectSigns = action.question.incorrectSigns,
                    shuffledSigns = (action.question.incorrectSigns + action.question.correctSign).shuffled(),
                    selectedSign = null,
                )
            }

            is Action.SetSelectedAnswer -> state.updateAndGet {
                it.copy(selectedSign = action.sign)
            }
        }
        state.emit(newValue)
    }

    private fun QuizState.toUiState(): QuizUiState {
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
        val uiAnswers = shuffledSigns.map {
            QuizUiAnswer(
                signName = it.signName,
                signOrdinal = it.ordinal,
                color = getUiSignColor(it, selectedSign, correctSign),
            )
        }

        return QuizUiState.Content(
            image = correctSign.imageResId,
            answers = uiAnswers,
        )
    }

    data class QuizState(
        val correctSign: Signs?,
        val incorrectSigns: List<Signs>,
        val shuffledSigns: List<Signs>,
        val selectedSign: Signs?,
    )

    sealed interface QuizUiState {
        data object Loading : QuizUiState
        data class Content(
            @DrawableRes val image: Int,
            val answers: List<QuizUiAnswer>,
        ) : QuizUiState
    }

    data class QuizUiAnswer(
        @StringRes val signName: Int,
        val signOrdinal: Int,
        val color: QuizUiAnswerColor,
    )

    enum class QuizUiAnswerColor {
        Neutral, Correct, Incorrect,
    }
}

sealed interface Event {
    sealed interface Ui : Event {
        data class OnSignClick(val signOrdinal: Int) : Ui
    }

    sealed interface Internal : Event {
        data object OnInit : Internal
    }
}

private sealed interface Action {
    data class SetQuestion(val question: QuizQuestion) : Action
    data class SetSelectedAnswer(val sign: Signs) : Action
}

sealed interface UiEffect {
    data class ShowToast(val text: String) : UiEffect
}
