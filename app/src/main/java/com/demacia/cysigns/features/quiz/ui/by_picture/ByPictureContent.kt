package com.demacia.cysigns.features.quiz.ui.by_picture

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import com.demacia.cysigns.features.quiz.ui.Event
import com.demacia.cysigns.features.quiz.ui.QuizUiAnswer
import com.demacia.cysigns.features.quiz.ui.QuizUiState
import com.demacia.cysigns.ui.models.toColor
import com.demacia.cysigns.utils.painter
import com.demacia.cysigns.utils.resource

@Composable
internal fun ColumnScope.ByPictureContent(
    uiState: QuizUiState.Content.ByPicture,
    sendEvent: (Event.Ui) -> Unit,
) {
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
}

@Composable
private fun AnswerButton(
    answer: QuizUiAnswer,
    sendEvent: (Event.Ui) -> Unit,
) {
    Button(
        onClick = { sendEvent(Event.Ui.OnSignClick(answer.signOrdinal)) },
        colors = ButtonDefaults.outlinedButtonColors().copy(
            containerColor = answer.color.toColor(),
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