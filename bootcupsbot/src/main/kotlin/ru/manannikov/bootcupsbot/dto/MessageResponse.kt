package ru.manannikov.bootcupsbot.dto


import ru.manannikov.bootcupsbot.services.KeyboardSupplier

class MessageResponse {
    var chatId: Long = -1
    lateinit var messageCode: String

    var args: Array<*>? = null

    var keyboard: KeyboardSupplier? = null

    var replyToMessageId: Int? = null
}
