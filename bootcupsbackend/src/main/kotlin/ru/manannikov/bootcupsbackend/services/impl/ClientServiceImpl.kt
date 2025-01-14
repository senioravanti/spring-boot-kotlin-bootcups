package ru.manannikov.bootcupsbackend.services.impl

import org.apache.logging.log4j.LogManager
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Propagation
import org.springframework.transaction.annotation.Transactional
import ru.manannikov.bootcupsbackend.entities.ClientEntity
import ru.manannikov.bootcupsbackend.enums.RegistrationState
import ru.manannikov.bootcupsbackend.exceptions.EntityAlreadyExistsException
import ru.manannikov.bootcupsbackend.exceptions.NotFoundException
import ru.manannikov.bootcupsbackend.repos.ClientRepo
import ru.manannikov.bootcupsbackend.services.ClientService
import ru.manannikov.bootcupsbackend.utils.ServiceUtils.tableNameFromEntity

@Transactional(readOnly = true)
@Service("clientService")
class ClientServiceImpl(
    private val clientRepo: ClientRepo
)
    : ClientService
{
    override fun findByRegistrationState(registrationState: RegistrationState): List<ClientEntity> = clientRepo.findByRegistrationState(registrationState)

    override fun findById(id: Long): ClientEntity {
        if (clientRepo.count() == 0L) throw NotFoundException(ClientEntity::class.java)

        return clientRepo.findById(id).orElseThrow { NotFoundException(id.toString(), ClientEntity::class.java) }
    }

    @Transactional
    override fun save(entity: ClientEntity) {
        if (entity.id != null) throw EntityAlreadyExistsException(
            tableNameFromEntity(ClientEntity::class)
        )

        val savedClient = clientRepo.save(entity)
        logger.debug("Клиент успешно зарегистрирован:\n{}", savedClient)
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    override fun update(id: Long, entity: ClientEntity) {
        var existingClient = findById(id).apply {
            name = entity.name
            birthday = entity.birthday
            email = entity.email
            phoneNumber = entity.phoneNumber
            registrationState = entity.registrationState
            bonusCard = entity.bonusCard
        }
        existingClient = clientRepo.save(existingClient)
        logger.debug("Данные клиента с идентификатором {} успешно обновлены:\n{}", id, existingClient)
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    override fun deleteById(id: Long) {
        findById(id)
        clientRepo.deleteById(id)
        logger.debug("Клиент с идентификатором {}, и его бонусная карта -> успешно удалены", id)

    }

    companion object {
        private val logger = LogManager.getLogger(ClientServiceImpl::class.java)
    }
}