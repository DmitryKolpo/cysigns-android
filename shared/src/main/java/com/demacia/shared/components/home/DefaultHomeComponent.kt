package com.demacia.shared.components.home

import com.arkivanov.decompose.ComponentContext

class DefaultHomeComponent(
    componentContext: ComponentContext,
    private val openPictureGameScreen: () -> Unit,
    private val openNameGameScreen: () -> Unit,
) : HomeComponent, ComponentContext by componentContext {

    override fun onStartPictureGameClicked() {
        openPictureGameScreen()
    }

    override fun onStartNameGameClicked() {
        openNameGameScreen()
    }
}