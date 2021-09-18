package ru.devmark.cargo.exception

import org.springframework.http.HttpStatus

abstract class BaseException(
    val errorCode: String,
    override val message: String,
    val status: HttpStatus
) :
    RuntimeException(message)

class CargoNotFoundException(id: Int) : BaseException(
    errorCode = "cargo.not.found",
    message = "Cargo with id = $id not found",
    status = HttpStatus.NOT_FOUND,
)