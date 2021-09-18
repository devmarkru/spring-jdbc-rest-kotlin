package ru.devmark.cargo.exception

data class ApiError(
    val errorCode: String,
    val message: String,
)
