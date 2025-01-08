package ru.manannikov.bootcupsbot.utils


import org.apache.logging.log4j.LogManager
import ru.manannikov.bootcupsbot.utils.Constants.NO_BREAK_SPACE
import java.util.*

object ObjectUtils {
    private val logger = LogManager.getLogger(ObjectUtils::class.java)

    fun extractCommandFromKeyboardButtonText(buttonText: String?): String {
        return Optional.ofNullable<String>(buttonText)
            .map<String> { b: String ->
                val noBreakSpaceIndex: Int = b.indexOf(NO_BREAK_SPACE)
                val command = if (noBreakSpaceIndex == -1
                ) ""
                else b.substring(0, noBreakSpaceIndex)
                logger.debug("parsed command: {}", command)
                command
            }
            .orElse("")
    }
}
