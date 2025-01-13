package ru.manannikov.bootcupsbackend.entities

import jakarta.persistence.*
import java.util.*

@Entity
@Table(name = "categories")
class CategoryEntity : DictionaryEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "category_id")
    override var id: Short? = null

    @Column(name = "category_key", unique = true, length = 24)
    override lateinit var key: String

    @Column(name = "category_name", unique = true, length = 128)
    override lateinit var name: String

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other == null || this.javaClass != other.javaClass) return false

        other as CategoryEntity
        return if (this.id != null) this.id == other.id
        else this.key == other.key && this.name == other.name
    }

    override fun hashCode(): Int = Objects.hash(key, name)

    override fun toString(): String {
        return "Category(id=$id, key=$key, name='$name')"
    }
}