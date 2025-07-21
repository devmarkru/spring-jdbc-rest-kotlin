package ru.devmark.cargo.exception

import org.springframework.http.HttpStatus

abstract class BaseException(
    val errorCode: String,
    override val message: String,
    val status: HttpStatus
) : RuntimeException(message)
