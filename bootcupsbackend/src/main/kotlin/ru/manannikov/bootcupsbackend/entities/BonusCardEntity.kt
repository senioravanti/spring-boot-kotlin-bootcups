package ru.manannikov.bootcupsbackend.entities

import jakarta.persistence.*
import java.math.BigDecimal
import java.util.*

@Entity
@Table(name = "bonus_cards")
class BonusCardEntity {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "bonus_card_id")
    var id: Long? = null

    @Column(name = "bonus_card_amount", nullable = false, precision = 8, scale = 2)
    var amount: BigDecimal = BigDecimal("150.0")

    @Column(name = "bonus_card_discount_percent", nullable = false)
    var discountPercent: Short = 5

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other == null || this.javaClass != other.javaClass) return false

        other as BonusCardEntity
        return if (this.id != null) this.id == other.id
        else this.amount == other.amount && this.discountPercent == other.discountPercent
    }

    override fun hashCode(): Int = Objects.hash(this.amount, this.discountPercent)

    override fun toString(): String = "BonusCardEntity(amount=$amount, discountPercent=$discountPercent)"
}