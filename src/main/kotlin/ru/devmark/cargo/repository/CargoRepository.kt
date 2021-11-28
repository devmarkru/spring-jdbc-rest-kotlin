package ru.devmark.cargo.repository

import ru.devmark.cargo.model.Cargo

interface CargoRepository {

    fun getAll(pageIndex: Int): List<Cargo>

    fun findById(id: Int): Cargo?

    fun create(title: String, passengerCount: Int?): Int

    fun update(id: Int, title: String, passengerCount: Int?)

    fun deleteById(id: Int)
}