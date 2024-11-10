package ru.justlearn

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.core.EaseIn
import androidx.compose.animation.core.EaseOut
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.Text
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import dagger.hilt.android.AndroidEntryPoint
import ru.justlearn.domain.Word
import ru.justlearn.presentation.navigation.Route
import ru.justlearn.presentation.navigation.TopLevelRoutes
import ru.justlearn.presentation.saved.SavedWordListScreen
import ru.justlearn.presentation.saved.SavedWordListViewModel
import ru.justlearn.presentation.search.SearchWordScreen
import ru.justlearn.presentation.search.SearchWordViewModel
import ru.justlearn.presentation.word_details.WordDetailsScreen
import ru.justlearn.presentation.word_details.WordDetailsViewModel
import ru.justlearn.ui.navigation.CustomNavType
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
                    Scaffold(
                        bottomBar = {
                            BottomNavigation(
                                backgroundColor = MaterialTheme.colorScheme.primary,
                            ) {
                                val navBackStackEntry = navController.currentBackStackEntryAsState()
                                val currentRoute = navBackStackEntry.value?.destination?.route

                                TopLevelRoutes.forEach { topLevelRoute ->
                                    BottomNavigationItem(
                                        icon = {
                                            Image(
                                                painter = painterResource(topLevelRoute.iconRes!!),
                                                contentDescription = topLevelRoute.name!!,
                                                colorFilter = ColorFilter.tint(MaterialTheme.colorScheme.onPrimary)
                                            )
                                        },
                                        label = {
                                            Text(
                                                topLevelRoute.name!!,
                                                style = MaterialTheme.typography.labelSmall,
                                                color = MaterialTheme.colorScheme.onPrimary
                                            )
                                        },
                                        selected = currentRoute == topLevelRoute::class.qualifiedName,
                                        onClick = {
                                            navController.navigate(topLevelRoute) {
                                                popUpTo(navController.graph.startDestinationId) {
                                                    saveState = true
                                                }
                                                launchSingleTop = true
                                                restoreState = true
                                            }
                                        },
                                    )
                                }
                            }
                        }
                    ) { innerPadding ->
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
                                    },
                                    onBackClicked = {
                                        navController.popBackStack()
                                    }
                                )
                            }
                            composable<Route.SavedWords> {
                                SavedWordListScreen(
                                    viewModel = hiltViewModel<SavedWordListViewModel>(),
                                ) { word ->
                                    navController.navigate(Route.WordDetails(word))
                                }
                            }
                        }
                    }
                }
            }
        }
    }

}


