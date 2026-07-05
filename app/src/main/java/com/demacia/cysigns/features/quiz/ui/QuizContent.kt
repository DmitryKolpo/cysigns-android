package com.demacia.cysigns.features.quiz.ui

import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawingPadding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Info
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ripple
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.demacia.cysigns.R
import com.demacia.cysigns.data.Signs
import com.demacia.cysigns.features.quiz.ui.by_name.ByNameContent
import com.demacia.cysigns.features.quiz.ui.by_picture.ByPictureContent
import com.demacia.cysigns.ui.components.GGWP
import com.demacia.cysigns.ui.components.PrimaryButton
import com.demacia.cysigns.ui.components.StatisticItem
import com.demacia.cysigns.ui.models.QuizUiAnswerColor
import com.demacia.cysigns.ui.models.Statistic
import com.demacia.cysigns.ui.theme.CySignsTheme
import com.demacia.cysigns.utils.Spacer
import com.demacia.cysigns.utils.resource

@Composable
internal fun QuizContent(
    uiState: QuizUiState,
    sendEvent: (Event.Ui) -> Unit,
) {
    when (uiState) {
        is QuizUiState.Loading -> Loading()
        is QuizUiState.Content -> Data(uiState, sendEvent)
        is QuizUiState.Finished -> GGWP(uiState.statistic) { sendEvent(Event.Ui.OnNewGameClick) }
    }
}

@Composable
private fun Loading() {
    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier.fillMaxSize()
    ) {
        CircularProgressIndicator(
            color = MaterialTheme.colorScheme.primary,
        )
    }
}

@Composable
private fun Data(
    uiState: QuizUiState.Content,
    sendEvent: (Event.Ui) -> Unit,
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .safeDrawingPadding()
    ) {
        TopPart(uiState.statistic, sendEvent)
        MiddlePart(uiState, sendEvent)
        PrimaryButton(
            enabled = uiState.nextButtonEnabled,
            title = R.string.next_button.resource(),
            onClick = { sendEvent(Event.Ui.OnNextClick) },
        )
        Spacer(height = 16.dp)
    }
}

@Composable
private fun TopPart(
    statistic: Statistic,
    sendEvent: (Event.Ui) -> Unit,
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier.fillMaxWidth()
    ) {
        StatisticItem(
            statistic,
            modifier = Modifier
                .padding(horizontal = 16.dp)
                .weight(1f)
        )
        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier
                .size(50.dp)
                .clickable(
                    onClick = { sendEvent(Event.Ui.OnInfoClick) },
                    interactionSource = remember { MutableInteractionSource() },
                    indication = ripple(bounded = false),
                )
        ) {
            Icon(
                imageVector = Icons.Default.Info,
                contentDescription = null,
                tint = MaterialTheme.colorScheme.onBackground,
                modifier = Modifier.size(32.dp)
            )
        }
    }
}

@Composable
private fun ColumnScope.MiddlePart(
    uiState: QuizUiState.Content,
    sendEvent: (Event.Ui) -> Unit,
) {
    when (uiState) {
        is QuizUiState.Content.ByPicture -> ByPictureContent(uiState, sendEvent)
        is QuizUiState.Content.ByName -> ByNameContent(uiState, sendEvent)
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

@Preview
@Composable
private fun PreviewFinish() {
    CySignsTheme {
        QuizContent(
            uiState = QuizUiState.Finished(Statistic(11, 150, 5, 1)),
            sendEvent = {}
        )
    }
}