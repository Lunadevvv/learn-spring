package com.luna.identity_service.service;

import com.luna.identity_service.dto.request.UserCreationRequest;
import com.luna.identity_service.dto.request.UserUpdateRequest;
import com.luna.identity_service.dto.response.UserResponse;
import com.luna.identity_service.entity.User;
import com.luna.identity_service.exception.AppException;
import com.luna.identity_service.exception.ErrorCode;
import com.luna.identity_service.mapper.UserMapper;
import com.luna.identity_service.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private UserMapper userMapper;

    public UserResponse createUser(UserCreationRequest request){


        if(userRepository.existsByUsername(request.getUsername()))
            throw new RuntimeException("ErrorCode.USER_EXISTED");
//        User user = new User();
//        user.setUsername(request.getUsername());
//        user.setPassword(request.getPassword());
//        user.setFirstName(request.getFirstName());
//        user.setLastName(request.getLastName());
//        user.setDob(request.getDob());

        //Sau khi su dung Mapstruct de map du lieu dua User va UserCreationRequest
        User user = userMapper.toUser(request);
        //Mã hoá password bằng BCrypt
        //Strength là độ phức tạp của thuật toán mã hoá (số này càng cao thì càng ảnh hưởng đến performance)
        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder(10);
        user.setPassword(passwordEncoder.encode(request.getPassword()));


        return userMapper.toUserResponse(userRepository.save(user));
    }

    public List<User> getUsers(){
        return userRepository.findAll();
    }

    public UserResponse getUser(String id){
        //findById, neu ko co thi throw 1 exception
        return userMapper.toUserResponse(userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found!")));
    }

    public UserResponse updateUser(String id, UserUpdateRequest request){
        User user = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found!"));

//        user.setPassword(request.getPassword());
//        user.setFirstName(request.getFirstName());
//        user.setLastName(request.getLastName());
//        user.setDob(request.getDob());

        userMapper.updateUser(user, request);

        return userMapper.toUserResponse(userRepository.save(user));
    }

    public void deleteUser(String id){
        userRepository.deleteById(id);
    }
}
