package com.globalsync.eliteperformance.repository.jdbc;

import com.globalsync.eliteperformance.enums.Role;
import com.globalsync.eliteperformance.model.Employee;
import com.globalsync.eliteperformance.repository.EmployeeRepository;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class EmployeeJdbcRepository implements EmployeeRepository {

    private static final RowMapper<Employee> ROW_MAPPER = (rs, rowNum) -> new Employee(
            rs.getLong("employee_id"),
            rs.getString("name"),
            rs.getString("designation"),
            rs.getBigDecimal("base_salary"),
            Role.valueOf(rs.getString("role")),
            rs.getDate("last_promotion_date").toLocalDate()
    );

    private final NamedParameterJdbcTemplate jdbcTemplate;

    public EmployeeJdbcRepository(NamedParameterJdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public List<Employee> findAll() {
        return jdbcTemplate.query("SELECT * FROM employees ORDER BY employee_id", ROW_MAPPER);
    }

    @Override
    public Optional<Employee> findById(Long employeeId) {
        String sql = "SELECT * FROM employees WHERE employee_id = :employeeId";
        List<Employee> employees = jdbcTemplate.query(sql,
                new MapSqlParameterSource("employeeId", employeeId), ROW_MAPPER);
        return employees.stream().findFirst();
    }

    @Override
    public boolean existsById(Long employeeId) {
        Integer count = jdbcTemplate.queryForObject(
                "SELECT COUNT(*) FROM employees WHERE employee_id = :employeeId",
                new MapSqlParameterSource("employeeId", employeeId), Integer.class);
        return count != null && count > 0;
    }
}
