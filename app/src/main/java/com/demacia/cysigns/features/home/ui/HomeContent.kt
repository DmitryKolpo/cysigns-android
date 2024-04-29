package com.demacia.cysigns.features.home.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.demacia.cysigns.ui.components.PrimaryButton
import com.demacia.cysigns.utils.Spacer


@Composable
internal fun HomeContent(
    onStartPictureClicked: () -> Unit,
    onStartNameClicked: () -> Unit,
) {
    Column(
        verticalArrangement = Arrangement.Center,
        modifier = Modifier.fillMaxSize()
    ) {
        PrimaryButton(
            title = "Start New Picture Game",
            onClick = onStartPictureClicked,
        )
        Spacer(16.dp)
        PrimaryButton(
            title = "Start New Name Game",
            onClick = onStartNameClicked,
        )
    }
}

@Preview
@Composable
private fun Preview() {
    MaterialTheme {
        HomeContent(
            {}, {},
        )
    }
}