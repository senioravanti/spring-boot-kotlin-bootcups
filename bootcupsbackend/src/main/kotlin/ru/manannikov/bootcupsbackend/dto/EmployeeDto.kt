package ru.manannikov.bootcupsbackend.dto

import jakarta.validation.constraints.Pattern
import ru.manannikov.bootcupsbackend.utils.*

data class EmployeeDto(
    val id: Int?,

    @field:Pattern(regexp = LAST_NAME_REGEXP, message = "{employee-entity.last-name.pattern}")
    val lastName: String,
    @field:Pattern(regexp = FIRST_NAME_REGEXP, message = "{employee-entity.first-name.pattern}")
    val firstName: String,
    @field:Pattern(regexp = MIDDLE_NAME_REGEXP, message = "{employee-entity.middle-name.pattern}")
    val middleName: String?,

    @field:Pattern(regexp = USERNAME_REGEXP, message = "{employee-entity.username.pattern}")
    val username: String,
    @field:Pattern(regexp = PASSWORD_REGEXP, message = "{employee-entity.password.pattern}")
    val password: String,

    @field:Pattern(regexp = EMAIL_REGEXP, message = "{employee-entity.email.pattern}")
    val email: String,

    @field:Pattern(regexp = PHONE_NUMBER_REGEXP, message = "{employee-entity.phone-number.pattern}")
    val phoneNumber: String,

    /**
     * Если это ответ, то название роли, если запрос, то код роли
     */
    val role: String
)