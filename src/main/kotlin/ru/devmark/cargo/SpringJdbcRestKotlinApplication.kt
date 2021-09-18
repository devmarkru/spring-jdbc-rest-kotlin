package ru.devmark.cargo

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class SpringJdbcRestKotlinApplication

fun main(args: Array<String>) {
    runApplication<SpringJdbcRestKotlinApplication>(*args)
}
