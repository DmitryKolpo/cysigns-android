package com.demacia.cysigns.features.quiz

import android.widget.Toast
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import com.demacia.cysigns.features.quiz.ui.Event
import com.demacia.cysigns.features.quiz.ui.Mode
import com.demacia.cysigns.features.quiz.ui.QuizContent
import com.demacia.cysigns.features.quiz.ui.QuizViewModel
import com.demacia.cysigns.features.quiz.ui.UiEffect

@Composable
fun QuizScreen(
    mode: String,
    openInfo: () -> Unit,
    viewModel: QuizViewModel = hiltViewModel(),
) {
    val uiState = viewModel.uiState.collectAsState(initial = null).value
        ?: return Box(modifier = Modifier.fillMaxSize())

    LaunchedEffect(Unit) {
        val quizMode = when (mode) {
            "name" -> Mode.ByName
            "picture" -> Mode.ByPicture
            else -> throw IllegalArgumentException("Incorrect quiz mode. Passed mode:$mode")
        }
        viewModel.sendEvent(Event.Internal.SetMode(quizMode))
    }

    val context = LocalContext.current
    LaunchedEffect(Unit) {
        viewModel.effect.collect {
            when (it) {
                is UiEffect.ShowToast -> Toast.makeText(context, it.text, Toast.LENGTH_SHORT).show()
                is UiEffect.OpenInfo -> openInfo()
            }
        }
    }

    QuizContent(
        uiState = uiState,
        sendEvent = viewModel::sendEvent,
    )
}

