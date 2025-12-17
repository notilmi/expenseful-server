package org.ilmi.expensefulserver.input.web.data.input;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class LoginDTO {
    private String email;
    private String password;
}
