package ru.justlearn.data.search

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import ru.justlearn.domain.Word
import javax.inject.Inject

class MockWordsDataSource @Inject constructor() : WordsDataSource {

    private val availableWords = listOf(
        Word(value = "Hello"),
        Word(value = "World!"),
        Word(value = "World"),
        Word(value = "Hell"),
        Word(value = "or"),
    )

    override suspend fun getWordsByQuery(query: String): List<Word> {
        return withContext(Dispatchers.IO){
            availableWords.filter {
                it.value.contains(query, ignoreCase = true)
            }
        }
    }
}