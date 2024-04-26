package com.demacia.cysigns.features.quiz.ui.by_name

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.Row
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
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.demacia.cysigns.features.quiz.ui.Event
import com.demacia.cysigns.features.quiz.ui.QuizByNameUiAnswer
import com.demacia.cysigns.features.quiz.ui.QuizUiState
import com.demacia.cysigns.ui.models.toColor
import com.demacia.cysigns.utils.painter
import com.demacia.cysigns.utils.resource

@Composable
fun ColumnScope.ByNameContent(
    uiState: QuizUiState.Content.ByName,
    sendEvent: (Event.Ui) -> Unit,
) {
    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier.weight(1f)
    ) {
        Text(
            text = uiState.name.resource(),
            style = MaterialTheme.typography.titleLarge,
            textAlign = TextAlign.Center,
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        )
    }
    uiState.answers.chunked(2).forEach {
        AnswersRow(it, sendEvent, modifier = Modifier.weight(1f))
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
        modifier = modifier.fillMaxWidth()
    ) {
        answers.forEach {
            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier
                    .fillMaxHeight()
                    .weight(1f)
                    .padding(16.dp)
                    .clip(MaterialTheme.shapes.large)
                    .background(it.color.toColor())
                    .clickable { sendEvent(Event.Ui.OnSignClick(it.signOrdinal)) }
                    .padding(16.dp)
            ) {
                Image(
                    it.signImageRes.painter(),
                    null,
                    modifier = Modifier.fillMaxSize()
                )
            }
        }
    }
}