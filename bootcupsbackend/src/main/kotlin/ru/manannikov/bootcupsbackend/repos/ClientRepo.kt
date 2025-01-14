package ru.manannikov.bootcupsbackend.repos

import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.ListCrudRepository
import org.springframework.data.repository.query.Param
import ru.manannikov.bootcupsbackend.entities.ClientEntity
import ru.manannikov.bootcupsbackend.enums.RegistrationState

interface ClientRepo : ListCrudRepository<ClientEntity, Long> {
    @Query("SELECT c FROM ClientEntity c WHERE c.registrationState = :rs")
    fun findByRegistrationState(@Param("rs") registrationState: RegistrationState): List<ClientEntity>
}