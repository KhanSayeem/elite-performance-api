package com.globalsync.eliteperformance.repository.jdbc;

import com.globalsync.eliteperformance.model.PerformanceReview;
import com.globalsync.eliteperformance.repository.PerformanceReviewRepository;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

@Repository
public class PerformanceReviewJdbcRepository implements PerformanceReviewRepository {

    private final NamedParameterJdbcTemplate jdbcTemplate;

    public PerformanceReviewJdbcRepository(NamedParameterJdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public boolean existsByEmployeeIdAndReviewYear(Long employeeId, int reviewYear) {
        Integer count = jdbcTemplate.queryForObject("""
                SELECT COUNT(*) FROM performance_reviews
                WHERE employee_id = :employeeId AND review_year = :reviewYear
                """, new MapSqlParameterSource()
                .addValue("employeeId", employeeId)
                .addValue("reviewYear", reviewYear), Integer.class);
        return count != null && count > 0;
    }

    @Override
    public PerformanceReview save(PerformanceReview review) {
        String sql = """
                INSERT INTO performance_reviews
                (employee_id, review_year, task_completion, attendance, team_collaboration,
                 problem_solving, communication, leadership, client_satisfaction, total_kpi_score, category)
                VALUES
                (:employeeId, :reviewYear, :taskCompletion, :attendance, :teamCollaboration,
                 :problemSolving, :communication, :leadership, :clientSatisfaction, :totalKpiScore, :category)
                """;
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(sql, new MapSqlParameterSource()
                .addValue("employeeId", review.employeeId())
                .addValue("reviewYear", review.reviewYear())
                .addValue("taskCompletion", review.taskCompletion())
                .addValue("attendance", review.attendance())
                .addValue("teamCollaboration", review.teamCollaboration())
                .addValue("problemSolving", review.problemSolving())
                .addValue("communication", review.communication())
                .addValue("leadership", review.leadership())
                .addValue("clientSatisfaction", review.clientSatisfaction())
                .addValue("totalKpiScore", review.totalKpiScore())
                .addValue("category", review.category().name()), keyHolder, new String[]{"review_id"});
        return new PerformanceReview(keyHolder.getKey().longValue(), review.employeeId(), review.reviewYear(),
                review.taskCompletion(), review.attendance(), review.teamCollaboration(), review.problemSolving(),
                review.communication(), review.leadership(), review.clientSatisfaction(), review.totalKpiScore(),
                review.category(), review.createdAt());
    }
}
