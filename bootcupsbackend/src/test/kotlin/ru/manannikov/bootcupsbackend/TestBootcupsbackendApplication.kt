package ru.manannikov.bootcupsbackend

import org.springframework.boot.fromApplication
import org.springframework.boot.with


fun main(args: Array<String>) {
	fromApplication<BootcupsbackendApplication>().with(TestcontainersConfiguration::class).run(*args)
}
