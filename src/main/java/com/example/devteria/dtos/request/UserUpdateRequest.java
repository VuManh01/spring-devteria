package com.example.devteria.dtos.request;

import lombok.Data;

import java.time.LocalDate;

@Data
public class UserUpdateRequest {

    private String password;
    private String firstName;
    private String lastName;
    private LocalDate dateOfBirth;
}
