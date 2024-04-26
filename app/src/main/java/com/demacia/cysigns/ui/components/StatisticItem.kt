package com.demacia.cysigns.ui.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Icon
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.demacia.cysigns.ui.models.Statistic
import com.demacia.cysigns.utils.Spacer

@Composable
fun StatisticItem(
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