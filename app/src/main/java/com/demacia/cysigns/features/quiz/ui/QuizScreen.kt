package com.demacia.cysigns.features.quiz.ui

import android.widget.Toast
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.demacia.cysigns.R
import com.demacia.cysigns.data.Signs
import com.demacia.cysigns.ui.theme.CySignsTheme
import com.demacia.cysigns.utils.Spacer
import com.demacia.cysigns.utils.painter
import com.demacia.cysigns.utils.resource

@Composable
fun QuizScreen(
    viewModel: QuizViewModel = hiltViewModel()
) {
    val uiState = viewModel.uiState.collectAsState(initial = null).value
        ?: return Box(modifier = Modifier.fillMaxSize())

    val context = LocalContext.current
    LaunchedEffect(Unit) {
        viewModel.effect.collect {
            when (it) {
                is UiEffect.ShowToast -> Toast.makeText(context, it.text, Toast.LENGTH_SHORT).show()
            }
        }
    }

    Content(
        uiState = uiState,
        sendEvent = viewModel::sendEvent,
    )
}

@Composable
private fun Content(
    uiState: QuizUiState,
    sendEvent: (Event.Ui) -> Unit,
) {
    when (uiState) {
        is QuizUiState.Loading -> Loading()
        is QuizUiState.Content -> Data(uiState, sendEvent)
    }

}

@Composable
private fun Data(
    uiState: QuizUiState.Content,
    sendEvent: (Event.Ui) -> Unit,
) {
    Column(
        Modifier.fillMaxSize()
    ) {
        Spacer(height = 16.dp)
        StatisticItem(
            uiState.statistic,
            modifier = Modifier
                .padding(horizontal = 16.dp)
                .fillMaxWidth()
        )
        Image(
            painter = uiState.image.painter(),
            contentDescription = null,
            contentScale = ContentScale.Inside,
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
                .weight(1f)
        )
        uiState.answers.forEach {
            AnswerButton(
                answer = it,
                sendEvent = sendEvent,
            )
        }
        Spacer(height = 8.dp)
    }
}

@Composable
private fun StatisticItem(
    statistic: Statistic,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier,
    ) {
        Row {
            Text(text = "${statistic.current} / ${statistic.total}")
        }
        Spacer(height = 4.dp)
        LinearProgressIndicator(
            progress = { statistic.current.toFloat() / statistic.total.toFloat() },
            modifier = Modifier.fillMaxWidth()
        )
    }
}

@Composable
private fun AnswerButton(
    answer: QuizViewModel.QuizUiAnswer,
    sendEvent: (Event.Ui) -> Unit,
) {
    val bgColor = when (answer.color) {
        QuizViewModel.QuizUiAnswerColor.Neutral -> Color.White
        QuizViewModel.QuizUiAnswerColor.Correct -> Color.Green
        QuizViewModel.QuizUiAnswerColor.Incorrect -> Color.Red
    }

    Button(
        onClick = { sendEvent(Event.Ui.OnSignClick(answer.signOrdinal)) },
        colors = ButtonDefaults.outlinedButtonColors().copy(
            containerColor = bgColor,
        ),
        border = BorderStroke(2.dp, Color.Black),
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 4.dp)
    ) {
        Text(
            text = answer.signName.resource(),
            color = Color.Black,
        )
    }
}

@Composable
private fun Loading() {
    Box(modifier = Modifier.fillMaxSize())
}

@Preview
@Composable
private fun Preview() {
    CySignsTheme {
        Content(
            uiState = QuizUiState.Content(
                image = R.drawable.cyprus_road_sign_maximum_speed,
                answers = listOf(
                    QuizViewModel.QuizUiAnswer(
                        R.string.maximum_speed_limit,
                        Signs.Maximumspeedlimit.ordinal,
                        QuizViewModel.QuizUiAnswerColor.Neutral,
                    ),
                    QuizViewModel.QuizUiAnswer(
                        R.string.maximum_speed_limit,
                        Signs.Maximumspeedlimit.ordinal,
                        QuizViewModel.QuizUiAnswerColor.Neutral,
                    ),
                    QuizViewModel.QuizUiAnswer(
                        R.string.maximum_speed_limit,
                        Signs.Maximumspeedlimit.ordinal,
                        QuizViewModel.QuizUiAnswerColor.Neutral,
                    ),
                    QuizViewModel.QuizUiAnswer(
                        R.string.maximum_speed_limit,
                        Signs.Maximumspeedlimit.ordinal,
                        QuizViewModel.QuizUiAnswerColor.Neutral,
                    ),
                ),
                statistic = Statistic(11, 150)
            ),
            sendEvent = {}
        )
    }
}