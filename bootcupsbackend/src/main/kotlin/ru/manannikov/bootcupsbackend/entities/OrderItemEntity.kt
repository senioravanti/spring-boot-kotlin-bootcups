package ru.manannikov.bootcupsbackend.entities

import jakarta.persistence.*
import java.math.BigDecimal
import java.util.*

@Entity
@Table(name = "order_items")
class OrderItemEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "order_item_id")
    var id: Long? = null

    @Column(name = "order_item_quantity")
    var quantity: Short = 1
    @Column(name = "order_item_purchase", precision = 10, scale = 2)
    lateinit var purchase: BigDecimal

    @ManyToOne(optional = false)
    @JoinColumn(name = "order_id")
    lateinit var order: OrderEntity

    @ManyToOne(optional = false)
    @JoinColumn(name = "menu_item_id")
    lateinit var menuItem: MenuItemEntity

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other == null || this.javaClass != other.javaClass) return false

        other as OrderItemEntity
        return if (this.id != null) this.id == other.id
        else this.order == other.order && this.menuItem == other.menuItem && this.purchase == other.purchase && this.quantity == other.quantity
    }

    override fun hashCode(): Int = Objects.hash(order, menuItem, purchase, quantity)

    override fun toString(): String {
        return "OrderItem(id=$id, quantity=$quantity, purchase=$purchase, order=$order, menuItem=$menuItem)"
    }
}