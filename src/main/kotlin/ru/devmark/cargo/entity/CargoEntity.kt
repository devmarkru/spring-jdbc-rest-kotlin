package ru.devmark.cargo.entity

data class CargoEntity(
    val id: Int = 0,
    val title: String,
    val passengerCount: Int? = null,
)
