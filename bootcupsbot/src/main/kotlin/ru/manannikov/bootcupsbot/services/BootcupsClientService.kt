package ru.manannikov.bootcupsbot.services

import ru.manannikov.bootcupsbot.dto.ClientDto


interface BootcupsClientService {
    fun loadClientByChatId(chatId: Long): ClientDto
    fun registerOrUpdateClient(clientDto: ClientDto)
}
