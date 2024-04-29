package com.demacia.cysigns.features.quiz.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.demacia.cysigns.data.Signs
import com.demacia.cysigns.features.quiz.data.QuizQuestion
import com.demacia.cysigns.features.quiz.domain.QuizRepository
import com.demacia.cysigns.features.quiz.ui.Action.PerkeleGame
import com.demacia.cysigns.features.quiz.ui.Action.SaveAllQuestions
import com.demacia.cysigns.features.quiz.ui.Action.SetQuestion
import com.demacia.cysigns.features.quiz.ui.Action.SetSelectedAnswer
import com.demacia.cysigns.features.quiz.ui.Action.StartNewGame
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.Channel
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
            is Event.Ui.OnSignClick -> onSignClicked(event.signOrdinal)
            is Event.Ui.OnNextClick -> onNextClick(state.value)
            is Event.Ui.OnNewGameClick -> loadNewGame()
            is Event.Ui.OnInfoClick -> onInfoClick()
        }
    }

    private suspend fun handleInternalEvent(event: Event.Internal) {
        when (event) {
            is Event.Internal.OnInit -> init()
            is Event.Internal.SetMode -> reduce(Action.SetMode(event.mode))
        }
    }

    private suspend fun init() {
        loadNewGame()
    }

    private suspend fun loadNewGame() {
        val allQuestions = quizRepository.getQuestions().shuffled()
        val question = allQuestions[0]
        reduce(StartNewGame(allQuestions, question))
    }

    private suspend fun onSignClicked(signOrdinal: Int) {
        if (state.value.selectedSign != null) return

        checkAnswer(signOrdinal)
    }

    private suspend fun onNextClick(state: QuizState) {
        if (state.currentQuestionIndex == state.allQuestions.lastIndex) {
            reduce(PerkeleGame)
        } else {
            loadNextQuestion(state)
        }
    }

    private suspend fun onInfoClick() {
        _effect.send(UiEffect.OpenInfo)
    }

    private suspend fun loadNextQuestion(state: QuizState) {
        val nextIndex = state.currentQuestionIndex + 1
        val question = state.allQuestions[nextIndex]
        reduce(SetQuestion(question, state.currentQuestionIndex + 1))
    }

    private suspend fun checkAnswer(signOrdinal: Int) {
        val selectedSign = Signs.entries.toTypedArray()[signOrdinal]
        val correctSign = state.value.correctSign
        val isCorrectAnswer = correctSign == selectedSign

        reduce(SetSelectedAnswer(selectedSign, isCorrectAnswer))
    }

    private suspend fun reduce(action: Action) {
        val newValue = when (action) {
            is SaveAllQuestions -> state.updateAndGet {
                it.copy(
                    allQuestions = action.questions,
                )
            }

            is SetQuestion -> state.updateAndGet {
                it.copy(
                    correctSign = action.question.correctSign,
                    incorrectSigns = action.question.incorrectSigns,
                    shuffledSigns = (action.question.incorrectSigns + action.question.correctSign).shuffled(),
                    selectedSign = null,
                    currentQuestionIndex = action.index,
                )
            }

            is SetSelectedAnswer -> state.updateAndGet {
                it.copy(
                    selectedSign = action.sign,
                    correctAnswers = state.value.correctAnswers.run { if (action.isCorrect) this + 1 else this },
                    incorrectAnswers = state.value.incorrectAnswers.run { if (!action.isCorrect) this + 1 else this },
                )
            }

            is StartNewGame -> state.updateAndGet {
                it.copy(
                    allQuestions = action.questions,
                    currentQuestionIndex = 0,
                    correctSign = action.firstQuestion.correctSign,
                    incorrectSigns = action.firstQuestion.incorrectSigns,
                    shuffledSigns = (action.firstQuestion.incorrectSigns + action.firstQuestion.correctSign).shuffled(),
                    selectedSign = null,
                    correctAnswers = 0,
                    incorrectAnswers = 0,
                    isFinished = false,
                )
            }

            is PerkeleGame -> state.updateAndGet {
                it.copy(isFinished = true)
            }

            is Action.SetMode -> state.updateAndGet {
                it.copy(mode = action.mode)
            }
        }
        state.emit(newValue)
    }
}

sealed interface Event {
    sealed interface Ui : Event {
        data class OnSignClick(val signOrdinal: Int) : Ui
        data object OnNextClick : Ui
        data object OnNewGameClick : Ui
        data object OnInfoClick : Ui
    }

    sealed interface Internal : Event {
        data object OnInit : Internal
        data class SetMode(val mode: Mode) : Internal
    }
}

private sealed interface Action {
    data class SetMode(val mode: Mode) : Action

    data class StartNewGame(
        val questions: List<QuizQuestion>,
        val firstQuestion: QuizQuestion,
    ) : Action

    data class SaveAllQuestions(val questions: List<QuizQuestion>) : Action
    data class SetSelectedAnswer(val sign: Signs, val isCorrect: Boolean) : Action
    data class SetQuestion(val question: QuizQuestion, val index: Int) : Action
    data object PerkeleGame : Action
}

sealed interface UiEffect {
    data class ShowToast(val text: String) : UiEffect
    data object OpenInfo : UiEffect
}
