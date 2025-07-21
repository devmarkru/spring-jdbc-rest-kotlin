package ru.devmark.cargo.dto

data class CargoDto(
    val id: Int = 0,
    val title: String,
    val passengerCount: Int? = null,
)
