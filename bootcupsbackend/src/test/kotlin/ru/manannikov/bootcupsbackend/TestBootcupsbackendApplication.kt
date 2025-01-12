package ru.manannikov.bootcupsbackend

import org.springframework.boot.fromApplication


fun main(args: Array<String>) {
	fromApplication<BootcupsbackendApplication>().run(*args)
}
