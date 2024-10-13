package ru.justlearn.presentation.navigation


import androidx.annotation.DrawableRes
import kotlinx.serialization.Serializable
import ru.justlearn.R
import ru.justlearn.domain.Word

@Serializable
sealed class Route(@DrawableRes val iconRes: Int? = null, val name: String? = null) {

    @Serializable
    data object Search : Route(iconRes = R.drawable.search_word_bottom_nav_ic, "Home")

    @Serializable
    data class WordDetails(val word: Word) : Route()

    @Serializable
    data object SavedWords: Route(iconRes = R.drawable.saved_words_bottom_nav_ic, "Saved")

}

val TopLevelRoutes get() = listOf(Route.Search, Route.SavedWords)