package ru.manannikov.bootcupsbot.dto


class MessageDto {
    lateinit var client: ClientDto
    lateinit var command: String

    var replyToMessageId: Int? = null
}
