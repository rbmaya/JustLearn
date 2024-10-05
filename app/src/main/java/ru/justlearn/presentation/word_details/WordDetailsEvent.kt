package ru.justlearn.presentation.word_details

sealed class WordDetailsEvent {
    data class PlayAudio(val url: String): WordDetailsEvent()
}