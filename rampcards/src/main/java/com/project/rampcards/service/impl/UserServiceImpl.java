package com.project.rampcards.service.impl;

import com.project.rampcards.dto.UserDto;
import com.project.rampcards.entity.User;
import com.project.rampcards.exceptionhandler.UserNotFoundException;
import com.project.rampcards.repository.UserRepository;
import com.project.rampcards.service.UserService;
import org.modelmapper.ModelMapper;
import org.modelmapper.internal.bytebuddy.implementation.bytecode.Throw;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {

    private UserRepository userRepository;
    private ModelMapper modelMapper;

    public UserServiceImpl(UserRepository userRepository, ModelMapper modelMapper) {
        this.userRepository=userRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public List<UserDto> getUser( ) {
        List<User> userList = userRepository.findAll();
        return userList.stream().map(user -> modelMapper.map(user, UserDto.class)).collect(Collectors.toList());
    }
    @Override
    public UserDto getUserById(int id) throws UserNotFoundException {
        if(userRepository.findById(id).isPresent())
        {
            return modelMapper.map(userRepository.findById(id).get(), UserDto.class);
        }
        throw new UserNotFoundException("User with ID " + id + " not found");
    }
    @Override
    public User saveUser(UserDto userDto)
    {
        return userRepository.save(this.modelMapper.map(userDto, User.class));
    }
    @Override
    public String deleteUser(int id) throws UserNotFoundException
    {
        if(userRepository.findById(id).isPresent())
        {
            UserDto userDto = modelMapper.map(userRepository.findById(id).get(), UserDto.class);
            userRepository.deleteById(userDto.getUserId());
            return "Success";
        }
        throw new UserNotFoundException("User with ID " + id + " not found");
    }

}
