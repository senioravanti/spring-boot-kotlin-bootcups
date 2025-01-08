package ru.manannikov.bootcupsbot.services


import ru.manannikov.bootcupsbot.dto.MessageDto
import ru.manannikov.bootcupsbot.dto.MessageResponse

fun interface UpdateHandler : java.util.function.Function<MessageDto, MessageResponse>

