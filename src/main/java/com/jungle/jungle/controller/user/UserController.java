package com.jungle.jungle.controller.user;

import com.jungle.jungle.dto.LoginRequestDto;
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

@Controller
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
    public ResponseEntity<Object> signup(@Valid @RequestBody SignupRequestDto signupRequestDto) {
        Optional<User> user = userService.signup(signupRequestDto);
        if (user.isPresent()) {
            return ResponseEntity.ok("Signup successful");
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Username already exists");
        }
    }

    @PostMapping("/login")
    public ResponseEntity<String> login(@Valid @RequestBody LoginRequestDto loginRequestDto, HttpServletResponse response) {
        Optional<User> user = userService.login(loginRequestDto, response);
        if (user.isPresent()) {
            return ResponseEntity.ok("Login successful");
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid username or password");
        }
    }
}
