package com.globalsync.eliteperformance.service;

import com.globalsync.eliteperformance.dto.PerformanceCalculationRequest;
import com.globalsync.eliteperformance.dto.PerformanceCalculationResponse;
import com.globalsync.eliteperformance.enums.EmployeeCategory;
import com.globalsync.eliteperformance.exception.DuplicateReviewException;
import com.globalsync.eliteperformance.exception.EmployeeNotFoundException;
import com.globalsync.eliteperformance.exception.InvalidKpiScoreException;
import com.globalsync.eliteperformance.model.BonusRecord;
import com.globalsync.eliteperformance.model.Employee;
import com.globalsync.eliteperformance.model.PerformanceReview;
import com.globalsync.eliteperformance.repository.AuditLogRepository;
import com.globalsync.eliteperformance.repository.BonusRepository;
import com.globalsync.eliteperformance.repository.EmployeeRepository;
import com.globalsync.eliteperformance.repository.PerformanceReviewRepository;
import com.globalsync.eliteperformance.util.BonusCalculator;
import com.globalsync.eliteperformance.util.KpiCalculator;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Service
public class PerformanceService {

    private final EmployeeRepository employeeRepository;
    private final PerformanceReviewRepository performanceReviewRepository;
    private final BonusRepository bonusRepository;
    private final AuditLogRepository auditLogRepository;
    private final KpiCalculator kpiCalculator;
    private final BonusCalculator bonusCalculator;

    public PerformanceService(EmployeeRepository employeeRepository,
                              PerformanceReviewRepository performanceReviewRepository,
                              BonusRepository bonusRepository,
                              AuditLogRepository auditLogRepository,
                              KpiCalculator kpiCalculator,
                              BonusCalculator bonusCalculator) {
        this.employeeRepository = employeeRepository;
        this.performanceReviewRepository = performanceReviewRepository;
        this.bonusRepository = bonusRepository;
        this.auditLogRepository = auditLogRepository;
        this.kpiCalculator = kpiCalculator;
        this.bonusCalculator = bonusCalculator;
    }

    @Transactional(rollbackFor = Exception.class)
    public PerformanceCalculationResponse calculate(PerformanceCalculationRequest request, String performedBy) {
        if (request.reviewYear() > LocalDate.now().getYear()) {
            throw new InvalidKpiScoreException("Review year cannot be in the future");
        }

        Employee employee = employeeRepository.findById(request.employeeId())
                .orElseThrow(() -> new EmployeeNotFoundException(request.employeeId()));

        if (performanceReviewRepository.existsByEmployeeIdAndReviewYear(request.employeeId(), request.reviewYear())) {
            throw new DuplicateReviewException(request.employeeId(), request.reviewYear());
        }

        int totalScore = kpiCalculator.calculateTotal(request.toScores());
        EmployeeCategory category = kpiCalculator.categoryFor(totalScore);
        BigDecimal bonusAmount = bonusCalculator.bonusAmount(employee.baseSalary(), category);
        BigDecimal totalCompensation = bonusCalculator.totalCompensation(employee.baseSalary(), category);

        PerformanceReview review = new PerformanceReview(null, request.employeeId(), request.reviewYear(),
                request.taskCompletion(), request.attendance(), request.teamCollaboration(), request.problemSolving(),
                request.communication(), request.leadership(), request.clientSatisfaction(), totalScore,
                category, LocalDateTime.now());
        performanceReviewRepository.save(review);

        BonusRecord bonusRecord = new BonusRecord(null, request.employeeId(), request.reviewYear(), totalScore,
                category, category.getBonusPercentage(), bonusAmount, totalCompensation, LocalDateTime.now());
        bonusRepository.save(bonusRecord);
        auditLogRepository.save(request.employeeId(), "CALCULATED_BONUS_" + request.reviewYear(), performedBy);

        return new PerformanceCalculationResponse(request.employeeId(), request.reviewYear(), totalScore, category,
                category.getBonusPercentage(), bonusAmount, totalCompensation);
    }
}
