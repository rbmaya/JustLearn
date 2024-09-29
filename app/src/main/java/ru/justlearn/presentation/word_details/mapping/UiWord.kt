package ru.justlearn.presentation.word_details.mapping

import ru.justlearn.domain.Definition
import ru.justlearn.domain.Phonetic

data class UiWord(
    val id: String,
    val value: String,
    val phonetics: List<Phonetic>,
    val meanings: List<UiMeaning>
)

data class UiMeaning(
    val partOfSpeech: String,
    val definitions: List<Definition>,
    val synonyms: String,
    val antonyms: String,
)