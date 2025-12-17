package org.ilmi.expensefulserver.output.persistence.adapter.impl;

import org.ilmi.expensefulserver.domain.Statement;
import org.ilmi.expensefulserver.output.persistence.adapter.StatementPersistenceAdapter;
import org.ilmi.expensefulserver.output.persistence.entity.StatementEntity;
import org.ilmi.expensefulserver.output.persistence.mapper.StatementMapper;
import org.ilmi.expensefulserver.output.persistence.repository.StatementRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class StatementPersistenceAdapterImpl implements StatementPersistenceAdapter {
    private final StatementRepository statementRepository;
    private final StatementMapper statementMapper;

    public StatementPersistenceAdapterImpl(StatementRepository statementRepository, StatementMapper statementMapper) {
        this.statementRepository = statementRepository;
        this.statementMapper = statementMapper;
    }

    @Override
    public Optional<Statement> findByIdAndOwnerId(String id, String ownerId) {
        return statementRepository.findByIdAndOwnerId(id, ownerId)
                .map(statementMapper::toDomain);
    }

    @Override
    public boolean existsByIdAndOwnerId(String id, String ownerId) {
        return statementRepository.existsByIdAndOwnerId(id, ownerId);
    }

    @Override
    public List<Statement> findByOwnerId(String ownerId) {

        return statementRepository.findByOwnerId(ownerId)
                .stream()
                .map(statementMapper::toDomain)
                .toList();
    }

    @Override
    public Statement save(Statement statement) {

        return statementMapper.toDomain(
                statementRepository.save(
                        statementMapper.toEntity(statement)
                )
        );
    }

    @Override
    public void delete(Statement statement) {
        StatementEntity statementEntity = statementMapper.toEntity(statement);

        statementRepository.delete(statementEntity);
    }
}
