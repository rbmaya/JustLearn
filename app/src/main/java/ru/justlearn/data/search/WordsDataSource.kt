package ru.justlearn.data.search

import ru.justlearn.domain.Word

interface WordsDataSource {
    suspend fun getWordsByQuery(query: String): List<Word>
}