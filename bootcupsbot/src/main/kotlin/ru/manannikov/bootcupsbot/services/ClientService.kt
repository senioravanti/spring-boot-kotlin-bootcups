package ru.manannikov.bootcupsbot.services


import ru.manannikov.bootcupsbot.entities.ClientEntity
import java.util.*

interface ClientService {
    fun findById(chatId: Long): ClientEntity?
    fun save(clientEntity: ClientEntity)
}
