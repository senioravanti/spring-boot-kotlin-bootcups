package ru.manannikov.bootcupsbackend.utils

const val LAST_NAME_REGEXP = """^([a-zA-Z]{2,64}|[а-яА-Я]{2,64})$"""
const val FIRST_NAME_REGEXP = """^([a-zA-Z]{2,32}|[а-яА-Я]{2,32})$"""
const val MIDDLE_NAME_REGEXP = """^([a-zA-Z]{0,36}|[а-яА-Я]{0,36})$"""

const val USERNAME_REGEXP = """^(?=[a-zA-Z])(?=[0-9_]?)(?=^\w+$).{5,128}$"""
const val PASSWORD_REGEXP = """^(?=.*[0-9])(?=.*[A-Z])(?=.*[*!@#$%^&()\[\]<>{}\-+=|~_])(?=^[^а-яА-Я\\\s]+$).{5,100}$"""

const val EMAIL_REGEXP = """^(?=[a-zA-Z])(?=[\w.-]+@[a-z-]+\.[a-z]{2,}).{6,100}$"""
const val PHONE_NUMBER_REGEXP = """^([+][0-9]{1,3}\s?[0-9()]{3,5}\s?[0-9]{3}\s?[0-9]{2}[-\s]?[0-9]{2})$"""
