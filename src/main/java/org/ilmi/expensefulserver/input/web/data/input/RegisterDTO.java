package org.ilmi.expensefulserver.input.web.data.input;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class RegisterDTO {
    private String email;
    private String name;
    private String password;
}
