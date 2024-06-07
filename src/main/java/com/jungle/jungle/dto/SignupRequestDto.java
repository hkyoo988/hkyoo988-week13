package com.jungle.jungle.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SignupRequestDto {

    @Size(min = 4, max = 10, message = "Username must be between 4 and 10 characters")
    @Pattern(regexp = "^[a-z0-9]*$", message = "Username must contain only lowercase letters and numbers")
    private String username;

    @Size(min = 8, max = 15, message = "Password must be between 8 and 15 characters")
    @Pattern(regexp = "^[a-zA-Z0-9]*$", message = "Password must contain only letters and numbers")
    private String password;

    @Email
    private String email;
    private boolean admin = false;
    private String adminToken = "";
}
