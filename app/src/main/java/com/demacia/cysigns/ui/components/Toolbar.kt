package com.demacia.cysigns.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.demacia.cysigns.utils.painter

@Composable
fun Toolbar(
    title: String,
    onBackClick: (() -> Unit)?,
    modifier: Modifier = Modifier,
) {
    Row(
        modifier = modifier
            .height(intrinsicSize = IntrinsicSize.Min)
            .fillMaxWidth()
    ) {
        if (onBackClick != null) {
            StartIcon(onClick = onBackClick)
        }
        Title(title)
    }
}

@Composable
private fun StartIcon(
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Icon(
        painter = Icons.AutoMirrored.Filled.ArrowBack.painter(),
        contentDescription = null,
        tint = Color.Black,
        modifier = modifier
            .fillMaxHeight()
            .aspectRatio(1f)
            .clickable { onClick() }
            .padding(8.dp)
    )
}

@Composable
private fun Title(
    title: String,
    modifier: Modifier = Modifier
) {
    Box(
        contentAlignment = Alignment.Center,
        modifier = modifier
            .fillMaxHeight()
    ) {
        Text(
            text = title,
            style = MaterialTheme.typography.titleLarge,
            color = Color.Black,
        )
    }
}

@Preview
@Composable
private fun Preview() {
    MaterialTheme {
        Toolbar(
            onBackClick = {},
            title = "Title",
        )
    }
}