package ru.justlearn.presentation.search.views

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.text.ClickableText
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.rounded.Clear
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SearchBar
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import ru.justlearn.R
import ru.justlearn.domain.Word
import ru.justlearn.presentation.search.SearchWordState
import ru.justlearn.ui.components.EmptySearchResultView
import ru.justlearn.ui.theme.JustLearnTheme
import ru.justlearn.ui.theme.Typography

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchWordView(
    state: SearchWordState,
    onQueryChange: (String) -> Unit,
    onToggleSearch: (Boolean) -> Unit,
    onWordClick: (Word) -> Unit,
    onClearQuery: () -> Unit,
) {
    Scaffold(
        topBar = {
            SearchBar(
                query = state.query,
                onQueryChange = onQueryChange,
                onSearch = onQueryChange,
                active = false,
                onActiveChange = onToggleSearch,
                leadingIcon = {
                    Image(
                        painter = painterResource(R.drawable.search_word_screen_search_ic),
                        contentDescription = "",
                    )
                },
                trailingIcon = { SearchBarTrailingIcon(state, onClearQuery) },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(12.dp),
                placeholder = { Text(text = stringResource(id = R.string.search_word_screen_search_place)) }
            ) {}
        }
    ) { paddingValues ->
        LazyColumn(modifier = Modifier.padding(paddingValues)) {
            itemsIndexed(state.items) { index, item ->
                SearchWordItem(word = item, onWordClick = onWordClick)
                if (index < state.items.lastIndex) {
                    HorizontalDivider(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 12.dp),
                        thickness = 1.dp
                    )
                }
            }
        }
        if (state.showPlaceholder) {
            EmptySearchResultView(modifier = Modifier.fillMaxSize())
        }
    }
}

@Composable
fun SearchBarTrailingIcon(state: SearchWordState, onClearQuery: () -> Unit) {
    when {
        state.isSearching -> {
            CircularProgressIndicator(
                strokeWidth = 2.dp,
                modifier = Modifier.size(24.dp)
            )
        }

        state.query.isNotBlank() -> {
            Image(
                modifier = Modifier.clickable {
                    onClearQuery.invoke()
                },
                imageVector = Icons.Rounded.Clear,
                contentDescription = null
            )
        }
    }
}

@Composable
fun SearchWordItem(word: Word, onWordClick: (Word) -> Unit) {
    Text(
        text = AnnotatedString(text = word.value),
        modifier = Modifier
            .fillMaxWidth()
            .clickable {
                onWordClick(word)
            }
            .padding(12.dp),
        style = Typography.bodyLarge
    )
}

@Preview
@Composable
fun SearchWordViewPreview() {
    JustLearnTheme {
        SearchWordView(
            state = SearchWordState(
                isSearching = false,
                query = "Hello World!",
                items = listOf(
                    Word(
                        id = "id",
                        value = "hello",
                        phonetics = emptyList(),
                        meanings = emptyList(),
                    )
                ),
            ),
            onQueryChange = {},
            onToggleSearch = {},
            onWordClick = {},
            onClearQuery = {},
        )
    }
}