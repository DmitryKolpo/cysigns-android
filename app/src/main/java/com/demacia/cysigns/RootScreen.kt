package com.demacia.cysigns

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.arkivanov.decompose.extensions.compose.stack.Children
import com.demacia.cysigns.features.home.HomeScreen
import com.demacia.cysigns.features.info.InfoScreen
import com.demacia.cysigns.features.quiz.QuizScreen
import com.demacia.cysigns.ui.theme.CySignsTheme
import com.demacia.shared.root.RootComponent
import com.demacia.shared.root.RootComponent.Child

@Composable
fun RootScreen(
    component: RootComponent,
) {
    CySignsTheme {
        Surface(
            color = MaterialTheme.colorScheme.background,
            modifier = Modifier.fillMaxSize()
        ) {
            Children(
                stack = component.stack,
                modifier = Modifier.fillMaxSize()
            ) {
                when (val instance = it.instance) {
                    is Child.Home -> HomeScreen(component = instance.component)
                    is Child.Game -> QuizScreen(component = instance.component)
                    is Child.Info -> InfoScreen(component = instance.component)
                }
            }
        }
    }
}