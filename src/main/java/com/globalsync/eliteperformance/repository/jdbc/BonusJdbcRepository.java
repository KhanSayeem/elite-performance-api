package com.globalsync.eliteperformance.repository.jdbc;

import com.globalsync.eliteperformance.enums.EmployeeCategory;
import com.globalsync.eliteperformance.model.BonusRecord;
import com.globalsync.eliteperformance.repository.BonusRepository;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class BonusJdbcRepository implements BonusRepository {

    private static final RowMapper<BonusRecord> ROW_MAPPER = (rs, rowNum) -> new BonusRecord(
            rs.getLong("bonus_id"),
            rs.getLong("employee_id"),
            rs.getInt("review_year"),
            rs.getInt("total_kpi_score"),
            EmployeeCategory.valueOf(rs.getString("category")),
            rs.getInt("bonus_percentage"),
            rs.getBigDecimal("bonus_amount"),
            rs.getBigDecimal("total_compensation"),
            rs.getTimestamp("created_at").toLocalDateTime()
    );

    private final NamedParameterJdbcTemplate jdbcTemplate;

    public BonusJdbcRepository(NamedParameterJdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public BonusRecord save(BonusRecord bonusRecord) {
        String sql = """
                INSERT INTO bonus_records
                (employee_id, review_year, total_kpi_score, category, bonus_percentage, bonus_amount, total_compensation)
                VALUES (:employeeId, :reviewYear, :totalKpiScore, :category, :bonusPercentage, :bonusAmount, :totalCompensation)
                """;
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(sql, params(bonusRecord), keyHolder, new String[]{"bonus_id"});
        return new BonusRecord(keyHolder.getKey().longValue(), bonusRecord.employeeId(), bonusRecord.reviewYear(),
                bonusRecord.totalKpiScore(), bonusRecord.category(), bonusRecord.bonusPercentage(),
                bonusRecord.bonusAmount(), bonusRecord.totalCompensation(), bonusRecord.createdAt());
    }

    @Override
    public List<BonusRecord> findByEmployeeId(Long employeeId) {
        return jdbcTemplate.query("""
                SELECT * FROM bonus_records
                WHERE employee_id = :employeeId
                ORDER BY review_year DESC
                """, new MapSqlParameterSource("employeeId", employeeId), ROW_MAPPER);
    }

    @Override
    public List<BonusRecord> findAll() {
        return jdbcTemplate.query("SELECT * FROM bonus_records ORDER BY review_year DESC, employee_id", ROW_MAPPER);
    }

    private MapSqlParameterSource params(BonusRecord bonusRecord) {
        return new MapSqlParameterSource()
                .addValue("employeeId", bonusRecord.employeeId())
                .addValue("reviewYear", bonusRecord.reviewYear())
                .addValue("totalKpiScore", bonusRecord.totalKpiScore())
                .addValue("category", bonusRecord.category().name())
                .addValue("bonusPercentage", bonusRecord.bonusPercentage())
                .addValue("bonusAmount", bonusRecord.bonusAmount())
                .addValue("totalCompensation", bonusRecord.totalCompensation());
    }
}
