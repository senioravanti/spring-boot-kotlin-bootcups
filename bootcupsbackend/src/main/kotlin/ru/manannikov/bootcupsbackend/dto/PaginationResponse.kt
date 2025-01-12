package ru.manannikov.bootcupsbackend.dto

import com.fasterxml.jackson.annotation.JsonProperty

data class PaginationResponse<T>(
    val content: List<T>,

    val currentPageNumber: Int,
    val totalElements: Long,
    val totalPages: Int,

    val hasPrevious: Boolean,
    val hasNext: Boolean
)
