package ru.justlearn.presentation.search

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import ru.justlearn.presentation.search.views.SearchWordView

@Composable
fun SearchWordScreen(
    viewModel: SearchWordViewModel,
) {
    val state by viewModel.screenState.collectAsState()

    SearchWordView(
        state = state,
        onQueryChange = { newValue ->
            viewModel.obtainEvent(SearchWordEvent.QueryChanged(newValue))
        },
        onToggleSearch = {},
        onWordClick = {}
    )
}