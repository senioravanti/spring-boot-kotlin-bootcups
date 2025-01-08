package ru.manannikov.bootcupsbot.services.impl.handlers.message


import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.stereotype.Service
import ru.manannikov.bootcupsbot.dto.MessageDto
import ru.manannikov.bootcupsbot.dto.MessageResponse
import ru.manannikov.bootcupsbot.enums.RegistrationState
import ru.manannikov.bootcupsbot.services.UpdateHandler
import ru.manannikov.bootcupsbot.services.impl.handlers.registration.ClientRegistrationHandler

/**
 * Если клиент находится в базе данных, в состоянии REGISTERED, то он прислал задачу, о которой ему надо напомнить.
 * Предполагается, что сообщение есть и содержит текст
 */
@Service
class UpdateMessagesHandler(
    @Qualifier("registeredClientCommandsHandler")
    private val registeredClientCommandsHandler: UpdateHandler,

    @Qualifier("clientRegistrationHandler")
    private val clientRegistrationHandler: UpdateHandler
)
    : UpdateHandler
{
    override fun apply(messageDto: MessageDto): MessageResponse {
        return if (messageDto.client.registrationState == RegistrationState.REGISTERED)
            registeredClientCommandsHandler.apply(messageDto)
        else
            clientRegistrationHandler.apply(messageDto)
    }
}
