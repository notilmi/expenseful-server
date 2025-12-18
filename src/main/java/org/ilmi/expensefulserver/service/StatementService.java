package org.ilmi.expensefulserver.service;

import org.ilmi.expensefulserver.domain.Statement;
import org.ilmi.expensefulserver.domain.StatementType;

import java.time.LocalDate;
import java.util.List;

public interface StatementService {
    Statement createStatement(
            String title,
            String category,
            Double amount,
            LocalDate date,
            StatementType type,
            String ownerId
    );

    Statement updateStatement(
            String statementId,
            String title,
            String category,
            Double amount,
            LocalDate date,
            String ownerId
    );

    void deleteStatement(String statementId, String ownerId);

    Statement getStatementById(String statementId, String ownerId);

    List<Statement> getAllStatements(String ownerId);
}
