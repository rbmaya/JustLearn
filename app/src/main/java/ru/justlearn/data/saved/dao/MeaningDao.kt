package ru.justlearn.data.saved.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import ru.justlearn.data.saved.entities.MeaningEntity

@Dao
interface MeaningDao {

    @Query("SELECT * FROM MeaningEntity WHERE wordId=(:wordId)")
    suspend fun getMeaningsByWordId(wordId: Int): List<MeaningEntity>

    @Insert
    suspend fun addMeaning(entity: MeaningEntity): Int
}