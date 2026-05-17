package com.globalsync.eliteperformance.controller;

import com.globalsync.eliteperformance.config.AuthenticatedUser;
import com.globalsync.eliteperformance.model.BonusRecord;
import com.globalsync.eliteperformance.service.BonusService;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/bonuses")
public class BonusController {

    private final BonusService bonusService;

    public BonusController(BonusService bonusService) {
        this.bonusService = bonusService;
    }

    @GetMapping("/my")
    public List<BonusRecord> findMine(@AuthenticationPrincipal AuthenticatedUser user) {
        return bonusService.findMine(user.employeeId());
    }

    @GetMapping("/all")
    public List<BonusRecord> findAll() {
        return bonusService.findAll();
    }
}
