package ru.justlearn.data.saved

import androidx.room.withTransaction
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext
import ru.justlearn.data.mappers.database.DefinitionEntityMapper
import ru.justlearn.data.mappers.database.MeaningEntityMapper
import ru.justlearn.data.mappers.database.PhoneticEntityMapper
import ru.justlearn.data.mappers.database.WordEntityMapper
import ru.justlearn.data.saved.dao.AntonymDao
import ru.justlearn.data.saved.dao.DefinitionDao
import ru.justlearn.data.saved.dao.MeaningDao
import ru.justlearn.data.saved.dao.PhoneticDao
import ru.justlearn.data.saved.dao.SynonymDao
import ru.justlearn.data.saved.dao.WordDao
import ru.justlearn.data.saved.entities.AntonymEntity
import ru.justlearn.data.saved.entities.SynonymEntity
import ru.justlearn.domain.Word
import ru.justlearn.domain.saved.SavedWordsRepository
import javax.inject.Inject

class SavedWordsRepositoryImpl @Inject constructor(
    private val wordDao: WordDao,
    private val phoneticDao: PhoneticDao,
    private val meaningDao: MeaningDao,
    private val definitionDao: DefinitionDao,
    private val synonymDao: SynonymDao,
    private val antonymDao: AntonymDao,
    private val wordsDatabase: WordsDatabase,
) : SavedWordsRepository {

    override suspend fun getSavedWords(): List<Word> {
        return withContext(Dispatchers.IO) {
            wordDao.getAllWords().map { wordEntity ->
                val phoneticEntities = phoneticDao.getPhoneticsByWordId(wordEntity.id)
                val meaningEntities = meaningDao.getMeaningsByWordId(wordEntity.id)
                val definitionsByMeanings = meaningEntities.associate { meaningEntity ->
                    meaningEntity.id to definitionDao.getDefinitionsByMeaningId(meaningEntity.id)
                }
                val synonymsByMeanings = meaningEntities.associate { meaningEntity ->
                    meaningEntity.id to synonymDao.getSynonymsByMeaningId(meaningEntity.id)
                }
                val antonymsByMeanings = meaningEntities.associate { meaningEntity ->
                    meaningEntity.id to antonymDao.getAntonymsByMeaningId(meaningEntity.id)
                }

                WordEntityMapper.mapFromEntity(
                    wordEntity = wordEntity,
                    phoneticEntities = phoneticEntities,
                    meaningEntities = meaningEntities,
                    definitionsByMeanings = definitionsByMeanings,
                    synonymsByMeanings = synonymsByMeanings,
                    antonymsByMeanings = antonymsByMeanings,
                )
            }
        }
    }

    override suspend fun addWord(word: Word) {
        withContext(Dispatchers.IO) {
            wordsDatabase.withTransaction {
                val wordId = wordDao.addWord(WordEntityMapper.mapToEntity(word))

                word.phonetics.map { phonetic ->
                    PhoneticEntityMapper.mapToEntity(wordId = wordId, phonetic = phonetic)
                }.forEach {
                    phoneticDao.addPhonetic(it)
                }

                word.meanings.map { meaning ->
                    meaning to MeaningEntityMapper.mapToEntity(wordId = wordId, meaning)
                }.forEach { (meaning, entity) ->
                    val meaningId = meaningDao.addMeaning(entity)
                    meaning.definitions.forEach { definition ->
                        definitionDao.addDefinition(
                            DefinitionEntityMapper.mapToEntity(
                                meaningId = meaningId,
                                definition = definition,
                            )
                        )
                    }
                    meaning.synonyms.forEach { synonym ->
                        synonymDao.addSynonym(
                            SynonymEntity(
                                meaningId = meaningId,
                                value = synonym
                            )
                        )
                    }
                    meaning.antonyms.forEach { antonym ->
                        antonymDao.addAntonym(
                            AntonymEntity(
                                meaningId = meaningId,
                                value = antonym
                            )
                        )
                    }
                }
            }
        }
    }

    override suspend fun removeWord(word: Word) {
        withContext(Dispatchers.IO) {
            wordDao.removeWordByValue(word.value)
        }
    }

    override suspend fun wordIsSaved(value: String): Flow<Boolean> {
        return withContext(Dispatchers.IO) {
            wordDao.getWordFlowByValue(value)
                .map { it != null }
        }
    }
}