package ru.manannikov.bootcupsbackend.services.impl

import org.apache.logging.log4j.LogManager
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Propagation
import org.springframework.transaction.annotation.Transactional
import ru.manannikov.bootcupsbackend.entities.UnitEntity
import ru.manannikov.bootcupsbackend.exceptions.EntityAlreadyExistsException
import ru.manannikov.bootcupsbackend.exceptions.NotFoundException
import ru.manannikov.bootcupsbackend.repos.UnitRepo
import ru.manannikov.bootcupsbackend.services.UnitService
import ru.manannikov.bootcupsbackend.utils.ServiceUtils.tableNameFromEntity

@Transactional(readOnly = true)
@Service("unitService")
class UnitServiceImpl(
    private val unitRepo: UnitRepo
)
    : UnitService
{
    override fun findById(id: Short): UnitEntity {
        if (unitRepo.count() == 0L) throw NotFoundException(UnitEntity::class.java)

        return unitRepo.findById(id).orElseThrow {
            NotFoundException(id.toString(), UnitEntity::class.java)
        }
    }

    @Transactional
    override fun save(entity: UnitEntity) {
        if (entity.id != null) throw EntityAlreadyExistsException(
            tableNameFromEntity(UnitEntity::class)
        )

        val savedUnit = unitRepo.save(entity)
        logger.debug("Новая единица измерения успешно добавлена в БД:\n{}", savedUnit)
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    override fun update(id: Short, entity: UnitEntity) {
        var existingUnit = findById(id).apply {
            name = entity.name
            label = entity.label
        }
        existingUnit = unitRepo.save(existingUnit)
        logger.debug("Единица измерения с идентификатором {} успешно обновлена:\n{}", id, existingUnit)
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    override fun deleteById(id: Short) {
        findById(id)
        unitRepo.deleteById(id)
        logger.debug("Единица измерения с идентификатором {} успешно удалена", id)
    }

    companion object {
        private val logger = LogManager.getLogger(UnitServiceImpl::class.java)
    }
}