package ru.manannikov.bootcupsbackend.exceptions

class NotFoundException : RuntimeException {
    var entityId: String? = null
        get
    val entityClass: Class<*>

    constructor(cls: Class<*>): super("exception.not-found.table") {
        entityClass = cls
    }
    constructor(id: String?, cls: Class<*>): super("exception.not-found.id") {
        entityId = id
        entityClass = cls
    }
}