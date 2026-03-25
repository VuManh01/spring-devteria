package com.example.devteria.dtos.request;

import jakarta.validation.constraints.Size;
import lombok.Data;

import java.time.LocalDate;


@Data
public class UserCreationRequest {
    @Size(min = 3, message = "Username must be at least 3 characters long")
    private String username;

    @Size(min = 8, message = "Password must be at least 8 characters long")
    private String password;
    private String firstName;
    private String lastName;
    private LocalDate dateOfBirth;
}
