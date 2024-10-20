package ru.justlearn.data.saved.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import ru.justlearn.data.saved.entities.WordEntity

@Dao
interface WordDao {

    @Query("SELECT * FROM WordEntity")
    suspend fun getAllWords(): List<WordEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addWord(entity: WordEntity): Int

    @Query("DELETE FROM WordEntity WHERE value=(:value)")
    suspend fun removeWordByValue(value: String)
}