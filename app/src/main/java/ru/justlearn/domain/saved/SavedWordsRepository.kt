package ru.justlearn.domain.saved

import kotlinx.coroutines.flow.Flow
import ru.justlearn.domain.Word

interface SavedWordsRepository {

    suspend fun getSavedWords(): List<Word>

    suspend fun addWord(word: Word)

    suspend fun removeWord(word: Word)

    suspend fun wordIsSaved(value: String): Flow<Boolean>

}