package ru.justlearn.presentation.word_details

import ru.justlearn.domain.Word

data class WordDetailsScreenState(
    val word: Word,
    val isSaved: Boolean,
    val initialSaveStateProgress: Boolean,
)