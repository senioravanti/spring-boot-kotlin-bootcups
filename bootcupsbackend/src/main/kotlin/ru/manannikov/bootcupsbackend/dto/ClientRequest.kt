package ru.manannikov.bootcupsbackend.dto

import jakarta.validation.constraints.AssertTrue
import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.NotNull
import jakarta.validation.constraints.Pattern
import ru.manannikov.bootcupsbackend.enums.RegistrationState
import ru.manannikov.bootcupsbackend.utils.EMAIL_REGEXP
import ru.manannikov.bootcupsbackend.utils.PHONE_NUMBER_REGEXP
import ru.manannikov.bootcupsbackend.validation.AskBirthdateGroup
import ru.manannikov.bootcupsbackend.validation.AskEmailGroup
import ru.manannikov.bootcupsbackend.validation.AskNameGroup
import ru.manannikov.bootcupsbackend.validation.AskPhoneNumberGroup
import java.time.LocalDate

class ClientRequest {
    var id: Long? = null
    var chatId: Long? = null

    lateinit var registrationState: RegistrationState

    @get:NotBlank(groups = [AskNameGroup::class], message = "messages.validation.client-dto.name")
    var name: String? = null

    @get:NotNull(groups = [AskBirthdateGroup::class])
    var birthday: LocalDate? = null

    @get:Pattern(regexp = EMAIL_REGEXP, message = "messages.validation.client-dto.email", groups = [AskEmailGroup::class])
    var email: String? = null

    @get:Pattern(regexp = PHONE_NUMBER_REGEXP, groups = [AskPhoneNumberGroup::class], message = "messages.validation.client-dto.phone-number")
    var phoneNumber: String? = null

    @AssertTrue(groups = [AskBirthdateGroup::class], message = "messages.validation.client-dto.birthday")
    fun isCapable(): Boolean = birthday!!.isBefore(LocalDate.now().minusYears(14))
}
