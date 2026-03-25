package com.example.devteria.controller;

import com.example.devteria.dtos.request.UserCreationRequest;
import com.example.devteria.dtos.request.UserUpdateRequest;
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
    public ResponseEntity<String> createUser(
            @RequestBody @Valid UserCreationRequest request) {
        userService.createUser(request);
        return ResponseEntity.ok("User created successfully ");
    }

    @GetMapping("")
    public ResponseEntity<List<User>> getAllUsers() {
       List<User> userList = userService.getAllUsers();
        return ResponseEntity.ok(userList);
    }

    @GetMapping("/{userId}")
    public User getUserDetail(
            @PathVariable String userId) {
        try{
            return userService.getUserDetail(userId);

        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    @PutMapping("/{userId}")
    public User updateUser(
            @PathVariable String userId,
            @RequestBody UserUpdateRequest request) {
        try{
            return userService.updateUser(userId, request);

        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    @DeleteMapping("/{userId}")
    public ResponseEntity<String>  deleteUser(
            @PathVariable String userId) {
        try{
            userService.deleteUser(userId);
            return ResponseEntity.ok("User deleted successfully");
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
    }
}
