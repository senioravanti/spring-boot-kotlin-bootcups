package ru.manannikov.bootcupsbackend.entities

import jakarta.persistence.*
import java.util.*

@Entity
@Table(name = "employees")
class EmployeeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "employee_id")
    var id: Int? = null

    @Column(name = "employee_last_name", length = 64)
    lateinit var lastName: String
    @Column(name = "employee_first_name", length = 32)
    lateinit var fistName: String
    @Column(name = "employee_middle_name", length = 36)
    var middleName: String? = null

    @Column(name = "employee_username", length = 128)
    lateinit var username: String
    @Column(name = "employee_password", length = 100, unique = true)
    lateinit var password: String

    @Column(name = "employee_email", unique = true, length = 100)
    lateinit var email: String
    @Column(name = "employee_phone_number", unique = true, length = 18)
    lateinit var phoneNumber: String

    @ManyToOne(optional = false)
    @JoinColumn(name = "role_id")
    lateinit var role: RoleEntity

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other == null || this.javaClass != other.javaClass) return false

        other as EmployeeEntity
        return if (this.id != null) this.id == other.id
        else this.username == other.username && this.phoneNumber == other.phoneNumber && this.email == other.email
    }

    override fun hashCode(): Int = Objects.hash(username, phoneNumber, email)

    override fun toString(): String {
        return "Employee(id=$id, lastName='$lastName', fistName='$fistName', middleName=$middleName, username='$username', password='$password', email='$email', phoneNumber='$phoneNumber')"
    }


}