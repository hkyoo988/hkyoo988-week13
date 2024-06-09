package com.jungle.jungle.controller.user;

import com.jungle.jungle.dto.EnvelopeResponseDto;
import com.jungle.jungle.dto.LoginRequestDto;
import com.jungle.jungle.dto.LoginResponseDto;
import com.jungle.jungle.dto.SignupRequestDto;
import com.jungle.jungle.entity.user.User;
import com.jungle.jungle.jwt.JwtUtil;
import com.jungle.jungle.service.user.UserService;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.Optional;


@RestController
@RequiredArgsConstructor
@RequestMapping("/api/user")
public class UserController {

    private final UserService userService;
    private final JwtUtil jwtUtil;

    @GetMapping("/signup")
    public ModelAndView signupPage() {
        return new ModelAndView("signup");
    }

    @GetMapping("/login")
    public ModelAndView loginPage() {
        return new ModelAndView("login");
    }

    @PostMapping("/signup")
    public ResponseEntity<EnvelopeResponseDto<Object>> signup(@Valid @RequestBody SignupRequestDto signupRequestDto) {
        Optional<User> user = userService.signup(signupRequestDto);
        if (user.isPresent()) {
            EnvelopeResponseDto<Object> response = new EnvelopeResponseDto<>(null, "success", "Signup successful");
            return ResponseEntity.ok(response);
        } else {
            EnvelopeResponseDto<Object> response = new EnvelopeResponseDto<>(null, "error", "Username already exists");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }
    }

    @PostMapping("/login")
    public ResponseEntity<EnvelopeResponseDto<LoginResponseDto>> login(@Valid @RequestBody LoginRequestDto loginRequestDto, HttpServletResponse response) {
        Optional<User> user = userService.login(loginRequestDto, response);
        if (user.isPresent()) {
            User loggedUser = user.get();
            String token = jwtUtil.createToken(loggedUser.getUsername(), loggedUser.getRole());
            response.addHeader(JwtUtil.AUTHORIZATION_HEADER, token);
            LoginResponseDto loginResponseDto = LoginResponseDto.of(loggedUser, token);
            EnvelopeResponseDto<LoginResponseDto> responseDto = new EnvelopeResponseDto<>(loginResponseDto, "success", "Login successful");
            return ResponseEntity.ok(responseDto);
        } else {
            EnvelopeResponseDto<LoginResponseDto> responseDto = new EnvelopeResponseDto<>(null, "error", "Invalid credentials");
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(responseDto);
        }
    }
}
