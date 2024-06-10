package com.jungle.jungle.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@RequiredArgsConstructor
@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ErrorResponseDto {
    private final int status;
    private final String message;
    private final LocalDateTime time;
    private List<ValidationError> validError;

    @Data
    @RequiredArgsConstructor
    private static class ValidationError {
        private final String field;
        private final String message;
    }

    public void addValidationError(String field, String message) {
        if(Objects.isNull(validError)) {
            validError = new ArrayList<>();
        }
        validError.add(new ValidationError(field, message));
    }
}
