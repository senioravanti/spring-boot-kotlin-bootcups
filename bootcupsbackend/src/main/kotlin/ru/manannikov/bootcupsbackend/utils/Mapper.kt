package ru.manannikov.bootcupsbackend.utils

import org.springframework.context.MessageSource
import org.springframework.data.jpa.domain.AbstractPersistable_.id
import org.springframework.stereotype.Component
import ru.manannikov.bootcupsbackend.dto.*
import ru.manannikov.bootcupsbackend.entities.BonusCardEntity
import ru.manannikov.bootcupsbackend.entities.ClientEntity
import ru.manannikov.bootcupsbackend.entities.OrderEntity
import ru.manannikov.bootcupsbackend.enums.FieldEnum
import java.util.*

@Component("messageUtils")
class Mapper(
    private val messageSource: MessageSource
) {
    fun fieldEnumToFieldEnumDto(fieldEnum: FieldEnum) = FieldEnumDto(
        key = fieldEnum.fieldKey,
        fieldName = messageSource.getMessage(fieldEnum.fieldName, null, Locale.getDefault())
    )

    fun fieldEnumToFieldEnumDto(fieldEnums: List<FieldEnum>): List<FieldEnumDto> {
        return fieldEnums
            .map { fieldEnumToFieldEnumDto(it) }
    }

    fun bonusCardEntityToBonusCardDto(bonusCardEntity: BonusCardEntity) = BonusCardDto(
        amount = bonusCardEntity.amount,
        discountPercent = bonusCardEntity.discountPercent
    )

    fun clientEntityToClientDto(clientEntity: ClientEntity) = ClientDto().apply {
        id = clientEntity.id
        chatId = clientEntity.chatId
        registrationState = clientEntity.registrationState

        name = clientEntity.name

        birthday = clientEntity.birthday
        email = clientEntity.email
        phoneNumber = clientEntity.phoneNumber

        bonusCard = clientEntity.bonusCard ?.let { bonusCardEntityToBonusCardDto(it) }
    }

    fun oderEntityToOrderDto(orderEntity: OrderEntity) = OrderDto(
        id = orderEntity.id,
        totalAmount = orderEntity.totalAmount,
        discountAmount = orderEntity.discountAmount,

        createdAt = orderEntity.createdAt.epochSecond,
        status = fieldEnumToFieldEnumDto(orderEntity.status),

        client = orderEntity.client ?.let { clientEntityToClientDto(it) },
        employee = EmployeeDto.of(orderEntity.employee)
    )

}