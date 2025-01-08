package ru.manannikov.bootcupsbot

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class BootcupsBotApplication {
	companion object {
		@JvmStatic
		fun main(args: Array<String>) {
			runApplication<BootcupsBotApplication>(*args)
		}
	}
}