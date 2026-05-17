package com.globalsync.eliteperformance.enums;

public enum EmployeeCategory {
    GOLD(20),
    SILVER(12),
    BRONZE(5),
    NO_TIER(0);

    private final int bonusPercentage;

    EmployeeCategory(int bonusPercentage) {
        this.bonusPercentage = bonusPercentage;
    }

    public int getBonusPercentage() {
        return bonusPercentage;
    }
}
