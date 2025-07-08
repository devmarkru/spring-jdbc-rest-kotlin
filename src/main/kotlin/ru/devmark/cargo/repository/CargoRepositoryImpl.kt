package ru.devmark.cargo.repository

import org.springframework.jdbc.core.ResultSetExtractor
import org.springframework.jdbc.core.RowMapper
import org.springframework.jdbc.core.simple.JdbcClient
import org.springframework.jdbc.support.GeneratedKeyHolder
import org.springframework.stereotype.Repository
import ru.devmark.cargo.model.Cargo
import ru.devmark.cargo.util.getIntOrNull

@Repository
class CargoRepositoryImpl(
    private val jdbcClient: JdbcClient,
) : CargoRepository {

    override fun getAll(pageIndex: Int): List<Cargo> =
        jdbcClient
            .sql("select * from cargo order by id limit :limit offset :offset")
            .param("limit", PAGE_SIZE)
            .param("offset", PAGE_SIZE * pageIndex)
            .query(ROW_MAPPER)
            .list()

    override fun findById(id: Int): Cargo? =
        jdbcClient
            .sql("select * from cargo where id = :id")
            .param("id", id)
            .query(ROW_MAPPER)
            .optional()
            .orElse(null)

    override fun create(title: String, passengerCount: Int?): Int {
        val keyHolder = GeneratedKeyHolder()
        jdbcClient
            .sql("insert into cargo (title, passenger_count) values (:title, :passengerCount)")
            .param("title", title)
            .param("passengerCount", passengerCount)
            .update(keyHolder, "id")
        return keyHolder.keys?.getValue("id") as Int
    }

    override fun update(id: Int, title: String, passengerCount: Int?) {
        jdbcClient
            .sql("update cargo set title = :title, passenger_count = :passengerCount where id = :id")
            .param("id", id)
            .param("title", title)
            .param("passengerCount", passengerCount)
            .update()
    }

    override fun deleteById(id: Int) {
        jdbcClient
            .sql("delete from cargo where id = :id")
            .param("id", id)
            .update()
    }

    override fun getCarStatistics(): Map<String, Int> =
        jdbcClient
            .sql(
                """select cb.title, count(c.id) from cargo c
                   join car_brand cb on c.brand_id = cb.id
                   group by cb.title"""
            )
            .query(EXTRACTOR)

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
    }}