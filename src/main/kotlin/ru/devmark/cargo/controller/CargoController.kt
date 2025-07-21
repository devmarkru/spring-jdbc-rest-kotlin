package ru.devmark.cargo.controller

import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import ru.devmark.cargo.dto.CargoDto
import ru.devmark.cargo.service.CargoService

@RestController
@RequestMapping("/cargo")
class CargoController(
    private val cargoService: CargoService,
) {

    @GetMapping
    fun getAll(
        @RequestParam("page") pageIndex: Int,
    ): List<CargoDto> = cargoService.getAll(pageIndex)

    @GetMapping("/{id}")
    fun getById(@PathVariable id: Int): CargoDto = cargoService.getById(id)

    @GetMapping("/statistics")
    fun getCarStatistics(): Map<String, Int> =
        cargoService.getCarStatistics()

    @PostMapping
    fun create(@RequestBody dto: CargoDto): Int =
        cargoService.create(dto)

    @PutMapping("/{id}")
    fun update(@PathVariable id: Int, @RequestBody dto: CargoDto) {
        cargoService.update(id, dto)
    }

    @DeleteMapping("/{id}")
    fun deleteById(@PathVariable id: Int) {
        cargoService.deleteById(id)
    }
}
