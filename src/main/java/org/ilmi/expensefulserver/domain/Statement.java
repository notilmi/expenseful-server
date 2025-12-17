package org.ilmi.expensefulserver.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Statement {
    private String id;
    private String title;
    private String category;
    private Double amount;
    private String date;
    private StatementType type;
    private String ownerId; // User ID of the statement owner
}
