package org.ilmi.expensefulserver.output.persistence.mapper;

import org.ilmi.expensefulserver.domain.Statement;
import org.ilmi.expensefulserver.output.persistence.entity.StatementEntity;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
public class StatementMapper {
    private final ModelMapper modelMapper;

    public StatementMapper(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    public StatementEntity toEntity(Statement domain) {
        return modelMapper.map(domain, StatementEntity.class);
    }

    public Statement toDomain(StatementEntity entity) {
        return modelMapper.map(entity, Statement.class);
    }
}
