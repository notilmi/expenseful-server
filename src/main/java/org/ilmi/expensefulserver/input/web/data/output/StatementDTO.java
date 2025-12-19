package org.ilmi.expensefulserver.input.web.data.output;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.ilmi.expensefulserver.domain.StatementType;

import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class StatementDTO {
    private String id;
    private String title;
    private String category;
    private Double amount;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private LocalDate date;
    private StatementType type;
    private String ownerId; // User ID of the statement owner
}
