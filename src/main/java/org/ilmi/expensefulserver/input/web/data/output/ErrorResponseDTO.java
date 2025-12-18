package org.ilmi.expensefulserver.input.web.data.output;

import lombok.Builder;
import lombok.Data;
import org.jspecify.annotations.NullMarked;
import org.jspecify.annotations.Nullable;

@Data
@Builder
@NullMarked
public class ErrorResponseDTO<T> {
    private String code;
    private String message;
    @Nullable
    private T data;
}
