package org.ilmi.expensefulserver.service.impl;

import org.ilmi.expensefulserver.domain.Statement;
import org.ilmi.expensefulserver.exception.InvalidOperationException;
import org.ilmi.expensefulserver.exception.StatementNotFoundException;
import org.ilmi.expensefulserver.output.persistence.adapter.StatementPersistenceAdapter;
import org.ilmi.expensefulserver.service.StatementService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StatementServiceImpl implements StatementService {
    private final StatementPersistenceAdapter statementPersistenceAdapter;

    public StatementServiceImpl(StatementPersistenceAdapter statementPersistenceAdapter) {
        this.statementPersistenceAdapter = statementPersistenceAdapter;
    }

    @Override
    public Statement createStatement(Statement statement) {
        return statementPersistenceAdapter.save(statement);
    }

    @Override
    public Statement updateStatement(String statementId, Statement statement, String ownerId) {
        boolean isStatementExists = statementPersistenceAdapter.existsByIdAndOwnerId(statementId, ownerId);

        if (!isStatementExists) {
            throw new StatementNotFoundException("Statement not found with id: " + statementId);
        }

        if (!statement.getId().equals(statementId)) {
            throw new InvalidOperationException("Statement ID mismatch");
        }

        return statementPersistenceAdapter.save(statement);
    }

    @Override
    public void deleteStatement(String statementId, String ownerId) {
        Statement existing = statementPersistenceAdapter.findByIdAndOwnerId(statementId, ownerId)
                .orElseThrow(() -> new StatementNotFoundException("Statement not found with id: " + statementId));

        statementPersistenceAdapter.delete(existing);
    }

    @Override
    public Statement getStatementById(String statementId, String ownerId) {
        return statementPersistenceAdapter.findByIdAndOwnerId(statementId, ownerId)
                .orElseThrow(() -> new StatementNotFoundException("Statement not found with id: " + statementId));
    }

    @Override
    public List<Statement> getAllStatements(String ownerId) {
        return statementPersistenceAdapter.findByOwnerId(ownerId);
    }
}
