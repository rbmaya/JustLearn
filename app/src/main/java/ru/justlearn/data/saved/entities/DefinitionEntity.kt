package ru.justlearn.data.saved.entities

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    indices = [
        Index("id"),
        Index("meaningId")
    ],
    foreignKeys = [
        ForeignKey(
            entity = MeaningEntity::class,
            parentColumns = ["id"],
            childColumns = ["meaningId"],
            onDelete = ForeignKey.CASCADE
        )
    ]
)
data class DefinitionEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val meaningId: Long,
    val definition: String,
    val example: String?
)