package com.demacia.shared.components.game

import com.arkivanov.decompose.ComponentContext

class DefaultGameComponent(
    componentContext: ComponentContext,
    override val mode: GameMode,
    private val openInfoScreen: () -> Unit,
) : GameComponent, ComponentContext by componentContext {
    override fun onInfoClicked() = openInfoScreen()
}