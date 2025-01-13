package ru.manannikov.bootcupsbackend.controllers

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/v1/order")
class OrderController {
    @GetMapping(path = ["", "/"])
    fun findAll() {

    }

    /**
     * Должен возвращать список OrderItemDto
     */
    @GetMapping("/{id}")
    fun findById(
        @PathVariable("id") id: Int
    ) {}


}