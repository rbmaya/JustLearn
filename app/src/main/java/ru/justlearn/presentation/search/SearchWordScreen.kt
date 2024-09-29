package ru.justlearn.presentation.search

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import ru.justlearn.domain.Word
import ru.justlearn.presentation.search.views.SearchWordView

@Composable
fun SearchWordScreen(
    viewModel: SearchWordViewModel,
    onWordClick: (Word) -> Unit
) {
    val state by viewModel.screenState.collectAsState()

    SearchWordView(
        state = state,
        onQueryChange = { newValue ->
            viewModel.obtainEvent(SearchWordEvent.QueryChanged(newValue))
        },
        onToggleSearch = {},
        onWordClick = onWordClick,
        onClearQuery = {
            viewModel.obtainEvent(SearchWordEvent.ClearQuery)
        }
    )
}