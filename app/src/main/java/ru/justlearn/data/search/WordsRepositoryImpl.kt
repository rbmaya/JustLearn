package ru.justlearn.data.search

import ru.justlearn.domain.Word
import ru.justlearn.domain.search.WordsRepository
import javax.inject.Inject

class WordsRepositoryImpl @Inject constructor(
    private val wordsDataSource: WordsDataSource,
): WordsRepository {

    override suspend fun getWords(query: String): List<Word> {
        return wordsDataSource.getWordsByQuery(query)
    }
}