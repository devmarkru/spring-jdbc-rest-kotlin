package ru.devmark.cargo.service

import org.springframework.stereotype.Service
import ru.devmark.cargo.dto.CargoDto
import ru.devmark.cargo.exception.CargoNotFoundException
import ru.devmark.cargo.model.Cargo
import ru.devmark.cargo.repository.CargoRepository

@Service
class CargoServiceImpl(
    private val cargoRepository: CargoRepository,
) : CargoService {

    override fun getAll(): List<CargoDto> = cargoRepository.getAll()
        .map { it.toDto() }

    override fun getById(id: Int): CargoDto =
        cargoRepository.findById(id)
            ?.toDto()
            ?: throw CargoNotFoundException(id)

    override fun create(dto: CargoDto): Int =
        cargoRepository.create(dto.title, dto.passengerCount)

    override fun update(id: Int, dto: CargoDto) {
        cargoRepository.update(id, dto.title, dto.passengerCount)
    }

    override fun deleteById(id: Int) {
        cargoRepository.deleteById(id)
    }

    private fun Cargo.toDto() = CargoDto(
        id = id,
        title = title,
        passengerCount = passengerCount,
    )
}