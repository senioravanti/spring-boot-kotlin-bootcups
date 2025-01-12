package ru.manannikov.bootcupsbackend.exceptions

class EntityAlreadyExistsException(
    val tableName: String
) : RuntimeException("exception.illegal-argument.already-exists") {}