package org.ilmi.expensefulserver.input.web.data.output.mapper;

import org.ilmi.expensefulserver.domain.User;
import org.ilmi.expensefulserver.input.web.data.output.UserDTO;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
public class UserDTOMapper {
    private final ModelMapper modelMapper;

    public UserDTOMapper(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    public UserDTO toDTO(User user) {
        return modelMapper.map(user, UserDTO.class);
    }
}
