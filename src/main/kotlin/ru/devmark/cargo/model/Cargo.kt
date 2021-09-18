package ru.devmark.cargo.model

data class Cargo(
    val id: Int = 0,
    val title: String,
    val passengerCount: Int? = null,
)
