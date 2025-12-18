package org.ilmi.expensefulserver.input.web.data.output.mapper;

import org.ilmi.expensefulserver.domain.Statement;
import org.ilmi.expensefulserver.input.web.data.output.StatementDTO;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
public class StatementDTOMapper {
    private final ModelMapper modelMapper;

    public StatementDTOMapper(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    public StatementDTO toDTO(Statement statement) {
        return modelMapper.map(statement, StatementDTO.class);
    }
}
