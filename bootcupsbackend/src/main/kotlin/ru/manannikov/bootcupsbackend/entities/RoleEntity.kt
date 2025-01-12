package ru.manannikov.bootcupsbackend.entities

import jakarta.persistence.*
import ru.manannikov.bootcupsbackend.enums.RoleEnum
import java.util.*

@Entity
@Table(name = "roles")
class RoleEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "role_id")
    var id: Short? = null

    @Column(name = "role_name", unique = true, length = 32)
    @Enumerated(EnumType.STRING)
    var name: RoleEnum = RoleEnum.BARISTA

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other == null || this.javaClass != other.javaClass) return false

        other as RoleEntity
        return if (this.id != null) this.id == other.id
        else this.name == other.name
    }

    override fun hashCode(): Int = Objects.hash(name)

    override fun toString(): String {
        return "Role(id=$id, name='$name')"
    }
}