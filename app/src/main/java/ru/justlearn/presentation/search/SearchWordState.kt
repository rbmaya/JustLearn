package ru.justlearn.presentation.search

import ru.justlearn.domain.Word

data class SearchWordState(
    val isSearching: Boolean = false,
    val query: String = "",
    val items: List<Word> = emptyList(),
)