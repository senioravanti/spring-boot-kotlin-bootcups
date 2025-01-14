package ru.manannikov.bootcupsbackend.dto

import jakarta.validation.Valid
import java.time.LocalDate

data class ClientResponse(
    val id: Long,
    val chatId: Long,

    val name: String,

    val birthday: LocalDate,
    val email: String,
    val phoneNumber: String,

    @get:Valid
    val bonusCard: BonusCardDto
)
