package ru.justlearn.presentation.word_details

sealed class WordDetailsEvent {
    object OpenScreen: WordDetailsEvent()
    data class PlayAudio(val url: String): WordDetailsEvent()
    object SaveOrUnsaveWord: WordDetailsEvent()
}