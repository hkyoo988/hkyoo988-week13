package com.jungle.jungle.dto;

import com.jungle.jungle.entity.user.User;
import com.jungle.jungle.entity.user.UserRoleEnum;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Builder
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class LoginResponseDto {
    @NotBlank
    private String username;
    @NotBlank
    private UserRoleEnum role;
    @NotBlank
    private String token;

    public static LoginResponseDto of(User user, String token) {
        return LoginResponseDto.builder()
                .username(user.getUsername())
                .role(user.getRole())
                .token(token)
                .build();
    }
}
