package ru.justlearn.data.mappers

import ru.justlearn.auxiliary.Mapper
import ru.justlearn.data.model.DefinitionWebModel
import ru.justlearn.domain.Definition

object DefinitionWebMapper: Mapper<DefinitionWebModel, Definition>() {

    override fun map(source: DefinitionWebModel): Definition = with(source) {
        Definition(
            definition = definition,
            example = example
        )
    }

}