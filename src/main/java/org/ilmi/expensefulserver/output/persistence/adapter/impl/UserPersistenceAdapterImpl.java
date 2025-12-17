package org.ilmi.expensefulserver.output.persistence.adapter.impl;

import org.ilmi.expensefulserver.domain.User;
import org.ilmi.expensefulserver.output.persistence.adapter.UserPersistenceAdapter;
import org.ilmi.expensefulserver.output.persistence.mapper.UserMapper;
import org.ilmi.expensefulserver.output.persistence.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserPersistenceAdapterImpl implements UserPersistenceAdapter {
    private final UserRepository userRepository;
    private final UserMapper userMapper;

    public UserPersistenceAdapterImpl(UserRepository userRepository, UserMapper userMapper) {
        this.userRepository = userRepository;
        this.userMapper = userMapper;
    }

    @Override
    public Optional<User> findByEmail(String email) {
        return userRepository.findByEmail(email)
                .map(userMapper::toDomain);
    }

    @Override
    public Optional<User> findById(String id) {
        return userRepository.findById(id)
                .map(userMapper::toDomain);
    }

    @Override
    public User save(User user) {
        return userMapper.toDomain(
                userRepository.save(
                        userMapper.toEntity(user)
                )
        );
    }

    @Override
    public void delete(User user) {
        userRepository.delete(
                userMapper.toEntity(user)
        );
    }

    @Override
    public boolean existsByEmail(String email) {
        return userRepository.existsByEmail(email);
    }
}
