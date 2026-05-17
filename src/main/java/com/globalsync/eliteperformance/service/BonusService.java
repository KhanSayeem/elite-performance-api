package com.globalsync.eliteperformance.service;

import com.globalsync.eliteperformance.model.BonusRecord;
import com.globalsync.eliteperformance.repository.BonusRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BonusService {

    private final BonusRepository bonusRepository;

    public BonusService(BonusRepository bonusRepository) {
        this.bonusRepository = bonusRepository;
    }

    public List<BonusRecord> findMine(Long employeeId) {
        return bonusRepository.findByEmployeeId(employeeId);
    }

    public List<BonusRecord> findAll() {
        return bonusRepository.findAll();
    }
}
