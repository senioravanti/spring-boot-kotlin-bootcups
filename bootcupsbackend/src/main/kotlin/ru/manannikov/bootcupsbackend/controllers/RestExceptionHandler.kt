package ru.manannikov.bootcupsbackend.controllers

import org.apache.logging.log4j.LogManager
import org.springframework.context.MessageSource
import org.springframework.dao.DataIntegrityViolationException
import org.springframework.http.HttpStatus
import org.springframework.http.ProblemDetail
import org.springframework.web.bind.MethodArgumentNotValidException
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestControllerAdvice
import ru.manannikov.bootcupsbackend.dto.ViolationDto
import ru.manannikov.bootcupsbackend.exceptions.EntityAlreadyExistsException
import ru.manannikov.bootcupsbackend.exceptions.NotFoundException
import java.net.URI
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.*

@RestControllerAdvice
class RestExceptionHandler(
    private val messageSource: MessageSource
) {
    @ExceptionHandler(MethodArgumentNotValidException::class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    fun handleMethodArgumentNotValidException(ex: MethodArgumentNotValidException)
        : ProblemDetail
    {
        val violations = ex.bindingResult.fieldErrors.asSequence().map { ViolationDto(it.field, it.defaultMessage ?: "") }.toList()

        val problemDetail = ProblemDetail.forStatusAndDetail(
            HttpStatus.BAD_REQUEST,
            messageSource.getMessage("exception.validation.detail", null, Locale.getDefault())
        ).apply {
            type = createTagURI(ex::class.qualifiedName!!)
            properties = mapOf(
                "object_name" to ex.bindingResult.objectName,
                "violations" to violations
            )
        }
        logger.error("Ошибка валидации (@Valid):\n{}", ex.toString())
        logger.info("violations:\n{}", violations)
        return problemDetail
    }

    @ExceptionHandler(exception = [IllegalArgumentException::class])
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    fun handleIllegalArgumentException(ex: IllegalArgumentException)
        : ProblemDetail
    {
        val problemDetail = ProblemDetail.forStatusAndDetail(
            HttpStatus.BAD_REQUEST,
            messageSource.getMessage("exception.illegal-argument.detail", null, Locale.getDefault())
        ).apply {
            type = createTagURI(ex::class.qualifiedName!!)
        }
        logger.error("Передано недопустимое значение параметра запроса:\n{}", ex.toString())
        return problemDetail
    }

    @ExceptionHandler(exception = [DataIntegrityViolationException::class])
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    fun handleDataIntegrityViolationException(ex: DataIntegrityViolationException)
        : ProblemDetail
    {
        val problemDetail = ProblemDetail.forStatusAndDetail(
            HttpStatus.BAD_REQUEST,
            messageSource.getMessage("exception.data-integrity-violation-exception.detail", null, Locale.getDefault())
        ).apply {
            type = createTagURI(ex::class.qualifiedName!!)
        }
        logger.error("Нарушено одно или несколько ограничений целостности БД:\n{}", ex.toString())
        return problemDetail
    }

    @ExceptionHandler(exception = [NotFoundException::class])
    @ResponseStatus(HttpStatus.NOT_FOUND)
    fun handleNotFoundException(
        ex: NotFoundException
    )
        : ProblemDetail
    {
        val args = mutableListOf<Any>(ex.entityClass)
        ex.entityId ?.let {args.plus(it)}
        val errorMessage = messageSource.getMessage(ex.message!!, args.toTypedArray(), Locale.getDefault())

        logger.error("Откат запроса на выборку:\n{}", errorMessage)
        return createProblemDetailForCustomException(
            "exception.not-found.detail",
            ex::class.qualifiedName!!,
            errorMessage
        )
    }

    @ExceptionHandler(exception = [EntityAlreadyExistsException::class])
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    fun handleEntityAlreadyExistsException(ex: EntityAlreadyExistsException)
        : ProblemDetail
    {
        val errorMessage = messageSource.getMessage(ex.message!!, arrayOf(ex.tableName), Locale.getDefault())

        logger.error("Откат запроса на добавление:\n{}", errorMessage)
        return createProblemDetailForCustomException(
            "exception.illegal-argument.detail",
            ex::class.qualifiedName!!,
            errorMessage
        )
    }

    @ExceptionHandler(exception = [Exception::class])
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    fun handleGenericException(
        ex: Exception
    )
        : ProblemDetail
    {
        val problemDetail = ProblemDetail.forStatusAndDetail(
            HttpStatus.INTERNAL_SERVER_ERROR,
            messageSource.getMessage("exception.exception.detail", null, Locale.getDefault())
        ).apply {
            type = createTagURI(ex::class.qualifiedName!!)
        }
        logger.error("Внутренняя ошибка сервера:\n{}", ex.toString())
        return problemDetail
    }

    private fun createProblemDetailForCustomException(
        problemDetailCode: String,
        qualifiedClassName: String,
        errorMessage: String
    )
        : ProblemDetail
    {
        return ProblemDetail.forStatusAndDetail(
            HttpStatus.BAD_REQUEST,
            messageSource.getMessage(problemDetailCode, null, Locale.getDefault())
        ).apply {
            type = createTagURI(qualifiedClassName)
            properties = mapOf(
                "description" to errorMessage
            )
        }
    }

    companion object {
        private val logger = LogManager.getLogger(RestExceptionHandler::class.java)
        private val dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss")

        private fun createTagURI(qualifiedClassName: String): URI = URI("tag", "$qualifiedClassName,${LocalDateTime.now().format(dateTimeFormatter)}", null)
    }
}