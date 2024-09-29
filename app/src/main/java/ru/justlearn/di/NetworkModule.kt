package ru.justlearn.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import ru.justlearn.data.search.DictionaryApi

@Module
@InstallIn(SingletonComponent::class)
class NetworkModule {

    @Provides
    fun provideDictionaryApi(
        retrofit: Retrofit
    ): DictionaryApi = retrofit.create(DictionaryApi::class.java)

    @Provides
    fun provideRetrofit(
        okHttpClient: OkHttpClient
    ): Retrofit {
        return Retrofit.Builder()
            .baseUrl("https://api.dictionaryapi.dev/api/v2/")
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    @Provides
    fun provideOkHttpClient(): OkHttpClient {
        return OkHttpClient.Builder()
            .addInterceptor(HttpLoggingInterceptor().also {
                it.level = HttpLoggingInterceptor.Level.BODY
            })
            .build()
    }

}