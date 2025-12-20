package org.ilmi.expensefulserver.input.web.data.input;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class EditProfileDTO {
    private String name;
    private String password;
}
