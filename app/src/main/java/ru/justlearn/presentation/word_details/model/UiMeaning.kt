package ru.justlearn.presentation.word_details.model

import ru.justlearn.domain.Definition

data class UiMeaning(
    val partOfSpeech: String,
    val definitions: List<Definition>
)