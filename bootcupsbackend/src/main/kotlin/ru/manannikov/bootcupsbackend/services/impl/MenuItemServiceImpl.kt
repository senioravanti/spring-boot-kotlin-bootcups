package ru.manannikov.bootcupsbackend.services.impl

import org.apache.logging.log4j.LogManager
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageRequest
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import ru.manannikov.bootcupsbackend.entities.MenuItemEntity
import ru.manannikov.bootcupsbackend.repos.MenuItemRepo
import ru.manannikov.bootcupsbackend.repos.specs.Specifications
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
    )
        : Page<MenuItemEntity>
    {
        logger.debug("Получены filter: {}, pageRequest: {}", filter, pageRequest)
        val menuItems = if (filter != null) {
            menuItemRepo.findAll(
                Specifications.menuItemDefaultFilter(
                    filter
                ),
                pageRequest
            )
        } else {
            menuItemRepo.findAll(
                pageRequest
            )
        }
        logger.debug("menu items:\n{}", menuItems)
        return menuItems
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