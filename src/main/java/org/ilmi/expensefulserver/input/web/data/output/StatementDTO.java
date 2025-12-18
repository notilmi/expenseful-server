package org.ilmi.expensefulserver.input.web.data.output;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.ilmi.expensefulserver.domain.StatementType;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class StatementDTO {
    private String id;
    private String title;
    private String category;
    private Double amount;
    private String date;
    private StatementType type;
    private String ownerId; // User ID of the statement owner
}
