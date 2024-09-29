package ru.justlearn.data.search

import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import ru.justlearn.data.model.WordWebModel

interface DictionaryApi {

    @GET("entries/en/{query}")
    suspend fun searchWord(@Path("query") query: String): Response<List<WordWebModel>>
}