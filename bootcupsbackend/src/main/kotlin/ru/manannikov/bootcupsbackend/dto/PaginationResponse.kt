package ru.manannikov.bootcupsbackend.dto

data class PaginationResponse<T>(
    val content: List<T>,

    val currentPageNumber: Int,
    val totalElements: Long,
    val totalPages: Int,

    val hasPrevious: Boolean,
    val hasNext: Boolean
)
