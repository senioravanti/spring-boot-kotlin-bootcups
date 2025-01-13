package ru.manannikov.bootcupsbackend.entities

import jakarta.persistence.*
import ru.manannikov.bootcupsbackend.enums.RegistrationState
import java.time.LocalDate
import java.util.*

/**
 * id -> суррогатный первичный ключ, генерируется СУБД
 * chatId -> альтернативный ключ, генерируется Telegram
 */

@Entity
@Table(name = "clients")
class ClientEntity {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "client_id")
    var id: Long? = null

    @Column(name = "client_chat_id")
    var chatId: Long? = null

    @Column(name = "client_name", length = 128)
    var name: String? = null

    @Column(name = "client_birthday")
    var birthday: LocalDate? = null

    @Column(name = "client_email", unique = true, length = 100)
    var email: String? = null

    @Column(name = "client_phone_number", unique = true, length = 18)
    var phoneNumber: String? = null

    @Column(name = "client_registration_state", nullable = false, length = 12)
    @Enumerated(EnumType.STRING)
    var registrationState: RegistrationState = RegistrationState.ASK_REGISTRATION

    @OneToOne(
        fetch = FetchType.LAZY,
        cascade = [CascadeType.PERSIST, CascadeType.REMOVE]
    )
    @JoinTable(
        name = "client_bonus_cards",
        joinColumns = [JoinColumn(
            name = "client_id",
            nullable = false,
            unique = true)
        ],
        inverseJoinColumns = [JoinColumn(name = "bonus_card_id", nullable = false, unique = true)]
    )
    var bonusCard: BonusCardEntity? = null

    @OneToMany(mappedBy = "client", cascade = [CascadeType.REMOVE])
    val orders: Set<OrderEntity> = mutableSetOf()

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other == null || this.javaClass != other.javaClass) return false

        other as ClientEntity
        return if (this.id != null) this.id == other.id
        else this.phoneNumber == other.phoneNumber && this.email == other.email
    }

    override fun hashCode(): Int = Objects.hash(phoneNumber, email)

    override fun toString() = "Client(id=$id, name=$name, birthday=$birthday, email=$email, phoneNumber=$phoneNumber, registrationState=$registrationState)"
}