package ru.manannikov.bootcupsbackend.services.impl

import org.apache.logging.log4j.LogManager
import org.springframework.stereotype.Service
import ru.manannikov.bootcupsbackend.entities.MenuItemEntity
import ru.manannikov.bootcupsbackend.repos.MenuItemRepo
import ru.manannikov.bootcupsbackend.services.MenuItemService

@Service("menuItemService")
class MenuItemServiceImpl(
    private val menuItemRepo: MenuItemRepo
)
    : MenuItemService
{
    override fun findAll(filter: Map<String, Any>, sortFields: List<String>): List<MenuItemEntity> {
        TODO("Not yet implemented")
    }

    override fun save(menuItemEntity: MenuItemEntity): MenuItemEntity {
        TODO("Not yet implemented")
    }

    override fun update(id: Int, menuItemEntity: MenuItemEntity): MenuItemEntity {
        TODO("Not yet implemented")
    }

    override fun deleteAllById(ids: List<Int>) {
        TODO("Not yet implemented")
    }

    companion object {
        private val logger = LogManager.getLogger(MenuItemServiceImpl::class.java)
    }
}