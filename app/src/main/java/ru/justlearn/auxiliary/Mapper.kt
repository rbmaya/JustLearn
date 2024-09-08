package ru.justlearn.auxiliary

abstract class Mapper<From, To> {

    abstract fun map(source: From): To

    fun mapList(source: List<From>): List<To> = source.map(::map)
}