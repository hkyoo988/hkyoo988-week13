package com.jungle.jungle.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class EnvelopeResponseDto<T> {
    private String status;
    private String message;
    private T data;

}
