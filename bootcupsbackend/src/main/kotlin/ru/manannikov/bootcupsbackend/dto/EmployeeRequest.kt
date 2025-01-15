package ru.manannikov.bootcupsbackend.dto

import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.Pattern
import ru.manannikov.bootcupsbackend.utils.*

data class EmployeeRequest(
    val id: Int?,

    @get:Pattern(regexp = LAST_NAME_REGEXP, message = "{employee-entity.last-name.pattern}")
    val lastName: String,
    @get:Pattern(regexp = FIRST_NAME_REGEXP, message = "{employee-entity.first-name.pattern}")
    val firstName: String,
    @get:Pattern(regexp = MIDDLE_NAME_REGEXP, message = "{employee-entity.middle-name.pattern}")
    val middleName: String?,

    @get:Pattern(regexp = USERNAME_REGEXP, message = "{employee-entity.username.pattern}")
    val username: String,
    @get:Pattern(regexp = PASSWORD_REGEXP, message = "{employee-entity.password.pattern}")
    val password: String,

    @get:Pattern(regexp = EMAIL_REGEXP, message = "{employee-entity.email.pattern}")
    val email: String,

    @get:Pattern(regexp = PHONE_NUMBER_REGEXP, message = "{employee-entity.phone-number.pattern}")
    val phoneNumber: String,

    @get:NotBlank
    val roleKey: String
)