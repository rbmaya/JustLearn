package ru.justlearn.domain.search

import ru.justlearn.domain.Word

interface WordsRepository {
    suspend fun getWords(query: String): List<Word>
}