package ru.justlearn.data.saved.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import ru.justlearn.data.saved.entities.PhoneticEntity

@Dao
interface PhoneticDao {

    @Query("SELECT * FROM PhoneticEntity WHERE wordId=(:wordId)")
    suspend fun getPhoneticsByWordId(wordId: Long): List<PhoneticEntity>

    @Insert
    suspend fun addPhonetic(entity: PhoneticEntity)
}