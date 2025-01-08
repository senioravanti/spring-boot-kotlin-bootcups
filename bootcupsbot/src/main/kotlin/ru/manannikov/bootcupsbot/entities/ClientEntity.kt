package ru.manannikov.bootcupsbot.entities


import jakarta.persistence.*
import ru.manannikov.bootcupsbot.enums.RegistrationState
import java.time.LocalDate

@Entity
@Table(name = "clients")
class ClientEntity {
    @Id
    @Column(name = "client_id")
    var id: Long? = null

    @Column(name = "client_name", length = 64)
    var name: String? = null

    @Column(name = "client_birthday")
    var birthday: LocalDate? = null

    @Column(name = "client_email", unique = true, length = 100)
    var email: String? = null

    @Column(name = "client_phone_number", unique = true, length = 18)
    var phoneNumber: String? = null

    @Column(name = "client_registration_state", nullable = false, length = 16)
    @Enumerated(EnumType.STRING)
    lateinit var registrationState: RegistrationState

    // lazy loading :: Отложенная загрузка
    @OneToOne(fetch = FetchType.LAZY, cascade = [CascadeType.PERSIST, CascadeType.REMOVE])
    @JoinTable(
        name = "client_bonus_card",
        joinColumns = [JoinColumn(name = "client_id")],
        inverseJoinColumns = [JoinColumn(name = "bonus_card_id", nullable = false, unique = true)]
    )
    var bonusCard: BonusCardEntity? = null
}
