package com.demacia.cysigns.features.home.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.demacia.cysigns.R
import com.demacia.cysigns.ui.components.PrimaryButton
import com.demacia.cysigns.utils.Spacer
import com.demacia.cysigns.utils.resource

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
            title = R.string.start_new_picture_game.resource(),
            onClick = onStartPictureClicked,
        )
        Spacer(16.dp)
        PrimaryButton(
            title = R.string.start_new_name_game.resource(),
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