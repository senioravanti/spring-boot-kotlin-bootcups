package ru.manannikov.bootcupsbackend.entities

import jakarta.persistence.*
import java.util.*

@Entity
@Table(name = "units")
class UnitEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "unit_id")
    var id: Short? = null

    @Column(name = "unit_name", unique = true, length = 32)
    lateinit var name: String
    @Column(name = "unit_label", unique = true, length = 6)
    lateinit var label: String

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as UnitEntity
        return if (this.id != null) this.id == other.id
        else this.label == other.label && this.name == other.name
    }

    override fun hashCode(): Int = Objects.hash(label, name)

    override fun toString(): String {
        return "Unit(id=$id, name='$name', label='$label')"
    }
}