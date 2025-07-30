package com.luna.identity_service.controller;

import com.luna.identity_service.dto.request.ApiResponse;
import com.luna.identity_service.dto.request.UserCreationRequest;
import com.luna.identity_service.dto.request.UserUpdateRequest;
import com.luna.identity_service.dto.response.UserResponse;
import com.luna.identity_service.entity.User;
import com.luna.identity_service.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class UserController {
    @Autowired
    private UserService userService;

    @PostMapping("/users")
    ApiResponse<UserResponse> createUser(@RequestBody @Valid UserCreationRequest request){
        ApiResponse<UserResponse> apiResponse = new ApiResponse<>();

        apiResponse.setResult(userService.createUser(request));
        return apiResponse;
    }

    @GetMapping("/users")
    ApiResponse<List<User>> getUsers(){
        ApiResponse<List<User>> apiResponse = new ApiResponse<>();
        apiResponse.setResult(userService.getUsers());
        return apiResponse;
    }

    @GetMapping("/users/{userId}")
    UserResponse getUser(@PathVariable String userId){
        return userService.getUser(userId);
    }

    @PutMapping("/users/{userId}")
    UserResponse updateUser(@PathVariable String userId, @RequestBody UserUpdateRequest request){
        //Annotation RequestBody la lay thong tin tu request body xuong de xu li
        return userService.updateUser(userId, request);
    }

    @DeleteMapping("/users/{userId}")
    String deleteUser(@PathVariable String userId){
        userService.deleteUser(userId);
        return userId + "has been deleted!";
    }
}
