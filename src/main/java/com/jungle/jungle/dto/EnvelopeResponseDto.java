package com.jungle.jungle.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class EnvelopeResponseDto<T> {
    private T data;
    private String status;
    private String message;
}
