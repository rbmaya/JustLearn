package ru.justlearn.data.saved.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import ru.justlearn.data.saved.entities.SynonymEntity

@Dao
interface SynonymDao {

    @Query("SELECT * FROM SynonymEntity WHERE meaningId=(:meaningId)")
    suspend fun getSynonymsByMeaningId(meaningId: Long): List<SynonymEntity>

    @Insert
    suspend fun addSynonym(entity: SynonymEntity)
}