package org.ilmi.expensefulserver.input.web.data.input;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.ilmi.expensefulserver.domain.StatementType;
import org.ilmi.expensefulserver.validator.ValidEnum;

import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UpdateStatementDTO {
   @NotBlank(message = "Title is required")
    private String title;

    private String category;

    @NotNull(message = "Amount is required")
    @Min(value = 0, message = "Amount must be non-negative")
    private Double amount;

    @NotNull(message = "Date is required")
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate date;

    @NotNull(message = "Statement type is required")
    @ValidEnum(enumClass = StatementType.class, message = "Invalid statement type")
    private String type;

}
