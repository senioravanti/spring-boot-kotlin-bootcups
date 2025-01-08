package ru.manannikov.bootcupsbot.repositories

import org.springframework.data.jpa.repository.JpaRepository
import ru.manannikov.bootcupsbot.entities.ClientEntity

interface ClientRepository : JpaRepository<ClientEntity?, Long?> {}
