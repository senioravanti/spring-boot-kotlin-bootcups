package ru.manannikov.bootcupsbackend.utils

import org.springframework.context.MessageSource
import org.springframework.stereotype.Component
import ru.manannikov.bootcupsbackend.dto.SortFilterFieldDto
import ru.manannikov.bootcupsbackend.enums.FieldEnum
import java.util.*

@Component("messageUtils")
class MessageUtils(
    private val messageSource: MessageSource
) {
    fun mapFieldEnumToEnumDto(fieldEnums: List<FieldEnum>): List<SortFilterFieldDto> {
        return fieldEnums
            .map {
                SortFilterFieldDto(
                    key = it.fieldKey,
                    fieldName = messageSource.getMessage(it.fieldName, null, Locale.getDefault())
                )
            }
    }
}