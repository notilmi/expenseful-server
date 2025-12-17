package org.ilmi.expensefulserver.service;

import org.ilmi.expensefulserver.domain.Statement;

import java.util.List;

public interface StatementService {
    Statement createStatement(Statement statement);
    Statement updateStatement(String statementId, Statement statement, String ownerId);
    void deleteStatement(String statementId, String ownerId);

    Statement getStatementById(String statementId, String ownerId);
    List<Statement> getAllStatements(String ownerId);
}
