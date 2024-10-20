package ru.justlearn.di

import android.content.Context
import androidx.room.Room
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import ru.justlearn.data.saved.WordsDatabase

@Module
@InstallIn(SingletonComponent::class)
class RoomModule {

    fun provideWordsDatabase(@ApplicationContext context: Context): WordsDatabase {
        return Room.databaseBuilder(context, WordsDatabase::class.java, "words_database")
            .build()
    }

}