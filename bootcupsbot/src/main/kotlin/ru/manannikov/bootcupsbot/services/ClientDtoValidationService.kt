package ru.manannikov.bootcupsbot.services

import ru.manannikov.bootcupsbot.dto.ClientDto
import ru.manannikov.bootcupsbot.enums.RegistrationState

interface ClientDtoValidationService {
    fun validate(clientDto: ClientDto, nextRegistrationState: RegistrationState, messageCode: String, validationGroup: Class<*>): String
}