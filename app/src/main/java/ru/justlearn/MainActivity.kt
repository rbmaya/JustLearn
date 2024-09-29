package ru.justlearn

import android.os.Bundle
import android.transition.Transition
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.core.ArcAnimationSpec
import androidx.compose.animation.core.EaseIn
import androidx.compose.animation.core.EaseOut
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import dagger.hilt.android.AndroidEntryPoint
import ru.justlearn.domain.Word
import ru.justlearn.presentation.navigation.Route
import ru.justlearn.presentation.search.SearchWordScreen
import ru.justlearn.presentation.search.SearchWordViewModel
import ru.justlearn.presentation.word_details.WordDetailsScreen
import ru.justlearn.presentation.word_details.WordDetailsViewModel
import ru.justlearn.ui.navigation.CustomNavType
import ru.justlearn.ui.navigation.navigate
import ru.justlearn.ui.theme.JustLearnTheme
import kotlin.reflect.typeOf

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            JustLearnTheme {
                val navController = rememberNavController()

                Surface(modifier = Modifier.fillMaxSize()) {
                    Scaffold { innerPadding ->
                        NavHost(
                            navController = navController,
                            startDestination = Route.Search,
                            modifier = Modifier.padding(innerPadding)
                        ) {
                            composable<Route.Search> {
                                SearchWordScreen(
                                    hiltViewModel<SearchWordViewModel>(),
                                    onWordClick = { word ->
                                        navController.navigate(Route.WordDetails(word))
                                    })
                            }
                            composable<Route.WordDetails>(
                                enterTransition = {
                                    slideIntoContainer(
                                        animationSpec = tween(300, easing = EaseIn),
                                        towards = AnimatedContentTransitionScope.SlideDirection.Start
                                    )
                                },
                                exitTransition = {
                                    slideOutOfContainer(
                                        animationSpec = tween(300, easing = EaseOut),
                                        towards = AnimatedContentTransitionScope.SlideDirection.End
                                    )
                                },
                                typeMap = mapOf(
                                    typeOf<Word>() to CustomNavType(
                                        Word::class.java,
                                        Word.serializer()
                                    )
                                )
                            ) { navBackStackEntry ->
                                val word = navBackStackEntry.toRoute<Route.WordDetails>().word

                                WordDetailsScreen(
                                    viewModel = hiltViewModel<WordDetailsViewModel, WordDetailsViewModel.WordDetailsViewModelFactory> { factory ->
                                        factory.create(word)
                                    }
                                )
                            }
                        }
                    }
                }
            }
        }
    }

}


