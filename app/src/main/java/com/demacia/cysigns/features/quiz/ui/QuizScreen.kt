package com.demacia.cysigns.features.quiz.ui

import android.widget.Toast
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawingPadding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.ThumbUp
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.demacia.cysigns.R
import com.demacia.cysigns.data.Signs
import com.demacia.cysigns.ui.components.PrimaryButton
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
        is QuizUiState.Finished -> GGWP(uiState, sendEvent)
    }
}

@Composable
private fun GGWP(
    uiState: QuizUiState.Finished,
    sendEvent: (Event.Ui) -> Unit,
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .fillMaxSize()
            .safeDrawingPadding()
    ) {
        Spacer(1f)
        Icon(
            imageVector = Icons.Default.ThumbUp,
            contentDescription = null,
            tint = Color(255, 191, 0),
            modifier = Modifier
                .size(100.dp)
                .align(Alignment.CenterHorizontally)
        )
        Spacer(height = 16.dp)
        Text(
            text = R.string.congratulations.resource(),
            style = MaterialTheme.typography.titleLarge,
        )
        Spacer(height = 16.dp)
        Text(
            text = R.string.score.resource(),
            style = MaterialTheme.typography.titleMedium,
        )
        Spacer(height = 8.dp)
        Text(
            text = "${uiState.statistic.correct} / ${uiState.statistic.total}",
            style = MaterialTheme.typography.titleMedium,
        )
        Spacer(1f)
        PrimaryButton(
            enabled = true,
            title = R.string.new_game.resource(),
            onClick = { sendEvent(Event.Ui.OnNewGameClick) },
        )
        Spacer(height = 16.dp)
    }
}

@Composable
private fun Data(
    uiState: QuizUiState.Content,
    sendEvent: (Event.Ui) -> Unit,
) {
    Column(
        Modifier
            .fillMaxSize()
            .safeDrawingPadding()
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.fillMaxWidth()
        ) {
            StatisticItem(
                uiState.statistic,
                modifier = Modifier
                    .padding(horizontal = 16.dp)
                    .weight(1f)
            )
            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier
                    .size(50.dp)
                    .clickable { sendEvent(Event.Ui.OnInfoClick) }
            ) {
                Icon(
                    imageVector = Icons.Default.Info,
                    contentDescription = null,
                    modifier = Modifier.size(32.dp)
                )
            }
        }
        Image(
            painter = uiState.image.painter(),
            contentDescription = null,
            contentScale = ContentScale.Fit,
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
        PrimaryButton(
            enabled = uiState.nextButtonEnabled,
            title = R.string.next_button.resource(),
            onClick = { sendEvent(Event.Ui.OnNextClick) },
        )
        Spacer(height = 16.dp)
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
            Spacer(1f)
            Icon(
                imageVector = Icons.Default.Check,
                contentDescription = null,
                tint = Color.Green,
            )
            Text(
                text = statistic.correct.toString(),
            )
            Spacer(width = 4.dp)
            Icon(
                imageVector = Icons.Default.Close,
                contentDescription = null,
                tint = Color.Red,
            )
            Text(
                text = statistic.incorrect.toString(),
            )
        }
        Spacer(height = 4.dp)
        LinearProgressIndicator(
            progress = { statistic.current.toFloat() / statistic.total.toFloat() },
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(50))
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
                statistic = Statistic(11, 150, 5, 1),
                nextButtonEnabled = true,
            ),
            sendEvent = {}
        )
    }
}

@Preview
@Composable
private fun PreviewFinish() {
    CySignsTheme {
        Content(
            uiState = QuizUiState.Finished(Statistic(11, 150, 5, 1)),
            sendEvent = {}
        )
    }
}