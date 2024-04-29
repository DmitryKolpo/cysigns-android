package com.demacia.cysigns.features.info.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.demacia.cysigns.data.Signs
import com.demacia.cysigns.utils.Spacer
import com.demacia.cysigns.utils.painter
import com.demacia.cysigns.utils.resource

@Composable
internal fun InfoContent(
    state: InfoUiState,
) {
    LazyColumn(
        verticalArrangement = Arrangement.spacedBy(8.dp),
        contentPadding = PaddingValues(top = 16.dp, bottom = 32.dp, start = 8.dp, end = 8.dp),
        modifier = Modifier
            .fillMaxSize()
            .statusBarsPadding()
    ) {
        items(state.signs.count()) {
            val item = state.signs[it]
            SignItem(
                painter = item.imageRes.painter(),
                text = item.name,
            )
        }
    }
}

@Composable
private fun SignItem(
    painter: Painter,
    text: String,
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp)
            .height(IntrinsicSize.Max)
    ) {
        SignIcon(painter)
        Spacer(8.dp)
        Text(
            text = text,
            color = Color.Black,
            style = MaterialTheme.typography.bodySmall,
            textAlign = TextAlign.Center,
            overflow = TextOverflow.Ellipsis,
            modifier = Modifier
                .weight(1f)
                .align(Alignment.CenterVertically)
        )
    }
}

@Composable
private fun SignIcon(
    painter: Painter,
) {
    Box(modifier = Modifier.size(32.dp)) {
        Image(
            painter = painter,
            contentDescription = null,
            contentScale = ContentScale.Inside,
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun Preview() {
    MaterialTheme {
        InfoContent(
            state = InfoUiState(
                Signs.entries.map {
                    InfoSignUi(
                        imageRes = it.imageResId,
                        name = it.signName.resource(),
                    )
                },
            )
        )
    }
}