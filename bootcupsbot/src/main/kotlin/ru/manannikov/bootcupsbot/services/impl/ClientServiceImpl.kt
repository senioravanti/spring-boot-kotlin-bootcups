package ru.manannikov.bootcupsbot.services.impl

import org.springframework.stereotype.Service
import ru.manannikov.bootcupsbot.entities.ClientEntity
import ru.manannikov.bootcupsbot.repositories.ClientRepository
import ru.manannikov.bootcupsbot.services.ClientService

@Service
class ClientServiceImpl(
    private val repository: ClientRepository
) : ClientService {

    override fun findById(id: Long): ClientEntity? {
        return repository.findById(id).orElse(null)
    }

    override fun save(clientEntity: ClientEntity) {
        repository.save(clientEntity)
    }
}
