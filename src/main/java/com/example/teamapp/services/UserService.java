package com.example.teamapp.services;

import com.example.teamapp.dto.UserDto;
import com.example.teamapp.models.Auth.UserEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import com.example.teamapp.repositories.UserRepo;

import java.util.List;

@Service("userService")
public class UserService {

    private final UserRepo userRepo;
    final PasswordEncoder passwordEncoder;

    public UserService(UserRepo userRepo, PasswordEncoder passwordEncoder) {
        this.userRepo = userRepo;
        this.passwordEncoder = passwordEncoder;
    }

    public void saveUser(final UserDto userDto){
        UserEntity customerModel = addUser(userDto);
        userRepo.save(customerModel);
    }

    public boolean isUserExist(final UserDto user){
        UserEntity userFromDb = userRepo.findByUsername(user.getUsername());
        if(userFromDb != null)
            return passwordEncoder.matches(user.getPassword(), userFromDb.getPassword());
        return false;
    }

    public List<UserEntity> findUsers(final String username){
        return userRepo.findUsersByUsername(username);
    }

    private UserEntity addUser(final UserDto userDto){
        UserEntity user = new UserEntity();
        user.setUsername(userDto.getUsername());
        user.setPassword(passwordEncoder.encode(userDto.getPassword()));
        return user;
    }
}