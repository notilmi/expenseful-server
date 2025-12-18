package org.ilmi.expensefulserver.service.impl;

import org.ilmi.expensefulserver.domain.Statement;
import org.ilmi.expensefulserver.domain.StatementType;
import org.ilmi.expensefulserver.exception.StatementNotFoundException;
import org.ilmi.expensefulserver.output.persistence.adapter.StatementPersistenceAdapter;
import org.ilmi.expensefulserver.service.StatementService;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class StatementServiceImpl implements StatementService {
    private final StatementPersistenceAdapter statementPersistenceAdapter;

    public StatementServiceImpl(StatementPersistenceAdapter statementPersistenceAdapter) {
        this.statementPersistenceAdapter = statementPersistenceAdapter;
    }


    @Override
    public Statement createStatement(
            String title,
            String category,
            Double amount,
            LocalDate date,
            StatementType type,
            String ownerId
    ) {
        Statement statement = new Statement();
        statement.setTitle(title);
        statement.setCategory(category);
        statement.setAmount(amount);
        statement.setDate(date);
        statement.setType(type);
        statement.setOwnerId(ownerId);

        return statementPersistenceAdapter.save(statement);
    }

    @Override
    public Statement updateStatement(
            String statementId,
            String title,
            String category,
            Double amount,
            LocalDate date,
            String ownerId
    ) {
        Statement statement = statementPersistenceAdapter.findByIdAndOwnerId(statementId, ownerId)
                .orElseThrow(() -> new StatementNotFoundException("Statement not found with id: " + statementId));

        statement.setTitle(title);
        statement.setCategory(category);
        statement.setAmount(amount);
        statement.setDate(date);

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
