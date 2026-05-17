package com.globalsync.eliteperformance.repository.jdbc;

import com.globalsync.eliteperformance.enums.Role;
import com.globalsync.eliteperformance.model.User;
import com.globalsync.eliteperformance.repository.UserRepository;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class UserJdbcRepository implements UserRepository {

    private static final RowMapper<User> ROW_MAPPER = (rs, rowNum) -> new User(
            rs.getLong("user_id"),
            rs.getLong("employee_id"),
            rs.getString("username"),
            rs.getString("password"),
            Role.valueOf(rs.getString("role"))
    );

    private final NamedParameterJdbcTemplate jdbcTemplate;

    public UserJdbcRepository(NamedParameterJdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public Optional<User> findByUsername(String username) {
        String sql = "SELECT * FROM users WHERE username = :username";
        List<User> users = jdbcTemplate.query(sql, new MapSqlParameterSource("username", username), ROW_MAPPER);
        return users.stream().findFirst();
    }

    @Override
    public boolean existsByUsername(String username) {
        Integer count = jdbcTemplate.queryForObject("SELECT COUNT(*) FROM users WHERE username = :username",
                new MapSqlParameterSource("username", username), Integer.class);
        return count != null && count > 0;
    }

    @Override
    public User save(User user) {
        String sql = """
                INSERT INTO users (employee_id, username, password, role)
                VALUES (:employeeId, :username, :password, :role)
                """;
        KeyHolder keyHolder = new GeneratedKeyHolder();
        MapSqlParameterSource params = new MapSqlParameterSource()
                .addValue("employeeId", user.employeeId())
                .addValue("username", user.username())
                .addValue("password", user.password())
                .addValue("role", user.role().name());
        jdbcTemplate.update(sql, params, keyHolder, new String[]{"user_id"});
        return new User(keyHolder.getKey().longValue(), user.employeeId(), user.username(), user.password(), user.role());
    }
}
