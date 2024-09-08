package ru.justlearn.data.search

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import ru.justlearn.domain.Definition
import ru.justlearn.domain.Meaning
import ru.justlearn.domain.PartOfSpeech
import ru.justlearn.domain.Word
import javax.inject.Inject

class MockWordsDataSource @Inject constructor() : WordsDataSource {

    private val availableWords = listOf(MOCK_WORD)

    override suspend fun getWordsByQuery(query: String): List<Word> {
        return withContext(Dispatchers.IO){
            availableWords.filter {
                it.value.contains(query, ignoreCase = true)
            }
        }
    }

    companion object {
        val MOCK_WORD = Word(
            id = "id",
            value = "get",
            phonetic = "/ɡɛt/",
            audioUrl = null,
            meanings = listOf(
                Meaning(
                    partOfSpeech = PartOfSpeech.Verb,
                    definitions = listOf(
                        Definition(
                            definition = "To receive",
                            example = "He got a severe reprimand for that.",
                        ),
                        Definition(
                            definition = "To receive",
                            example = "He got a severe reprimand for that.",
                        ),
                        Definition(
                            definition = "To receive",
                            example = "He got a severe reprimand for that.",
                        )
                    )
                ),
                Meaning(
                    partOfSpeech = PartOfSpeech.Verb,
                    definitions = listOf(
                        Definition(
                            definition = "To receive",
                            example = "He got a severe reprimand for that.",
                        ),
                        Definition(
                            definition = "To receive",
                            example = "He got a severe reprimand for that.",
                        ),
                        Definition(
                            definition = "To receive",
                            example = "He got a severe reprimand for that.",
                        )
                    )
                )
            ),
            synonyms = listOf("arrive", "reach"),
            antonyms = listOf("lose")
        )
    }
}