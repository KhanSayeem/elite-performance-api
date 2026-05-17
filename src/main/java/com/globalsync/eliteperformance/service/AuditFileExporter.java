package com.globalsync.eliteperformance.service;

import com.globalsync.eliteperformance.model.AuditLog;
import org.springframework.stereotype.Component;

import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Collection;

@Component
public class AuditFileExporter {

    public void exportCsv(Collection<AuditLog> logs, Path path) throws IOException {
        try (BufferedWriter writer = Files.newBufferedWriter(path)) {
            writer.write("logId,employeeId,action,performedBy,timestamp");
            writer.newLine();
            for (AuditLog log : logs) {
                writer.write("%d,%d,%s,%s,%s".formatted(log.logId(), log.employeeId(), log.action(),
                        log.performedBy(), log.timestamp()));
                writer.newLine();
            }
        }
    }
}
