package org.ilmi.expensefulserver.output.persistence.adapter;

import org.ilmi.expensefulserver.domain.User;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public interface UserPersistenceAdapter {
    Optional<User> findByEmail(String email);
    Optional<User> findById(String id);
    User save(User user);
    void delete(User user);
    boolean existsByEmail(String email);
}
