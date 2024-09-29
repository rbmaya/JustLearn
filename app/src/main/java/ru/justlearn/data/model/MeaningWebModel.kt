package ru.justlearn.data.model

import com.google.gson.annotations.SerializedName

class MeaningWebModel(
    @SerializedName("partOfSpeech")
    val partOfSpeech: String,
    @SerializedName("definitions")
    val definitions: List<DefinitionWebModel>,
    @SerializedName("synonyms")
    val synonyms: List<String>,
    @SerializedName("antonyms")
    val antonyms: List<String>
)