package ru.justlearn.data.saved

import androidx.room.Database
import androidx.room.RoomDatabase
import ru.justlearn.data.saved.dao.AntonymDao
import ru.justlearn.data.saved.dao.DefinitionDao
import ru.justlearn.data.saved.dao.MeaningDao
import ru.justlearn.data.saved.dao.PhoneticDao
import ru.justlearn.data.saved.dao.SynonymDao
import ru.justlearn.data.saved.dao.WordDao
import ru.justlearn.data.saved.entities.AntonymEntity
import ru.justlearn.data.saved.entities.DefinitionEntity
import ru.justlearn.data.saved.entities.MeaningEntity
import ru.justlearn.data.saved.entities.PhoneticEntity
import ru.justlearn.data.saved.entities.SynonymEntity
import ru.justlearn.data.saved.entities.WordEntity

@Database(
    entities = [
        WordEntity::class,
        MeaningEntity::class,
        DefinitionEntity::class,
        SynonymEntity::class,
        AntonymEntity::class,
        PhoneticEntity::class,
    ],
    version = 1,
)
abstract class WordsDatabase: RoomDatabase() {

    abstract fun getWordDao(): WordDao

    abstract fun getMeaningDao(): MeaningDao

    abstract fun getDefinitionDao(): DefinitionDao

    abstract fun getPhoneticDao(): PhoneticDao

    abstract fun getSynonymDao(): SynonymDao

    abstract fun getAntonymDao(): AntonymDao
}
