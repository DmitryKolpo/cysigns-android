package com.demacia.cysigns.features.quiz.ui.by_picture

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.demacia.cysigns.R
import com.demacia.cysigns.data.Signs
import com.demacia.cysigns.features.quiz.ui.Event
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
internal fun ColumnScope.ByPictureContent(
    uiState: QuizUiState.Content.ByPicture,
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
        Image(
            painter = uiState.image.painter(),
            contentDescription = null,
            contentScale = ContentScale.Fit,
            modifier = Modifier
                .fillMaxSize(0.75f)
        )
    }
    Spacer(height = 8.dp)
    uiState.answers.forEach {
        Box(
            modifier = Modifier.height(intrinsicSize = IntrinsicSize.Min)
        ) {
            FakeTwoLines()
            AnswerButton(
                answer = it,
                sendEvent = sendEvent,
            )
        }
        Spacer(height = 8.dp)
    }
}

@Composable
private fun FakeTwoLines() {
    Text(
        text = "\n",
        onClick = {},
        modifier = Modifier.background(Color.Transparent)
    )
}

@Composable
private fun AnswerButton(
    answer: QuizUiAnswer,
    sendEvent: (Event.Ui) -> Unit,
) {
    Text(
        text = answer.signName.resource(),
        onClick = { sendEvent(Event.Ui.OnSignClick(answer.signOrdinal)) },
        color = answer.color.toColor(),
        modifier = Modifier.fillMaxSize()
    )
}

@Composable
private fun Text(
    text: String,
    onClick: () -> Unit,
    color: Color = Color.Transparent,
    modifier: Modifier = Modifier,
) {
    Box(
        contentAlignment = Alignment.Center,
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp)
            .background(color, MaterialTheme.shapes.large)
            .border(1.dp, Color(3, 3, 5, 15), MaterialTheme.shapes.large)
            .clip(MaterialTheme.shapes.large)
            .clickable { onClick() }
            .padding(12.dp)
    ) {
        Text(
            text = text,
            color = Color.Black,
            textAlign = TextAlign.Center,
        )
    }
}

@Preview
@Composable
private fun Preview() {
    CySignsTheme {
        QuizContent(
            uiState = QuizUiState.Content.ByPicture(
                image = R.drawable.cyprus_road_sign_maximum_speed,
                answers = listOf(
                    QuizUiAnswer(
                        Signs.Maximumspeedlimit.ordinal,
                        QuizUiAnswerColor.Neutral,
                        R.string.maximum_speed_limit,
                    ),
                    QuizUiAnswer(
                        Signs.Maximumspeedlimit.ordinal,
                        QuizUiAnswerColor.Neutral,
                        R.string.maximum_speed_limit,
                    ),
                    QuizUiAnswer(
                        Signs.Maximumspeedlimit.ordinal,
                        QuizUiAnswerColor.Neutral,
                        R.string.maximum_speed_limit,
                    ),
                    QuizUiAnswer(
                        Signs.Maximumspeedlimit.ordinal,
                        QuizUiAnswerColor.Neutral,
                        R.string.maximum_speed_limit,
                    ),
                ),
                statistic = Statistic(11, 150, 5, 1),
                nextButtonEnabled = true,
            ),
            sendEvent = {}
        )
    }
}