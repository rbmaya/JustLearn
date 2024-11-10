package ru.justlearn.presentation.saved

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import ru.justlearn.R
import ru.justlearn.domain.Word
import ru.justlearn.presentation.saved.views.SavedWordListView
import ru.justlearn.ui.components.EmptySearchResultView
import ru.justlearn.ui.components.LoadingView

@Composable
fun SavedWordListScreen(
    viewModel: SavedWordListViewModel,
    onWordClick: (Word) -> Unit
) {

    val state by viewModel.screenState.collectAsState()

    when {
        state.isInitialLoading -> LoadingView()
        state.wordList.isEmpty() -> {
            EmptySearchResultView(
                titleRes = R.string.saved_word_list_no_words
            )
        }
        else -> SavedWordListView(state, onWordClick)
    }

    LaunchedEffect(true) {
        viewModel.obtainEvent(SavedWordListEvent.OpenScreen)
    }
}