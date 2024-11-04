package ru.justlearn.data.saved.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import ru.justlearn.data.saved.entities.DefinitionEntity

@Dao
interface DefinitionDao {

    @Query("SELECT * FROM DefinitionEntity WHERE meaningId=(:meaningId)")
    suspend fun getDefinitionsByMeaningId(meaningId: Long): List<DefinitionEntity>

    @Insert
    suspend fun addDefinition(entity: DefinitionEntity)
}