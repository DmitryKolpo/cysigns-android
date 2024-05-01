package com.demacia.cysigns.features.home

import androidx.compose.runtime.Composable
import com.demacia.cysigns.features.home.ui.HomeContent

@Composable
fun HomeScreen(
    startPictureQuiz: () -> Unit,
    startNameQuiz: () -> Unit,
) {
    HomeContent(
        onStartPictureClicked = startPictureQuiz,
        onStartNameClicked = startNameQuiz,
    )
}
