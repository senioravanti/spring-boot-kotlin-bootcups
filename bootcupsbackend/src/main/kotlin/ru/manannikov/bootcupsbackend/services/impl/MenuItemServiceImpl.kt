package ru.manannikov.bootcupsbackend.services.impl

import org.apache.logging.log4j.LogManager
import org.springframework.data.domain.PageRequest
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import org.springframework.util.MultiValueMap
import ru.manannikov.bootcupsbackend.dto.SortFilterFieldDto
import ru.manannikov.bootcupsbackend.entities.MenuItemEntity
import ru.manannikov.bootcupsbackend.repos.MenuItemRepo
import ru.manannikov.bootcupsbackend.services.MenuItemService

@Transactional(readOnly = true)
@Service("menuItemService")
class MenuItemServiceImpl(
    private val menuItemRepo: MenuItemRepo
)
    : MenuItemService
{
    override fun findAll(
        pageRequest: PageRequest,
        filter: Map<String, Any>?
    ): List<MenuItemEntity> {
        TODO("Not yet implemented")
    }

    override fun save(menuItemEntity: MenuItemEntity) {
        TODO("Not yet implemented")
    }

    override fun update(id: Int, menuItemEntity: MenuItemEntity) {
        TODO("Not yet implemented")
    }

    override fun deleteAllById(ids: List<Int>) {
        TODO("Not yet implemented")
    }

    companion object {
        private val logger = LogManager.getLogger(MenuItemServiceImpl::class.java)
    }
}