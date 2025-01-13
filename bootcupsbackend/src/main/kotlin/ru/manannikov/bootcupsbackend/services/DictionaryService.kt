package ru.manannikov.bootcupsbackend.services

interface DictionaryService<T> {
    fun findAll(): List<T>
    fun findByKey(key: String): T
}