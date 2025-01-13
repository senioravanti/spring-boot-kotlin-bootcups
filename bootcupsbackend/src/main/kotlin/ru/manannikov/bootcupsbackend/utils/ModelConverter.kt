package ru.manannikov.bootcupsbackend.utils

import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.context.MessageSource
import org.springframework.data.domain.Page
import org.springframework.stereotype.Component
import ru.manannikov.bootcupsbackend.dto.*
import ru.manannikov.bootcupsbackend.entities.*
import ru.manannikov.bootcupsbackend.services.DictionaryService
import java.util.*

@Component("messageUtils")
class ModelConverter(
    @Qualifier("roleService")
    private val roleService: DictionaryService<RoleEntity>,
    private val messageSource: MessageSource
) {

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

    fun oderToDto(orderEntity: OrderEntity) = OrderDto(
        id = orderEntity.id,
        totalAmount = orderEntity.totalAmount,
        discountAmount = orderEntity.discountAmount,

        createdAt = orderEntity.createdAt.epochSecond,
        status = messageSource.getMessage(orderEntity.status.fieldName, null, Locale.getDefault()),

        client = orderEntity.client ?.let { clientToDto(it) },
        employee = employeeToDto(orderEntity.employee)
    )

    fun <E, R> toPaginationResponse(entityPage: Page<E>, contentMapper: (E) -> R) = PaginationResponse(
        content = entityPage.content.map { contentMapper.invoke(it) },

        currentPageNumber = entityPage.number,
        totalElements = entityPage.totalElements,
        totalPages = entityPage.totalPages,

        hasPrevious = entityPage.hasPrevious(),
        hasNext = entityPage.hasNext()
    )

}