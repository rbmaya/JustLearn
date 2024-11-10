package ru.justlearn.presentation.saved.views

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import ru.justlearn.domain.Word
import ru.justlearn.presentation.saved.SavedWordsListScreenState
import ru.justlearn.ui.components.WordListView

@Composable
fun SavedWordListView(
    state: SavedWordsListScreenState,
    onWordClick: (Word) -> Unit,
) {
    WordListView(
        items = state.wordList,
        modifier = Modifier.fillMaxSize().padding(top = 24.dp)
    ) {
        onWordClick(it)
    }
}