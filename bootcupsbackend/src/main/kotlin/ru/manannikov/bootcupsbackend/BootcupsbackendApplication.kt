package ru.manannikov.bootcupsbackend

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.core.env.AbstractEnvironment

@SpringBootApplication
class BootcupsbackendApplication {
	companion object {
		@JvmStatic
		fun main(args: Array<String>) {
			System.setProperty(AbstractEnvironment.ACTIVE_PROFILES_PROPERTY_NAME, "dev")
			runApplication<BootcupsbackendApplication>(*args)
		}
	}
}

