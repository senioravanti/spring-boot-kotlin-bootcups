package ru.manannikov.bootcupsbackend.entities

import jakarta.persistence.*
import ru.manannikov.bootcupsbackend.enums.Category
import java.util.*

@Entity
@Table(name = "categories")
class CategoryEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "category_id")
    var id: Short? = null

    @Column(name = "category_name", unique = true, length = 24)
    @Enumerated(EnumType.STRING)
    lateinit var name: Category

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other == null || this.javaClass != other.javaClass) return false

        other as CategoryEntity
        return if (this.id != null) this.id == other.id
        else this.name == other.name
    }

    override fun hashCode(): Int = Objects.hash(name)

    override fun toString(): String {
        return "Category(id=$id, name='$name')"
    }
}