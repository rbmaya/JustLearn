package ru.justlearn.presentation.search.views

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Clear
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SearchBar
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import ru.justlearn.R
import ru.justlearn.domain.Word
import ru.justlearn.presentation.search.SearchWordState
import ru.justlearn.ui.components.EmptySearchResultView
import ru.justlearn.ui.components.WordListView
import ru.justlearn.ui.theme.JustLearnTheme

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
        WordListView(items = state.items, modifier = Modifier.padding(paddingValues)) {
            onWordClick(it)
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