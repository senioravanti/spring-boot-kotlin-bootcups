package ru.manannikov.bootcupsbackend.entities

import jakarta.persistence.*
import java.util.*

@Entity
@Table(name = "products")
class ProductEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "product_id")
    var id: Short? = null

    @Column(name = "product_name", unique = true, length = 512)
    lateinit var name: String

    @Column(name = "product_description")
    var description: String? = null

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
        name = "product_categories",
        joinColumns = [JoinColumn(name = "product_id", nullable = false)],
        inverseJoinColumns = [JoinColumn(name = "category_id", nullable = false)]
    )
    val category: Set<CategoryEntity> = mutableSetOf()

    @OneToMany(mappedBy = "product", cascade = [CascadeType.PERSIST, CascadeType.REMOVE])
    val menuItems: Set<MenuItemEntity> = mutableSetOf()

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other == null || this.javaClass != other.javaClass) return false

        other as ProductEntity
        return if (this.id != null) this.id == other.id
        else this.name == other.name
    }

    override fun hashCode(): Int = Objects.hash(name)

    override fun toString(): String {
        return "Product(id=$id, name='$name', description=$description)"
    }
}