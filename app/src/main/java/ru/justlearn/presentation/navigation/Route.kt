package ru.justlearn.presentation.navigation

import kotlinx.serialization.Serializable
import ru.justlearn.domain.Word

@Serializable
sealed class Route {

    @Serializable
    data object Search: Route()

    @Serializable
    data class WordDetails(val word: Word): Route()
}