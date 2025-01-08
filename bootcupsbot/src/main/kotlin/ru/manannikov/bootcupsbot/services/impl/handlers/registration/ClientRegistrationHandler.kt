package ru.manannikov.bootcupsbot.services.impl.handlers.registration


import jakarta.annotation.PostConstruct
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.stereotype.Service
import ru.manannikov.bootcupsbot.dto.MessageDto
import ru.manannikov.bootcupsbot.dto.MessageResponse
import ru.manannikov.bootcupsbot.services.KeyboardService
import ru.manannikov.bootcupsbot.services.UpdateHandler

import ru.manannikov.bootcupsbot.enums.RegistrationState
import ru.manannikov.bootcupsbot.services.KeyboardSupplier
import java.util.*
import java.util.function.Function

/**
 * Отвечает за последовательную регистрацию клиента
 * Должен оперировать только тем пользователем, который есть в БД
 */
@Service("clientRegistrationHandler")
class ClientRegistrationHandler(
    private val keyboardService: KeyboardService,

    @Qualifier("askNameRegistrationHandler")
    private val askNameRegistrationHandler: UpdateHandler,

    @Qualifier("askBirthDateRegistrationHandler")
    private val askBirthDateRegistrationHandler: UpdateHandler,

    @Qualifier("askPhoneNumberRegistrationHandler")
    private val askPhoneNumberRegistrationHandler: UpdateHandler,

    @Qualifier("askEmailRegistrationHandler")
    private val askEmailRegistrationHandler: UpdateHandler
)
    : UpdateHandler
{
    private val askRegistrationHandler = UpdateHandler {
        messageDto -> MessageResponse().apply {
            chatId = messageDto.client.chatId
            messageCode = "messages.command.start.anonymous"
            keyboard = KeyboardSupplier { keyboardService.registerKeyboard(it) }
        }
    }

    @PostConstruct
    fun init() {
        registrationHandlers[RegistrationState.ASK_REGISTRATION] = askRegistrationHandler

        registrationHandlers[RegistrationState.ASK_NAME] = askNameRegistrationHandler

        registrationHandlers[RegistrationState.ASK_BIRTHDATE] = askBirthDateRegistrationHandler

        registrationHandlers[RegistrationState.ASK_PHONE_NUMBER] = askPhoneNumberRegistrationHandler

        registrationHandlers[RegistrationState.ASK_EMAIL] = askEmailRegistrationHandler
    }

    override fun apply(messageDto: MessageDto): MessageResponse =
        registrationHandlers[messageDto.client.registrationState]
            ?. apply(messageDto)
            ?: throw IllegalArgumentException("У клиента не может быть других состояний, кроме определенных в перечислении RegistrationState")


    companion object {
        private val registrationHandlers: MutableMap<RegistrationState, UpdateHandler?> =
            EnumMap(RegistrationState::class.java)
    }
}
