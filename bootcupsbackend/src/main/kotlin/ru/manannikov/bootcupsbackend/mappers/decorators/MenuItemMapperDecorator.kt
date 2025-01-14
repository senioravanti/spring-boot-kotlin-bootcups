package ru.manannikov.bootcupsbackend.mappers.decorators

import org.springframework.beans.factory.annotation.Autowired
import ru.manannikov.bootcupsbackend.dto.MenuItemRequest
import ru.manannikov.bootcupsbackend.entities.MenuItemEntity
import ru.manannikov.bootcupsbackend.mappers.MenuItemMapper
import ru.manannikov.bootcupsbackend.services.ProductService
import ru.manannikov.bootcupsbackend.services.UnitService

abstract class MenuItemMapperDecorator : MenuItemMapper {
    @set:Autowired
    open lateinit var delegate: MenuItemMapper

    @set:Autowired
    lateinit var productService: ProductService
    @set:Autowired
    lateinit var unitService: UnitService

    override fun toEntity(menuItem: MenuItemRequest): MenuItemEntity = delegate.toEntity(menuItem).apply {
        product = productService.findById(menuItem.productId)
        unit = unitService.findById(menuItem.unitId)
    }
}