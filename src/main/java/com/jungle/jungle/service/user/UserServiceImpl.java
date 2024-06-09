package com.jungle.jungle.service.user;

import com.jungle.jungle.dto.LoginRequestDto;
import com.jungle.jungle.dto.SignupRequestDto;
import com.jungle.jungle.entity.user.User;
import com.jungle.jungle.entity.user.UserRoleEnum;
import com.jungle.jungle.jwt.JwtUtil;
import com.jungle.jungle.repository.user.UserRepository;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.security.crypto.password.PasswordEncoder;


import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private static final String ADMIN_TOKEN = "AAABnvxRVklrnYxKZ0aHgTBcXukeZygoC";
    private final JwtUtil jwtUtil;
    private final PasswordEncoder passwordEncoder;

    @Transactional
    public Optional<User> signup(SignupRequestDto signupRequestDto) {
        String username = signupRequestDto.getUsername();
        String password = signupRequestDto.getPassword();
        String email = signupRequestDto.getEmail();

        Optional<User> found = userRepository.findByUsername(username);
        if (found.isPresent()) {
            throw new IllegalArgumentException("중복된 사용자가 존재합니다.");
        }

        UserRoleEnum role = UserRoleEnum.USER;
        if (signupRequestDto.isAdmin()) {
            if(!signupRequestDto.getAdminToken().equals(ADMIN_TOKEN)) {
                throw new IllegalArgumentException("관리자 암호가 틀려 등록이 불가합니다.");
            }
            role = UserRoleEnum.ADMIN;
        }
        password = passwordEncoder.encode(signupRequestDto.getPassword());
        User user = new User(username, password, email, role);
        userRepository.save(user);
        return Optional.of(user);
    }

    @Transactional(readOnly = true)
    public Optional<User> login(LoginRequestDto loginRequestDto, HttpServletResponse response) {
        Optional<User> user = userRepository.findByUsername(loginRequestDto.getUsername());
        if (user.isPresent() && passwordEncoder.matches(loginRequestDto.getPassword(), user.get().getPassword())) {
            return user;
        } else {
            return Optional.empty();
        }
    }
}
