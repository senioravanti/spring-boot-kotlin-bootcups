package ru.manannikov.bootcupsbackend.utils

const val LAST_NAME_REGEXP = """^([a-zA-Z]{2,64}|[а-яА-Я]{2,64})$"""
const val FIRST_NAME_REGEXP = """^([a-zA-Z]{2,32}|[а-яА-Я]{2,32})$"""
const val MIDDLE_NAME_REGEXP = """^([a-zA-Z]{0,36}|[а-яА-Я]{0,36})$"""

const val USERNAME_REGEXP = """^(?=[a-zA-Z])(?=[0-9_]?)(?=^\w+$).{5,128}$"""
const val PASSWORD_REGEXP = """^(?=.*[0-9])(?=.*[A-Z])(?=.*[*!@#$%^&()\[\]<>{}\-+=|~_])(?=^[^а-яА-Я\\\s]+$).{5,100}$"""

const val EMAIL_REGEXP = """^(?=[a-zA-Z])(?=[\w.-]+@[a-z-]+\.[a-z]{2,}).{6,100}$"""
const val PHONE_NUMBER_REGEXP = """^([+][0-9]{1,3}\s?[0-9()]{3,5}\s?[0-9]{3}\s?[0-9]{2}[-\s]?[0-9]{2})$"""

const val UNIXTIME_REXEP = """^(?=^[1-9])(?=^[0-9]+$).{10,15}$"""
/**
 * Параметры запросов на выборку
 *
 * page_index -> Индекс текущей страницы (нач с нуля)
 * page_size -> Размер страницы
 * sort -> Массив ключей сортировки, должен включать ключи сортировки в формате:
 *       price_asc, price_desc, makes_asc, makes_desc
 */
const val PAGE_NUMBER = "page_number"
const val PAGE_SIZE = "page_size"

const val SORT = "sort"

const val EMPLOYEE_LAST_NAME = "last_name"
const val EMPLOYEE_FIRST_NAME = "first_name"
const val EMPLOYEE_MIDDLE_NAME = "middle_name"
const val EMPLOYEE_EMAIL = "employee_email"

const val CLIENT_NAME = "name"
const val CLIENT_EMAIL = "client_email"
const val CLIENT_ANONYMOUS = "anonymous"