package com.demacia.shared.components.game

interface GameComponent {

    val mode: GameMode

    fun onInfoClicked()
}