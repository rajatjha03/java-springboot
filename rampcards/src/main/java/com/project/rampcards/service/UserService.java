package com.project.rampcards.service;

import com.project.rampcards.dto.UserDto;
import com.project.rampcards.entity.User;
import com.project.rampcards.exceptionhandler.UserNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;


public interface UserService {
    public User saveUser(UserDto userDto);

    public String deleteUser(int id) throws UserNotFoundException;
    public List<UserDto> getUser( );

    public UserDto getUserById(int id) throws UserNotFoundException;
}
