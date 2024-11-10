package ru.justlearn.ui.components

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.HorizontalDivider
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import ru.justlearn.domain.Word

@Composable
fun WordListView(
    modifier: Modifier = Modifier,
    items: List<Word>,
    onWordClick: (Word) -> Unit,
) {
    LazyColumn(modifier = modifier) {
        itemsIndexed(items) { index, item ->
            WordView(word = item, onWordClick = onWordClick)
            if (index < items.lastIndex) {
                HorizontalDivider(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 12.dp),
                    thickness = 1.dp
                )
            }
        }
    }
}

@Preview
@Composable
fun WordListViewPreview() {
    WordListView(
        items = listOf(
            Word(
                id = "id",
                value = "hello",
                phonetics = emptyList(),
                meanings = emptyList(),
            )
        ),
        modifier = Modifier.fillMaxSize()
    ) {

    }
}