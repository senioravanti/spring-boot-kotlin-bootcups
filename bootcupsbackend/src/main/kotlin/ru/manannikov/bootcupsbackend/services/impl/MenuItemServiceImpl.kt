package ru.manannikov.bootcupsbackend.services.impl

import com.sun.tools.javac.jvm.PoolConstant.LoadableConstant.Long
import org.apache.logging.log4j.LogManager
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageRequest
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Propagation
import org.springframework.transaction.annotation.Transactional
import ru.manannikov.bootcupsbackend.entities.MenuItemEntity
import ru.manannikov.bootcupsbackend.exceptions.NotFoundException
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
    override fun findById(id: Int): MenuItemEntity = menuItemRepo.findById(id).orElseThrow {
        NotFoundException(id.toLong(), MenuItemEntity::class.java)
    }

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

    @Transactional
    override fun save(menuItemEntity: MenuItemEntity) {
        if (menuItemEntity.id != null) throw IllegalArgumentException("")

        val savedMenuItem = menuItemRepo.save(menuItemEntity)
        logger.debug("Позиция меню успешно создана:\n{}", savedMenuItem)
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    override fun update(id: Int, menuItemEntity: MenuItemEntity) {
        var existingMenuItem = findById(id).apply {
            makes = menuItemEntity.makes
            topping = menuItemEntity.topping
            imageUri = menuItemEntity.imageUri
            product = menuItemEntity.product
            unit = menuItemEntity.unit
        }
        existingMenuItem = menuItemRepo.save(existingMenuItem)
        logger.debug("Позиция меню успешно обновлена:\n{}", existingMenuItem)
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    override fun deleteById(id: Int) {
        findById(id)
        menuItemRepo.deleteById(id)
        logger.debug("Позиция меню с идентификатором $id успешно удалена")
    }

    companion object {
        private val logger = LogManager.getLogger(MenuItemServiceImpl::class.java)
    }
}