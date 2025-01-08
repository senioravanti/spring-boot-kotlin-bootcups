package ru.manannikov.bootcupsbot.services.impl


import org.apache.logging.log4j.LogManager
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import ru.manannikov.bootcupsbot.dto.ClientDto
import ru.manannikov.bootcupsbot.entities.BonusCardEntity
import ru.manannikov.bootcupsbot.entities.ClientEntity
import ru.manannikov.bootcupsbot.enums.RegistrationState
import ru.manannikov.bootcupsbot.services.BootcupsClientService
import ru.manannikov.bootcupsbot.services.ClientService
import ru.manannikov.bootcupsbot.utils.Mapper

@Service
@Transactional
class BootcupsClientServiceImpl(
    private val clientService: ClientService
) : BootcupsClientService {

    override fun loadClientByChatId(chatId: Long): ClientDto {
        val clientEntity: ClientEntity? = clientService.findById(chatId)
        val clientDto = ClientDto(chatId)

        return if (clientEntity != null) {
            logger.debug("Из БД получены данные клиента с id = {} и бонусной картой с id = {}", clientEntity.id, clientEntity.bonusCard ?: "null")
            Mapper.clientEntityToDto(clientEntity)
        } else {
            logger.debug("Обработка сообщения от нового клиента")
            clientDto.apply {
                registrationState = RegistrationState.ASK_REGISTRATION
            }
        }
    }

    /**
     * Если клиент еще не зарегистрирован, то мы его регистрируем, иначе обновляем.
     */
    override fun registerOrUpdateClient(clientDto: ClientDto) {
        val clientEntityToUpdate: ClientEntity = Mapper.clientEntityFromDto(clientDto)

        clientService.findById(clientDto.chatId)?.let {
            existingClientEntity ->
                logger.debug("В БД найден клиент с id = {}", clientDto.chatId)
                clientEntityToUpdate.id = existingClientEntity.id
                if (existingClientEntity.bonusCard == null && clientEntityToUpdate.registrationState == RegistrationState.REGISTERED) {
                    clientEntityToUpdate.bonusCard = BonusCardEntity()
                    logger.debug("Клиенту с chatId = {} присвоена бонусная карта: {}", clientDto.chatId, clientEntityToUpdate.bonusCard)
                }
        }

        clientService.save(clientEntityToUpdate)
        logger.info("Данные клиента с chatId = {} успешно обновлены", clientDto.chatId)
    }

    companion object {
        private val logger = LogManager.getLogger(BootcupsClientServiceImpl::class.java)
    }
}