package ru.justlearn.data.search

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import ru.justlearn.data.mappers.web.WordWebMapper
import ru.justlearn.domain.Word
import javax.inject.Inject

class WordsDataSourceImpl @Inject constructor(
    private val dictionaryApi: DictionaryApi,
) : WordsDataSource {

    override suspend fun getWordsByQuery(query: String): List<Word> {
        return withContext(Dispatchers.IO) {
            val response = dictionaryApi.searchWord(query)
            response.body()?.let { WordWebMapper.mapList(it) }
                ?.groupBy { it.value }
                ?.map { (value, words) ->
                    Word(
                        id = words.first().id,
                        value = value,
                        phonetics = words
                            .map { it.phonetics }
                            .flatten()
                            .distinctBy { it.phonetic },
                        meanings = words
                            .map { it.meanings }
                            .flatten(),
                    )
                }
                .orEmpty()
        }
    }

}