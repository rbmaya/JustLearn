package ru.justlearn.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent
import dagger.hilt.components.SingletonComponent
import ru.justlearn.data.search.MockWordsDataSource
import ru.justlearn.data.search.WordsDataSource
import ru.justlearn.data.search.WordsRepositoryImpl
import ru.justlearn.domain.search.WordsRepository

@Module(includes = [SearchWordModule.Bindings::class])
@InstallIn(SingletonComponent::class)
class SearchWordModule {

    @Module
    @InstallIn(SingletonComponent::class)
    interface Bindings {
        @Binds
        fun bindWordsRepository(impl: WordsRepositoryImpl): WordsRepository

        @Binds
        fun bindWordsDataSource(impl: MockWordsDataSource): WordsDataSource
    }
}