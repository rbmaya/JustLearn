package ru.justlearn.domain.saved

import ru.justlearn.domain.Word

interface SavedWordsRepository {

    suspend fun getSavedWords(): List<Word>

    suspend fun addWord(word: Word)

    suspend fun removeWord(word: Word)

}