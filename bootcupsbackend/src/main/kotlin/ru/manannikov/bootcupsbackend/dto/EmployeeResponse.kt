package ru.manannikov.bootcupsbackend.dto

class EmployeeResponse(
    val id: Int,

    val lastName: String,
    val firstName: String,
    val middleName: String?,

    val username: String,
    val password: String,

    val email: String,
    val phoneNumber: String,
) {
    lateinit var role: DictionaryDto

    override fun toString(): String {
        return "EmployeeResponse(id=$id, lastName='$lastName', firstName='$firstName', middleName=$middleName, username='$username', password='$password', email='$email', phoneNumber='$phoneNumber', role=$role)"
    }
}
