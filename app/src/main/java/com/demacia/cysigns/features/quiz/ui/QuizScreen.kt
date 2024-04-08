package com.demacia.cysigns.features.quiz.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import com.demacia.cysigns.ui.theme.CySignsTheme

@Composable
fun QuizScreen(
    viewModel: QuizViewModel = hiltViewModel()
) {
    //state
    Content()
}

@Composable
private fun Content() {
    Column(
        Modifier
            .fillMaxSize()
            .background(Color.Red)
    ) {

    }
}

@Preview
@Composable
private fun Preview() {
    CySignsTheme {
        Content()
    }
}