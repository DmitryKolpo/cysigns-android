package com.demacia.cysigns

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.createGraph
import com.demacia.cysigns.features.home.HomeScreen
import com.demacia.cysigns.features.info.InfoScreen
import com.demacia.cysigns.features.quiz.QuizScreen
import com.demacia.cysigns.ui.theme.CySignsTheme

@Composable
fun RootScreen() {
    CySignsTheme {
        Surface(
            color = MaterialTheme.colorScheme.background,
            modifier = Modifier.fillMaxSize()
        ) {
            RootNavHost()
        }
    }
}

@Composable
private fun RootNavHost() {
    val navController = rememberNavController()
    val navGraph = remember(navController) {
        navController.createGraph(startDestination = "home") {
            composable("home") {
                HomeScreen(
                    startPictureQuiz = { navController.navigate("quiz/name") },
                    startNameQuiz = { navController.navigate("quiz/picture") },
                )
            }
            //TODO: implement Safe Arguments
            composable("quiz/name") {
                QuizScreen(
                    mode = "name",
                    openInfo = { navController.navigate("info") },
                )
            }
            composable("quiz/picture") {
                QuizScreen(
                    mode = "picture",
                    openInfo = { navController.navigate("info") },
                )
            }
            composable("info") { InfoScreen() }
        }
    }

    NavHost(navController, navGraph)
}