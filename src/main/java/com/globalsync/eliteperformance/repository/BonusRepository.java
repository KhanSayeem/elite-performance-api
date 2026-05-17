package com.globalsync.eliteperformance.repository;

import com.globalsync.eliteperformance.model.BonusRecord;

import java.util.List;

public interface BonusRepository {
    BonusRecord save(BonusRecord bonusRecord);

    List<BonusRecord> findByEmployeeId(Long employeeId);

    List<BonusRecord> findAll();
}
