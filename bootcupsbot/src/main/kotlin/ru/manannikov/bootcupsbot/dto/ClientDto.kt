package ru.manannikov.bootcupsbot.dto


import jakarta.validation.constraints.AssertTrue
import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.NotNull
import jakarta.validation.constraints.Pattern
import ru.manannikov.bootcupsbot.enums.RegistrationState
import ru.manannikov.bootcupsbot.validation.AskBirthdateGroup
import ru.manannikov.bootcupsbot.validation.AskEmailGroup
import ru.manannikov.bootcupsbot.validation.AskNameGroup
import ru.manannikov.bootcupsbot.validation.AskPhoneNumberGroup
import java.time.LocalDate

class ClientDto(val chatId: Long) {
    lateinit var registrationState: RegistrationState

    @NotBlank(groups = [AskNameGroup::class], message = "messages.validation.client-dto.name")
    var name: String? = null

    @NotNull(groups = [AskBirthdateGroup::class])
    var birthday: LocalDate? = null

    @Pattern(regexp = "^([A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+)\$", message = "messages.validation.client-dto.email", groups = [AskEmailGroup::class])
    var email: String? = null

    @Pattern(regexp = "^([+]{1}[0-9]{1,3}\\s?[0-9()]{3,5}\\s?[0-9]{3}\\s?[0-9]{2}[-\\s]?[0-9]{2})\$", groups = [AskPhoneNumberGroup::class], message = "messages.validation.client-dto.phone-number")
    var phoneNumber: String? = null

    var bonusCard: BonusCardDto? = null

    @AssertTrue(groups = [AskBirthdateGroup::class], message = "messages.validation.client-dto.birthday")
    fun isCapable(): Boolean = birthday!!.isBefore(LocalDate.now().minusYears(14))
}
