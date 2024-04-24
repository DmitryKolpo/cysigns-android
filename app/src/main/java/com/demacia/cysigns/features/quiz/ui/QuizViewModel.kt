package com.demacia.cysigns.features.quiz.ui

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

    private val state = MutableStateFlow(QuizState.default())
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
            Event.Internal.OnInit -> init()
        }
    }

    private suspend fun init() {
        val allQuestions = quizRepository.getQuestions().shuffled()
        val question = allQuestions[0]
        reduce(Action.SaveAllQuestions(allQuestions))
        reduce(Action.SetQuestion(question, 0))
    }

    private suspend fun loadNextQuestion(state: QuizState) {
        //TODO: if last question, show finish screen
        if (state.currentQuestionIndex == state.allQuestions.lastIndex) return

        val nextIndex = state.currentQuestionIndex + 1
        val question = state.allQuestions[nextIndex]
        reduce(Action.SetQuestion(question, state.currentQuestionIndex + 1))
    }

    private suspend fun checkAnswer(signOrdinal: Int) {
        val selectedSign = Signs.entries.toTypedArray()[signOrdinal]
        val correctSign = state.value.correctSign
        val isCorrectAnswer = correctSign == selectedSign

        reduce(Action.SetSelectedAnswer(selectedSign, isCorrectAnswer))
        delay(if (isCorrectAnswer) 1000 else 2000)
        loadNextQuestion(state.value)
    }

    private suspend fun reduce(action: Action) {
        val newValue = when (action) {
            is Action.SaveAllQuestions -> state.updateAndGet {
                it.copy(
                    allQuestions = action.questions,
                )
            }

            is Action.SetQuestion -> state.updateAndGet {
                it.copy(
                    correctSign = action.question.correctSign,
                    incorrectSigns = action.question.incorrectSigns,
                    shuffledSigns = (action.question.incorrectSigns + action.question.correctSign).shuffled(),
                    selectedSign = null,
                    currentQuestionIndex = action.index,
                )
            }

            is Action.SetSelectedAnswer -> state.updateAndGet {
                it.copy(
                    selectedSign = action.sign,
                    correctAnswers = state.value.correctAnswers.run { if (action.isCorrect) this + 1 else this },
                    incorrectAnswers = state.value.incorrectAnswers.run { if (!action.isCorrect) this + 1 else this },
                )
            }
        }
        state.emit(newValue)
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
    data class SaveAllQuestions(val questions: List<QuizQuestion>) : Action
    data class SetSelectedAnswer(val sign: Signs, val isCorrect: Boolean) : Action
    data class SetQuestion(val question: QuizQuestion, val index: Int) : Action
}

sealed interface UiEffect {
    data class ShowToast(val text: String) : UiEffect
}
