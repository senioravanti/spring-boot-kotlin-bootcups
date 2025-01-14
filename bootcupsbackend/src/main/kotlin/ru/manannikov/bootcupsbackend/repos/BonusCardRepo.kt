package ru.manannikov.bootcupsbackend.repos

import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.ListCrudRepository
import org.springframework.data.repository.query.Param
import ru.manannikov.bootcupsbackend.entities.BonusCardEntity

interface BonusCardRepo : ListCrudRepository<BonusCardEntity, Long>
{
    @Query("SELECT b FROM ClientEntity c JOIN c.bonusCard b WHERE c.id = :clientId")
    fun findByClientId(@Param("clientId") clientId: Long): BonusCardEntity?
}
