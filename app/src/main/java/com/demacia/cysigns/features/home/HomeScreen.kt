package com.demacia.cysigns.features.home

import androidx.compose.runtime.Composable
import com.demacia.cysigns.features.home.ui.HomeContent
import com.demacia.shared.components.home.HomeComponent

@Composable
fun HomeScreen(
    component: HomeComponent,
) {
    HomeContent(
        onStartPictureClicked = { component.onStartPictureGameClicked() },
        onStartNameClicked = { component.onStartNameGameClicked() },
    )
}
