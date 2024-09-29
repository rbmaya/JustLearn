package ru.justlearn.data.model

import com.google.gson.annotations.SerializedName

class DefinitionWebModel(
    @SerializedName("definition")
    val definition: String,
    @SerializedName("example")
    val example: String?,
)