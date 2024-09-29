package ru.justlearn.data.search

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import ru.justlearn.data.mappers.WordWebMapper
import ru.justlearn.domain.Word
import javax.inject.Inject

class WordsDataSourceImpl @Inject constructor(
    private val dictionaryApi: DictionaryApi,
) : WordsDataSource {

    override suspend fun getWordsByQuery(query: String): List<Word> {
        return withContext(Dispatchers.IO) {
            val response = dictionaryApi.searchWord(query)
            response.body()?.let { WordWebMapper.mapList(it) }
                .orEmpty()
        }
    }

}