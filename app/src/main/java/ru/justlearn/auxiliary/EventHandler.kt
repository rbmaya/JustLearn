package ru.justlearn.auxiliary

interface EventHandler<T> {
    fun obtainEvent(event: T)
}