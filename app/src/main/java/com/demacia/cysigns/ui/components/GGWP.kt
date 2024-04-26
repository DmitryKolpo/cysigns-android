package com.demacia.cysigns.ui.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.safeDrawingPadding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ThumbUp
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.demacia.cysigns.R
import com.demacia.cysigns.ui.models.Statistic
import com.demacia.cysigns.utils.Spacer
import com.demacia.cysigns.utils.resource


@Composable
fun GGWP(
    statistic: Statistic,
    onClick: () -> Unit,
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
            text = "${statistic.correct} / ${statistic.total}",
            style = MaterialTheme.typography.titleMedium,
        )
        Spacer(1f)
        PrimaryButton(
            enabled = true,
            title = R.string.new_game.resource(),
            onClick = onClick,
        )
        Spacer(height = 16.dp)
    }
}