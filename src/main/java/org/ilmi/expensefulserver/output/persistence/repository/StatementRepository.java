package org.ilmi.expensefulserver.output.persistence.repository;

import org.ilmi.expensefulserver.output.persistence.entity.StatementEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface StatementRepository extends JpaRepository<StatementEntity, String> {
    Optional<StatementEntity> findByIdAndOwnerId(String id, String ownerId);
    List<StatementEntity> findByOwnerId(String ownerId);
    boolean existsByIdAndOwnerId(String id, String ownerId);
}
