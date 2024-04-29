package com.demacia.cysigns.features.quiz

import android.widget.Toast
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import com.demacia.cysigns.features.quiz.ui.Event
import com.demacia.cysigns.features.quiz.ui.Mode
import com.demacia.cysigns.features.quiz.ui.Mode.Companion.toUiMode
import com.demacia.cysigns.features.quiz.ui.QuizContent
import com.demacia.cysigns.features.quiz.ui.QuizViewModel
import com.demacia.cysigns.features.quiz.ui.UiEffect
import com.demacia.shared.components.game.GameComponent

@Composable
fun QuizScreen(
    component: GameComponent,
    viewModel: QuizViewModel = hiltViewModel(),
) {
    val uiState = viewModel.uiState.collectAsState(initial = null).value
        ?: return Box(modifier = Modifier.fillMaxSize())

    LaunchedEffect(Unit) {
        viewModel.sendEvent(Event.Internal.SetMode(component.mode.toUiMode()))
    }

    val context = LocalContext.current
    LaunchedEffect(Unit) {
        viewModel.effect.collect {
            when (it) {
                is UiEffect.ShowToast -> Toast.makeText(context, it.text, Toast.LENGTH_SHORT).show()
                is UiEffect.OpenInfo -> {
                    component.onInfoClicked()
                }
            }
        }
    }

    QuizContent(
        uiState = uiState,
        sendEvent = viewModel::sendEvent,
    )
}

