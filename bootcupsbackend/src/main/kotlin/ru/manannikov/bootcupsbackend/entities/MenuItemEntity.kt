package ru.manannikov.bootcupsbackend.entities

import jakarta.persistence.*
import java.math.BigDecimal
import java.util.*

@Entity
@Table(name = "menu_items")
class MenuItemEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "menu_item_id")
    var id: Int? = null

    @Column(name = "menu_item_price", precision = 8, scale = 2)
    lateinit var price: BigDecimal
    @Column(name = "menu_item_makes")
    var makes: Short = 100
    @Column(name = "menu_item_topping", unique = true, length = 128)
    var topping: String? = null

    @Column(name = "menu_item_image_uri", unique = true, length = 1024)
    var imageUri: String? = null

    @ManyToOne(optional = false)
    @JoinColumn(name = "product_id")
    lateinit var product: ProductEntity

    @ManyToOne(optional = false)
    @JoinColumn(name = "unit_id")
    lateinit var unit: UnitEntity

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other == null || this.javaClass != other.javaClass) return false

        other as MenuItemEntity
        return if (this.id != null) this.id == other.id
        else this.product == other.product && this.price == other.price && this.makes == other.makes && this.topping == other.topping
    }

    override fun hashCode(): Int {
        return Objects.hash(product, price, makes, topping)
    }

    override fun toString(): String {
        return "MenuItem(id=$id, topping=$topping, makes=$makes, price=$price, imageUri=$imageUri, product=$product, unit=$unit)"
    }
}