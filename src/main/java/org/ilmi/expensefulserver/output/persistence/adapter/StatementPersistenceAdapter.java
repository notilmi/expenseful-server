package org.ilmi.expensefulserver.output.persistence.adapter;

import org.ilmi.expensefulserver.domain.Statement;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public interface StatementPersistenceAdapter {
    Optional<Statement> findByIdAndOwnerId(String id, String ownerId);
    boolean existsByIdAndOwnerId(String id, String ownerId);
    List<Statement> findByOwnerId(String ownerId);
    Statement save(Statement statement);
    void delete(Statement statement);
}
