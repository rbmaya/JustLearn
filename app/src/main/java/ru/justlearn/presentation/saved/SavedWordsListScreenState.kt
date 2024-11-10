package ru.justlearn.presentation.saved

import ru.justlearn.domain.Word

data class SavedWordsListScreenState(
    val wordList: List<Word>,
    val isInitialLoading: Boolean,
)