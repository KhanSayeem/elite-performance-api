package com.globalsync.eliteperformance.controller;

import com.globalsync.eliteperformance.config.AuthenticatedUser;
import com.globalsync.eliteperformance.dto.PerformanceCalculationRequest;
import com.globalsync.eliteperformance.dto.PerformanceCalculationResponse;
import com.globalsync.eliteperformance.service.PerformanceService;
import jakarta.validation.Valid;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/performances")
public class PerformanceController {

    private final PerformanceService performanceService;

    public PerformanceController(PerformanceService performanceService) {
        this.performanceService = performanceService;
    }

    @PostMapping("/calculate")
    public PerformanceCalculationResponse calculate(@Valid @RequestBody PerformanceCalculationRequest request,
                                                    @AuthenticationPrincipal AuthenticatedUser user) {
        return performanceService.calculate(request, user.getUsername());
    }
}
