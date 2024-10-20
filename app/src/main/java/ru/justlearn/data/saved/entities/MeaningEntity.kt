package ru.justlearn.data.saved.entities

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    indices = [
        Index("id"),
        Index("wordId")
    ],
    foreignKeys = [
        ForeignKey(
            entity = WordEntity::class,
            parentColumns = ["id"],
            childColumns = ["wordId"],
            onDelete = ForeignKey.CASCADE
        )
    ]
)
data class MeaningEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val wordId: Int,
    val partOfSpeech: String,
)