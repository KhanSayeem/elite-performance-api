package com.globalsync.eliteperformance.repository.jdbc;

import com.globalsync.eliteperformance.repository.AuditLogRepository;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class AuditLogJdbcRepository implements AuditLogRepository {

    private final NamedParameterJdbcTemplate jdbcTemplate;

    public AuditLogJdbcRepository(NamedParameterJdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public void save(Long employeeId, String action, String performedBy) {
        jdbcTemplate.update("""
                INSERT INTO audit_logs (employee_id, action, performed_by)
                VALUES (:employeeId, :action, :performedBy)
                """, new MapSqlParameterSource()
                .addValue("employeeId", employeeId)
                .addValue("action", action)
                .addValue("performedBy", performedBy));
    }
}
