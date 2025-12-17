package org.ilmi.expensefulserver.output.persistence.mapper;

import org.ilmi.expensefulserver.domain.User;
import org.ilmi.expensefulserver.output.persistence.entity.UserEntity;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
public class UserMapper {
    private final ModelMapper modelMapper;

    public UserMapper(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    public UserEntity toEntity(User domain) {
        return modelMapper.map(domain, UserEntity.class);
    }

    public User toDomain(UserEntity entity) {
        return modelMapper.map(entity, User.class);
    }
}
