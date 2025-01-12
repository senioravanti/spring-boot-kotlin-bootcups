package ru.manannikov.bootcupsbackend.exceptions

class NotFoundException : RuntimeException {
    var entityId: Long? = null
        get
    val entityClass: Class<*>

    constructor(cls: Class<*>): super("exception.not-found.table") {
        entityClass = cls
    }
    constructor(id: Long, cls: Class<*>): super("exception.not-found.id") {
        entityId = id
        entityClass = cls
    }
}