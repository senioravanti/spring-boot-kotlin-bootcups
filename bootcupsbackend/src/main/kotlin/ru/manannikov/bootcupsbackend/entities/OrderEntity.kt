package ru.manannikov.bootcupsbackend.entities

import jakarta.persistence.*
import ru.manannikov.bootcupsbackend.enums.OrderStatus
import java.math.BigDecimal
import java.time.Instant
import java.util.*

@Entity
@Table(name = "orders")
class OrderEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "order_id")
    var id: Int? = null

    @Column(name = "order_total_amount", precision = 10, scale = 2)
    lateinit var totalAmount: BigDecimal
    @Column(name = "order_discount_amount", precision = 8, scale = 2)
    lateinit var discountAmount: BigDecimal

    @Column(name = "order_created_at", unique = true)
    lateinit var createdAt: Instant

    @Column(name = "order_status", length = 16)
    @Enumerated(EnumType.STRING)
    var status: OrderStatus = OrderStatus.RAISED

    @ManyToOne
    @JoinColumn(name = "client_id")
    var client: ClientEntity? = null

    @ManyToOne(optional = false)
    @JoinColumn(name = "employee_id")
    lateinit var employee: EmployeeEntity

    @OneToMany(mappedBy = "parentOrder", cascade = [CascadeType.REMOVE], orphanRemoval = true)
    val orderItems: Set<OrderItemEntity> = mutableSetOf()

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other == null || this.javaClass != other.javaClass) return false

        other as OrderEntity
        return if (this.id != null) this.id == other.id
        else this.createdAt == other.createdAt && this.totalAmount == other.totalAmount && this.discountAmount == other.discountAmount
    }

    override fun hashCode(): Int = Objects.hash(createdAt, totalAmount, discountAmount)

    override fun toString(): String {
        return "Order(id=$id, totalAmount=$totalAmount, discountAmount=$discountAmount, createdAt=$createdAt, status=$status, client=$client, employee=$employee)"
    }


}