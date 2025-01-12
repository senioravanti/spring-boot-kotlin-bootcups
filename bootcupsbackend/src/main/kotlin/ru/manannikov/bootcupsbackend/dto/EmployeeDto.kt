package ru.manannikov.bootcupsbackend.dto

import jakarta.validation.Valid
import jakarta.validation.constraints.Pattern
import ru.manannikov.bootcupsbackend.entities.EmployeeEntity
import ru.manannikov.bootcupsbackend.utils.*

data class EmployeeDto(
    val id: Int?,

    @Pattern(regexp = LAST_NAME_REGEXP, message = "{employee-entity.last-name.pattern}")
    val lastName: String,
    @Pattern(regexp = FIRST_NAME_REGEXP, message = "{employee-entity.first-name.pattern}")
    val firstName: String,
    @Pattern(regexp = MIDDLE_NAME_REGEXP, message = "{employee-entity.middle-name.pattern}")
    val middleName: String?,

    @Pattern(regexp = USERNAME_REGEXP, message = "{employee-entity.username.pattern}")
    val username: String,
    @Pattern(regexp = PASSWORD_REGEXP, message = "{employee-entity.password.pattern}")
    val password: String,

    @Pattern(regexp = EMAIL_REGEXP, message = "{employee-entity.email.pattern}")
    val email: String,

    @Pattern(regexp = PHONE_NUMBER_REGEXP, message = "{employee-entity.phone-number.pattern}")
    val phoneNumber: String,

    @Valid
    val role: RoleDto
) {
    fun toEntity() = EmployeeEntity().apply {
        this.id = this@EmployeeDto.id

        this.lastName = this@EmployeeDto.lastName
        this.firstName = this@EmployeeDto.firstName
        this.middleName = this@EmployeeDto.middleName

        this.username = this@EmployeeDto.username
        this.password = this@EmployeeDto.password

        this.email = this@EmployeeDto.email
        this.phoneNumber = this@EmployeeDto.phoneNumber

        this.role = this@EmployeeDto.role.toEntity()
    }

    companion object {
        fun of(employeeEntity: EmployeeEntity) = EmployeeDto(
            id = employeeEntity.id,

            lastName = employeeEntity.lastName,
            firstName = employeeEntity.firstName,
            middleName = employeeEntity.middleName,

            username = employeeEntity.username,
            password = employeeEntity.password,

            email = employeeEntity.email,
            phoneNumber = employeeEntity.phoneNumber,

            role = RoleDto.of(employeeEntity.role)
        )
    }
}