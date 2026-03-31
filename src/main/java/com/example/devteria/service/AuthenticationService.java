package com.example.devteria.service;

import com.example.devteria.dtos.request.AuthenticationRequest;
import com.example.devteria.exception.AppException;
import com.example.devteria.exception.ErrorCode;
import com.example.devteria.model.User;
import com.example.devteria.repository.UserRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AuthenticationService {

    private final UserRepo userRepository;

    public boolean authenticated(AuthenticationRequest request){
        User existingUser = userRepository.findByUsername(request.getUsername()).orElseThrow(
                () -> new AppException(ErrorCode.USER_EXISTED));

        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder(10);

        boolean check = passwordEncoder.matches(request.getPassword(), existingUser.getPassword());
        return check;
    }
}
