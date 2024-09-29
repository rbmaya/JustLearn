package ru.justlearn.presentation.search

sealed class SearchWordEvent {
    data class QueryChanged(val query: String): SearchWordEvent()
    object ClearQuery: SearchWordEvent()
}