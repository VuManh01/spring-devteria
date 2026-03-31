package com.example.devteria.controller;

import com.example.devteria.dtos.request.AuthenticationRequest;
import com.example.devteria.dtos.response.ApiResponse;
import com.example.devteria.dtos.response.AuthenticationResponse;
import com.example.devteria.service.AuthenticationService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestClient;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthenticationController {

    private final AuthenticationService authenticationService;
    private final RestClient.Builder builder;

    @PostMapping("/log-in")
    public ApiResponse<AuthenticationResponse> authenticate(
            @RequestBody AuthenticationRequest request
    ){

       boolean check = authenticationService.authenticated(request);

       AuthenticationResponse authenticationResponse = AuthenticationResponse.builder()
                .authenticated(check)
                .build();

       ApiResponse<AuthenticationResponse> apiResponse = new ApiResponse<>();
       apiResponse.setResult(authenticationResponse);

       return apiResponse;
    }

}
