package ru.manannikov.bootcupsbackend.utils

import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.context.MessageSource
import org.springframework.data.domain.Page
import org.springframework.stereotype.Service
import ru.manannikov.bootcupsbackend.dto.*
import ru.manannikov.bootcupsbackend.entities.*
import ru.manannikov.bootcupsbackend.enums.FieldEnum
import ru.manannikov.bootcupsbackend.services.*
import java.util.*

@Service("messageUtils")
class ModelConverter(

    @Qualifier("roleService")
    private val roleService: DictionaryService<RoleEntity>,

    private val productService: ProductService,
    private val unitService: UnitService,

    private val menuItemService: MenuItemService,
    private val orderService: OrderService,

    private val messageSource: MessageSource
) {

    fun toFieldEnumDto(fieldEnum: FieldEnum) = FieldEnumDto(
        key = fieldEnum.fieldKey,
        name = messageSource.getMessage(fieldEnum.fieldName, null, Locale.getDefault())
    )
    fun toFieldEnumDto(fieldEnums: List<FieldEnum>): List<FieldEnumDto> = fieldEnums.map {
        toFieldEnumDto(it)
    }
    /**
     * menu item dto -> entity и обратно
     */
    private fun commonToMenuItemEntity(
        menuItemDto: MenuItemDto
    ): MenuItemEntity = MenuItemEntity().apply {
        id = menuItemDto.id

        makes = menuItemDto.makes
        price = menuItemDto.price

        topping = menuItemDto.topping
        imageUri = menuItemDto.imageUri
    }
    fun toMenuItemEntity(
        menuItemRequest: MenuItemRequest,
    ): MenuItemEntity = commonToMenuItemEntity(menuItemRequest).apply {
        product = productService.findById(menuItemRequest.productId)
        unit = unitService.findById(menuItemRequest.unitId)
    }

    /**
     * dictionary dto -> entity и обратно
     */
    fun dictionaryToDto(dictionaryEntity: DictionaryEntity) = DictionaryDto(
        id = dictionaryEntity.id,
        key = dictionaryEntity.key,
        name = messageSource.getMessage(dictionaryEntity.name, null, Locale.getDefault())
    )
    fun dictionaryToDto(dictionaryEntities: List<DictionaryEntity>): List<DictionaryDto> {
        return dictionaryEntities.map { dictionaryToDto(it) }
    }

    fun bonusCardEntityToDto(bonusCardEntity: BonusCardEntity) = BonusCardDto(
        amount = bonusCardEntity.amount,
        discountPercent = bonusCardEntity.discountPercent
    )
    fun clientToDto(clientEntity: ClientEntity) = ClientDto().apply {
        id = clientEntity.id
        chatId = clientEntity.chatId
        registrationState = clientEntity.registrationState

        name = clientEntity.name

        birthday = clientEntity.birthday
        email = clientEntity.email
        phoneNumber = clientEntity.phoneNumber

        bonusCard = clientEntity.bonusCard ?.let { bonusCardEntityToDto(it) }
    }

    /**
     * employee entity -> dto и обратно
     */
    fun employeeToDto(employeeEntity: EmployeeEntity) = EmployeeDto(
        id = employeeEntity.id,

        lastName = employeeEntity.lastName,
        firstName = employeeEntity.firstName,
        middleName = employeeEntity.middleName,

        username = employeeEntity.username,
        password = employeeEntity.password,

        email = employeeEntity.email,
        phoneNumber = employeeEntity.phoneNumber,

        role = messageSource.getMessage(employeeEntity.role.name, null, Locale.getDefault())
    )
    fun employeeToEntity(employeeDto: EmployeeDto) = EmployeeEntity().apply {
        id = employeeDto.id

        lastName = employeeDto.lastName
        firstName = employeeDto.firstName
        middleName = employeeDto.middleName

        username = employeeDto.username
        password = employeeDto.password

        email = employeeDto.email
        phoneNumber = employeeDto.phoneNumber

        role = roleService.findByKey(employeeDto.role)
    }

    fun toOrderDto(orderEntity: OrderEntity) = OrderDto(
        id = orderEntity.id,
        totalAmount = orderEntity.totalAmount,
        discountAmount = orderEntity.discountAmount,

        createdAt = orderEntity.createdAt.epochSecond,
        status = messageSource.getMessage(orderEntity.status.fieldName, null, Locale.getDefault()),

        client = orderEntity.client ?.let { clientToDto(it) },
        employee = employeeToDto(orderEntity.employee)
    )
    fun toOrderItemEntity(orderItemRequest: OrderItemRequest) = OrderItemEntity().apply {
        id = orderItemRequest.id
        quantity = orderItemRequest.quantity

        order = orderService.findById(orderItemRequest.orderId)
        menuItem = menuItemService.findById(orderItemRequest.menuItemId)
    }

    fun <E, R> toPaginationResponse(entityPage: Page<E>, contentMapper: (E) -> R) = PaginationResponse(
        content = entityPage.content.map { contentMapper.invoke(it) },

        currentPageNumber = entityPage.number,
        totalElements = entityPage.totalElements,
        totalPages = entityPage.totalPages,

        hasPrevious = entityPage.hasPrevious(),
        hasNext = entityPage.hasNext()
    )

}