package ru.devmark.cargo.service

import org.springframework.stereotype.Service
import ru.devmark.cargo.dto.CargoDto
import ru.devmark.cargo.exception.CargoNotFoundException
import ru.devmark.cargo.entity.CargoEntity
import ru.devmark.cargo.repository.CargoRepository

@Service
class CargoService(
    private val cargoRepository: CargoRepository,
){

    fun getAll(pageIndex: Int): List<CargoDto> =
        cargoRepository.getAll(pageIndex)
            .map { it.toDto() }

    fun getById(id: Int): CargoDto =
        cargoRepository.findById(id)
            ?.toDto()
            ?: throw CargoNotFoundException(id)

    fun create(dto: CargoDto): Int =
        cargoRepository.create(dto.title, dto.passengerCount)

    fun update(id: Int, dto: CargoDto) {
        cargoRepository.update(id, dto.title, dto.passengerCount)
    }

    fun deleteById(id: Int) {
        cargoRepository.deleteById(id)
    }

    fun getCarStatistics(): Map<String, Int> =
        cargoRepository.getCarStatistics()

    private fun CargoEntity.toDto() = CargoDto(
        id = id,
        title = title,
        passengerCount = passengerCount,
    )
}