package ru.justlearn.domain

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import kotlinx.serialization.Serializable

@Serializable
@Parcelize
data class Meaning(
    val partOfSpeech: PartOfSpeech,
    val definitions: List<Definition>,
): Parcelable