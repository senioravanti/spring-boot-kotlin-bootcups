package ru.manannikov.bootcupsbot.services.impl.handlers.message


import org.springframework.stereotype.Service
import ru.manannikov.bootcupsbot.dto.ClientDto
import ru.manannikov.bootcupsbot.dto.MessageResponse
import ru.manannikov.bootcupsbot.services.CommandHandler
import ru.manannikov.bootcupsbot.utils.Constants.BONUS_CARD_COMMAND
import ru.manannikov.bootcupsbot.utils.Constants.NO_BREAK_SPACE
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

@Service("bonusCardCommandHandler")
class BonusCardCommandHandler : CommandHandler {
    override fun apply(clientDto: ClientDto): MessageResponse {
        val bonusCard = clientDto.bonusCard ?: throw IllegalArgumentException("У зарегистрированного клиента должна быть бонусная карта !")

        val args = arrayOf(
            BONUS_CARD_COMMAND,
            NO_BREAK_SPACE,
            LocalDateTime.now().format(formatter),
            bonusCard.amount.toPlainString(),
            java.lang.String.valueOf(bonusCard.discountPercent)
        )

        return MessageResponse().apply {
            chatId = clientDto.chatId
            messageCode = "messages.command.bonus-card.reply"
            this.args = args
        }
    }

    companion object {
        private val formatter: DateTimeFormatter = DateTimeFormatter.ofPattern("hh:mm dd.MM.yyyy")
    }
}
