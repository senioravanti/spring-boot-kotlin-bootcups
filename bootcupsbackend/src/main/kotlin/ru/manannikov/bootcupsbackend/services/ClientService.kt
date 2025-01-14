package ru.manannikov.bootcupsbackend.services

import ru.manannikov.bootcupsbackend.entities.ClientEntity
import ru.manannikov.bootcupsbackend.enums.RegistrationState

interface ClientService : CrudService<ClientEntity, Long> {
    fun findByRegistrationState(registrationState: RegistrationState): List<ClientEntity>
}