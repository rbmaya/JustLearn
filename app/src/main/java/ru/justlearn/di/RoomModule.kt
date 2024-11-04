package ru.justlearn.di

import android.content.Context
import androidx.room.Room
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import ru.justlearn.data.saved.SavedWordsRepositoryImpl
import ru.justlearn.data.saved.WordsDatabase
import ru.justlearn.data.saved.dao.AntonymDao
import ru.justlearn.data.saved.dao.DefinitionDao
import ru.justlearn.data.saved.dao.MeaningDao
import ru.justlearn.data.saved.dao.PhoneticDao
import ru.justlearn.data.saved.dao.SynonymDao
import ru.justlearn.data.saved.dao.WordDao
import ru.justlearn.domain.saved.SavedWordsRepository
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class RoomModule {

    @Provides
    @Singleton
    fun provideWordsDatabase(@ApplicationContext context: Context): WordsDatabase {
        return Room.databaseBuilder(context, WordsDatabase::class.java, "words_database")
            .build()
    }

    @Provides
    fun provideWordDao(database: WordsDatabase): WordDao = database.getWordDao()

    @Provides
    fun provideMeaningDao(database: WordsDatabase): MeaningDao = database.getMeaningDao()

    @Provides
    fun provideDefinitionDao(database: WordsDatabase): DefinitionDao = database.getDefinitionDao()

    @Provides
    fun providePhoneticDao(database: WordsDatabase): PhoneticDao = database.getPhoneticDao()

    @Provides
    fun provideSynonymDao(database: WordsDatabase): SynonymDao = database.getSynonymDao()

    @Provides
    fun provideAntonymDao(database: WordsDatabase): AntonymDao = database.getAntonymDao()

    @Provides
    fun provideSavedWordsRepository(impl: SavedWordsRepositoryImpl): SavedWordsRepository {
        return impl
    }

}