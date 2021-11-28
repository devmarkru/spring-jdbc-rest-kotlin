package ru.devmark.cargo.repository

import org.springframework.jdbc.core.ResultSetExtractor
import org.springframework.jdbc.core.RowMapper
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate
import org.springframework.jdbc.support.GeneratedKeyHolder
import org.springframework.stereotype.Repository
import ru.devmark.cargo.model.Cargo
import ru.devmark.cargo.util.getIntOrNull

@Repository
class CargoRepositoryImpl(
    private val jdbcTemplate: NamedParameterJdbcTemplate,
) : CargoRepository {

    override fun getAll(pageIndex: Int): List<Cargo> =
        jdbcTemplate.query(
            "select * from cargo order by id limit :limit offset :offset",
            mapOf(
                "limit" to PAGE_SIZE,
                "offset" to PAGE_SIZE * pageIndex,
            ),
            ROW_MAPPER
        )

    override fun findById(id: Int): Cargo? =
        jdbcTemplate.query(
            "select * from cargo where id = :id",
            mapOf(
                "id" to id,
            ),
            ROW_MAPPER
        ).firstOrNull()

    override fun create(title: String, passengerCount: Int?): Int {
        val keyHolder = GeneratedKeyHolder()
        jdbcTemplate.update(
            "insert into cargo (title, passenger_count) values (:title, :passengerCount)",
            MapSqlParameterSource(
                mapOf(
                    "title" to title,
                    "passengerCount" to passengerCount,
                )
            ),
            keyHolder,
            listOf("id").toTypedArray()
        )
        return keyHolder.keys?.getValue("id") as Int
    }

    override fun update(id: Int, title: String, passengerCount: Int?) {
        jdbcTemplate.update(
            "update cargo set title = :title, passenger_count = :passengerCount where id = :id",
            mapOf(
                "id" to id,
                "title" to title,
                "passengerCount" to passengerCount,
            )
        )
    }

    override fun deleteById(id: Int) {
        jdbcTemplate.update(
            "delete from cargo where id = :id",
            mapOf(
                "id" to id,
            )
        )
    }

    override fun getCarStatistics(): Map<String, Int> =
        jdbcTemplate.query(
            """select cb.title, count(c.id) from cargo c
                   join car_brand cb on c.brand_id = cb.id
                   group by cb.title""",
            EXTRACTOR,
        )!!

    private companion object {
        const val PAGE_SIZE = 3
        val ROW_MAPPER = RowMapper<Cargo> { rs, _ ->
            Cargo(
                id = rs.getInt("id"),
                title = rs.getString("title"),
                passengerCount = rs.getIntOrNull("passenger_count"),
            )
        }

        val EXTRACTOR = ResultSetExtractor<Map<String, Int>> { rs ->
            val result = mutableMapOf<String, Int>()
            while(rs.next()) {
                val title = rs.getString("title")
                result.getOrPut(title) { 0 }
                result[title] = result.getValue(title) + rs.getInt("count")
            }
            result
        }
    }
}