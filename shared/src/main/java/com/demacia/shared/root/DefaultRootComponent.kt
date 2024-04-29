package com.demacia.shared.root

import android.os.Parcelable
import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.router.stack.ChildStack
import com.arkivanov.decompose.router.stack.StackNavigation
import com.arkivanov.decompose.router.stack.childStack
import com.arkivanov.decompose.router.stack.pop
import com.arkivanov.decompose.router.stack.push
import com.arkivanov.decompose.value.Value
import com.demacia.shared.components.game.DefaultGameComponent
import com.demacia.shared.components.game.GameMode
import com.demacia.shared.components.home.DefaultHomeComponent
import com.demacia.shared.components.info.DefaultInfoComponent
import com.demacia.shared.root.RootComponent.*
import kotlinx.parcelize.Parcelize
import kotlinx.serialization.Serializable

class DefaultRootComponent(
    componentContext: ComponentContext,
) : RootComponent, ComponentContext by componentContext {

    private val navigation = StackNavigation<Config>()

    override val stack: Value<ChildStack<*, Child>> =
        childStack(
            source = navigation,
            initialConfiguration = Config.Home,
            handleBackButton = true,
            childFactory = ::createChild,
            serializer = null,
        )

    private fun createChild(
        config: Config,
        childComponentContext: ComponentContext,
    ): Child =
        when (config) {
            Config.Home -> Child.Home(homeComponent(childComponentContext))
            is Config.Game -> Child.Game(gameComponent(childComponentContext, config))
            Config.Info -> Child.Info(infoComponent(childComponentContext))
        }

    private fun homeComponent(componentContext: ComponentContext) =
        DefaultHomeComponent(
            componentContext = componentContext,
            openNameGameScreen = { navigation.push(Config.Game(GameMode.ByName)) },
            openPictureGameScreen = { navigation.push(Config.Game(GameMode.ByPicture)) }
        )

    private fun gameComponent(
        componentContext: ComponentContext,
        config: Config.Game,
    ) = DefaultGameComponent(
        componentContext = componentContext,
        openInfoScreen = {
            navigation.pop()
//            navigation.push(Config.Info)
        },
        mode = config.gameMode,
    )

    private fun infoComponent(componentContext: ComponentContext) =
        DefaultInfoComponent(
            componentContext = componentContext,
        )
}

private sealed interface Config : Parcelable {
    @Parcelize
    data object Home : Config

    @Parcelize
    data class Game(
        val gameMode: GameMode,
    ) : Config

    @Parcelize
    data object Info : Config
}