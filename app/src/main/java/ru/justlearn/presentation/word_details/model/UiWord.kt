package ru.justlearn.presentation.word_details.model

data class UiWord(
    val id: String,
    val value: String,
    val phonetic: String,
    val audioUrl: String?,
    val meanings: List<UiMeaning>,
    val synonyms: String,
    val antonyms: String
)