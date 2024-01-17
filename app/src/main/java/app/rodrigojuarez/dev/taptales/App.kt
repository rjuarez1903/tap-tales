package app.rodrigojuarez.dev.taptales

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import app.rodrigojuarez.dev.taptales.ui.pages.auth.AuthScreen
import app.rodrigojuarez.dev.taptales.ui.pages.Home
import app.rodrigojuarez.dev.taptales.ui.pages.NewTale
import app.rodrigojuarez.dev.taptales.ui.pages.Tale
import app.rodrigojuarez.dev.taptales.ui.pages.TalesList
import app.rodrigojuarez.dev.taptales.ui.theme.DarkPink
import app.rodrigojuarez.dev.taptales.ui.theme.MidnightPurple
import app.rodrigojuarez.dev.taptales.ui.theme.ShadowPurple
import app.rodrigojuarez.dev.taptales.ui.theme.Night

@Composable
fun App() {
    val navHostController = rememberNavController()

    Column(
        modifier = Modifier
            .background(
                Brush.verticalGradient(
                    listOf(
                        Night,
                        MidnightPurple,
                        ShadowPurple,
                        DarkPink
                    )
                )
            )
    ) {
        NavHost(
            navController = navHostController,
            startDestination = "authScreen",

            ) {
            composable("authScreen") {
                AuthScreen(navHostController = navHostController)
            }
            composable("home") {
                Home(navHostController)
            }
            composable("talesList") {
                TalesList(navHostController)
            }
            composable("tale/{slug}") {
                val slug = it.arguments?.getString("slug")
                Tale(slug)
            }
            composable("newTale") {
                NewTale(navHostController)
            }
        }
    }

}