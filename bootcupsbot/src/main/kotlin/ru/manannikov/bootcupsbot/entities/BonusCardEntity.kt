package ru.manannikov.bootcupsbot.entities


import jakarta.persistence.*
import java.math.BigDecimal

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

    override fun toString(): String = "BonusCardEntity(amount=$amount, discountPercent=$discountPercent)"
}
