package com.example.devteria.service;


import com.example.devteria.dtos.request.UserCreationRequest;
import com.example.devteria.dtos.request.UserUpdateRequest;

import com.example.devteria.exception.AppException;
import com.example.devteria.exception.ErrorCode;
import com.example.devteria.model.User;
import com.example.devteria.repository.UserRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepo userRepository;

    public User createUser(UserCreationRequest request) {
        User user = new User();

        if(userRepository.existsByUsername(request.getUsername())){
            throw new AppException(ErrorCode.USER_EXISTED);
        }

        user.setUsername(request.getUsername());
        user.setPassword(request.getPassword());
        user.setFirstName(request.getFirstName());
        user.setLastName(request.getLastName());
        user.setDateOfBirth(request.getDateOfBirth());

        //gọi userRepo=> để tạo row mới trong table gọi đến hàm save()
        User newUser = userRepository.save(user);
        return newUser;
    }

    public List<User> getAllUsers(){
       return userRepository.findAll();
    }

    public User getUserDetail(String userId){
        return existingUser(userId);
    }

    public User updateUser(String userId, UserUpdateRequest request){
        User existingUser = existingUser(userId);

        existingUser.setPassword(request.getPassword());
        existingUser.setFirstName(request.getFirstName());
        existingUser.setLastName(request.getLastName());
        existingUser.setDateOfBirth(request.getDateOfBirth());
        userRepository.save(existingUser);

        return existingUser;
    }

    public User existingUser(String userId){
        return userRepository.findById(userId).orElseThrow(
                () -> new RuntimeException("User not found with id" )
        );
    }

    public void deleteUser(String userId){
        User existingUser = existingUser(userId);
        userRepository.delete(existingUser);
    }
}
