package ru.justlearn.data.saved.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import ru.justlearn.data.saved.entities.AntonymEntity

@Dao
interface AntonymDao {

    @Query("SELECT * FROM AntonymEntity WHERE meaningId=(:meaningId)")
    fun getAntonymsByMeaningId(meaningId: Int): List<AntonymEntity>

    @Insert
    fun addAntonym(entity: AntonymEntity)
}