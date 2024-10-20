package ru.justlearn.data.mappers.database

import ru.justlearn.data.saved.entities.DefinitionEntity
import ru.justlearn.domain.Definition

object DefinitionEntityMapper {

    fun mapToEntity(meaningId: Int, definition: Definition): DefinitionEntity = with(definition) {
        DefinitionEntity(
            meaningId = meaningId,
            definition = this.definition,
            example = example,
        )
    }

    fun mapFromEntity(definitionEntity: DefinitionEntity): Definition = with(definitionEntity) {
        Definition(
            definition = definition,
            example = example,
        )
    }
}