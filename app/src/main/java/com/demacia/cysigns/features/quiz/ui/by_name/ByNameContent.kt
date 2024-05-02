package com.demacia.cysigns.features.quiz.ui.by_name

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.demacia.cysigns.R
import com.demacia.cysigns.data.Signs
import com.demacia.cysigns.features.quiz.ui.Event
import com.demacia.cysigns.features.quiz.ui.QuizByNameUiAnswer
import com.demacia.cysigns.features.quiz.ui.QuizContent
import com.demacia.cysigns.features.quiz.ui.QuizUiAnswer
import com.demacia.cysigns.features.quiz.ui.QuizUiState
import com.demacia.cysigns.ui.models.QuizUiAnswerColor
import com.demacia.cysigns.ui.models.Statistic
import com.demacia.cysigns.ui.models.toColor
import com.demacia.cysigns.ui.theme.CySignsTheme
import com.demacia.cysigns.utils.Spacer
import com.demacia.cysigns.utils.painter
import com.demacia.cysigns.utils.resource

@Composable
fun ColumnScope.ByNameContent(
    uiState: QuizUiState.Content.ByName,
    sendEvent: (Event.Ui) -> Unit,
) {
    val shape = MaterialTheme.shapes.large
    val borderColor = Color(3, 3, 5, 15)
    Spacer(height = 16.dp)
    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier
            .fillMaxWidth()
            .weight(1f)
            .padding(horizontal = 16.dp)
            .background(Color.White, shape)
            .border(1.dp, borderColor, shape)
    ) {
        Text(
            text = uiState.name.resource(),
            style = MaterialTheme.typography.titleLarge,
            textAlign = TextAlign.Center,
        )
    }
    Spacer(height = 8.dp)
    uiState.answers.chunked(2).forEach {
        AnswersRow(it, sendEvent)
        Spacer(height = 8.dp)
    }
}

@Composable
private fun AnswersRow(
    answers: List<QuizByNameUiAnswer>,
    sendEvent: (Event.Ui) -> Unit,
    modifier: Modifier = Modifier,
) {
    Row(
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp)
    ) {
        answers.forEach {
            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier
                    .weight(1f)
                    .aspectRatio(1f)
                    .clip(MaterialTheme.shapes.large)
                    .background(it.color.toColor())
                    .clickable { sendEvent(Event.Ui.OnSignClick(it.signOrdinal)) }
            ) {
                Image(
                    it.signImageRes.painter(),
                    null,
                    modifier = Modifier.fillMaxSize(0.8f)
                )
            }
        }
    }
}

@Preview
@Composable
private fun Preview() {
    CySignsTheme {
        QuizContent(
            uiState = QuizUiState.Content.ByName(
                name = Signs.Maximumspeedlimit.signName,
                answers = listOf(
                    QuizByNameUiAnswer(
                        Signs.Maximumspeedlimit.ordinal,
                        QuizUiAnswerColor.Neutral,
                        Signs.Maximumspeedlimit.imageResId,
                    ),
                    QuizByNameUiAnswer(
                        Signs.Maximumspeedlimit.ordinal,
                        QuizUiAnswerColor.Neutral,
                        Signs.Maximumspeedlimit.imageResId,
                    ),
                    QuizByNameUiAnswer(
                        Signs.Maximumspeedlimit.ordinal,
                        QuizUiAnswerColor.Neutral,
                        Signs.Maximumspeedlimit.imageResId,
                    ),
                    QuizByNameUiAnswer(
                        Signs.Maximumspeedlimit.ordinal,
                        QuizUiAnswerColor.Neutral,
                        Signs.Maximumspeedlimit.imageResId,
                    ),
                ),
                statistic = Statistic(11, 150, 5, 1),
                nextButtonEnabled = true,
            ),
            sendEvent = {}
        )
    }
}