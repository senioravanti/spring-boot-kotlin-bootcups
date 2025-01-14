package ru.manannikov.bootcupsbackend.utils

import org.springframework.context.MessageSource
import org.springframework.data.domain.Page
import org.springframework.stereotype.Service
import ru.manannikov.bootcupsbackend.dto.DictionaryDto
import ru.manannikov.bootcupsbackend.dto.FieldEnumDto
import ru.manannikov.bootcupsbackend.dto.PaginationResponse
import ru.manannikov.bootcupsbackend.entities.DictionaryEntity
import ru.manannikov.bootcupsbackend.enums.FieldEnum
import java.util.*

@Service("miscellaneousMapper")
class MiscellaneousMapper(
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

    fun <E, R> toPaginationResponse(entityPage: Page<E>, contentMapper: (E) -> R) = PaginationResponse(
        content = entityPage.content.map { contentMapper.invoke(it) },

        currentPageNumber = entityPage.number,
        totalElements = entityPage.totalElements,
        totalPages = entityPage.totalPages,

        hasPrevious = entityPage.hasPrevious(),
        hasNext = entityPage.hasNext()
    )

}