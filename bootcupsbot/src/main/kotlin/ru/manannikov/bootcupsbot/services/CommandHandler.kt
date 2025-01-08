package ru.manannikov.bootcupsbot.services


import ru.manannikov.bootcupsbot.dto.ClientDto
import ru.manannikov.bootcupsbot.dto.MessageResponse

@FunctionalInterface
fun interface CommandHandler : java.util.function.Function<ClientDto, MessageResponse>
