package ru.justlearn.data.saved.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow
import ru.justlearn.data.saved.entities.WordEntity

@Dao
interface WordDao {

    @Query("SELECT * FROM WordEntity")
    suspend fun getAllWords(): List<WordEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addWord(entity: WordEntity): Long

    @Query("DELETE FROM WordEntity WHERE value=(:value)")
    suspend fun removeWordByValue(value: String)

    @Query("SELECT * FROM WordEntity WHERE value=(:value) LIMIT 1")
    fun getWordFlowByValue(value: String): Flow<WordEntity?>
}