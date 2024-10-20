package ru.justlearn.data.saved

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

class SavedWordsRepositoryImpl(
    private val wordDao: WordDao,
    private val phoneticDao: PhoneticDao,
    private val meaningDao: MeaningDao,
    private val definitionDao: DefinitionDao,
    private val synonymDao: SynonymDao,
    private val antonymDao: AntonymDao,
    private val wordsDatabase: WordsDatabase,
) : SavedWordsRepository {

    override suspend fun getSavedWords(): List<Word> {
        return wordDao.getAllWords().map { wordEntity ->
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

    override suspend fun addWord(word: Word) {
        // TODO запустить в одной транзакции
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

    override suspend fun removeWord(word: Word) {
        wordDao.removeWordByValue(word.value)
    }
}