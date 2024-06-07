package com.jungle.jungle.service.user;

import com.jungle.jungle.dto.LoginRequestDto;
import com.jungle.jungle.dto.SignupRequestDto;
import com.jungle.jungle.entity.user.User;
import jakarta.servlet.http.HttpServletResponse;

import java.util.Optional;

public interface UserService {
    public Optional<User> signup(SignupRequestDto signupRequestDto);

    public Optional<User> login(LoginRequestDto loginRequestDto, HttpServletResponse response);
}
