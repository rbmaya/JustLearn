package ru.justlearn.data.saved.entities

import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    primaryKeys = ["id"],
    indices = [
        Index("id")
    ]
)
data class WordEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val value: String
)