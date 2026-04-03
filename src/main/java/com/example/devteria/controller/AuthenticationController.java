package com.example.devteria.controller;

import com.example.devteria.dtos.request.AuthenticationRequest;
import com.example.devteria.dtos.request.IntrospectRequest;
import com.example.devteria.dtos.response.ApiResponse;
import com.example.devteria.dtos.response.AuthenticationResponse;
import com.example.devteria.dtos.response.IntrospectResponse;
import com.example.devteria.model.User;
import com.example.devteria.service.AuthenticationService;
import com.nimbusds.jose.JOSEException;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestClient;

import java.text.ParseException;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthenticationController {

    private final AuthenticationService authenticationService;

    @PostMapping("/token")
    public ApiResponse<AuthenticationResponse> authenticate(
            @RequestBody AuthenticationRequest request
    ){

       AuthenticationResponse check = authenticationService.authenticated(request);
//       AuthenticationResponse authenticationResponse = AuthenticationResponse.builder()
//                .authenticated(check)
//                .build();

       ApiResponse<AuthenticationResponse> apiResponse = new ApiResponse<>();
       apiResponse.setResult(check);

       return apiResponse;
    }

    @PostMapping("/introspect")
    public ApiResponse<IntrospectResponse> authenticate(
            @RequestBody IntrospectRequest request) throws ParseException, JOSEException {


        IntrospectResponse introspectResponse = authenticationService.introspect(request);

        ApiResponse<IntrospectResponse> apiResponse = new ApiResponse<>();
        apiResponse.setResult(introspectResponse);
        return apiResponse;
    }


}
