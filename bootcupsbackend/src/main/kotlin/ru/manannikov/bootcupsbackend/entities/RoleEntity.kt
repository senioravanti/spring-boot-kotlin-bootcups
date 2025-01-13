package ru.manannikov.bootcupsbackend.entities

import jakarta.persistence.*
import java.util.*

/**
 * key -> название роли на латинице в snake case, подходящее для контроллера
 * name -> код названия роли, предназначен для messageSource
 */
@Entity
@Table(name = "roles")
class RoleEntity : DictionaryEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "role_id")
    override var id: Short? = null

    @Column(name = "role_key", unique = true, length = 32)
    override lateinit var key: String

    @Column(name = "role_name", unique = true, length = 128)
    override lateinit var name: String

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other == null || this.javaClass != other.javaClass) return false

        other as RoleEntity
        return if (this.id != null) this.id == other.id
        else this.key == other.key && this.name == other.name
    }

    override fun hashCode(): Int = Objects.hash(key, name)

    override fun toString(): String {
        return "Role(id=$id, key=$key, name='$name')"
    }
}