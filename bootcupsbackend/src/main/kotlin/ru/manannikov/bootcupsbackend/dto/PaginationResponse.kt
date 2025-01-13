package ru.manannikov.bootcupsbackend.dto

import org.springframework.data.domain.Page

data class PaginationResponse<T>(
    val content: List<T>,

    val currentPageNumber: Int,
    val totalElements: Long,
    val totalPages: Int,

    val hasPrevious: Boolean,
    val hasNext: Boolean
) {
    companion object {
        fun <T, R> of(entityPage: Page<T>, contentMapper: (T) -> R) = PaginationResponse(
        content = entityPage.content.map { contentMapper.invoke(it) },

        currentPageNumber = entityPage.number,
        totalElements = entityPage.totalElements,
        totalPages = entityPage.totalPages,

        hasPrevious = entityPage.hasPrevious(),
        hasNext = entityPage.hasNext()
        )
    }
}
