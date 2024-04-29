package com.demacia.shared.root

import com.arkivanov.decompose.router.stack.ChildStack
import com.arkivanov.decompose.value.Value
import com.demacia.shared.components.game.GameComponent
import com.demacia.shared.components.home.HomeComponent
import com.demacia.shared.components.info.InfoComponent

interface RootComponent {
    val stack: Value<ChildStack<*, Child>>

    sealed class Child {
        class Home(val component: HomeComponent) : Child()
        class Game(val component: GameComponent) : Child()
        class Info(val component: InfoComponent) : Child()
    }
}