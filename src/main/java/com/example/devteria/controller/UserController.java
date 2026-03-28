package com.example.devteria.controller;

import com.example.devteria.dtos.request.UserCreationRequest;
import com.example.devteria.dtos.request.UserUpdateRequest;
import com.example.devteria.dtos.response.ApiResponse;
import com.example.devteria.dtos.response.UserResponse;
import com.example.devteria.model.User;
import com.example.devteria.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/users")
public class UserController {

    private final UserService userService;

    @PostMapping("")
    public ApiResponse<User> createUser(  //class ApiResponse <T> => cho nên bên này phải trả về ApiResponse và với class là User => khi này T = User
            @RequestBody @Valid UserCreationRequest request) {
        ApiResponse<User> apiResponse = new ApiResponse<>();

        apiResponse.setCode(1000);  // bên ApiResponse ta gán code 1000 sẵn nên có thể bỏ qua dòng này, tôi để đây để có cái nhìn trực quan hơn
        apiResponse.setMessage("User created successfully");
        apiResponse.setResult(userService.createUser(request));

        userService.createUser(request);

        return apiResponse;
    }

    @GetMapping("")
    public ResponseEntity<List<User>> getAllUsers() {
       List<User> userList = userService.getAllUsers();
        return ResponseEntity.ok(userList);
    }

    @GetMapping("/{userId}")
    public UserResponse getUserDetail(
            @PathVariable String userId) {
            return userService.getUserDetail(userId);

    }

    @PutMapping("/{userId}")
    public User updateUser(
            @PathVariable String userId,
            @RequestBody UserUpdateRequest request) {

            return userService.updateUser(userId, request);

    }

    @DeleteMapping("/{userId}")
    public ResponseEntity<String>  deleteUser(
            @PathVariable String userId) {

            userService.deleteUser(userId);
            return ResponseEntity.ok("User deleted successfully");

    }
}
