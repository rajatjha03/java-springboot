package com.project.rampcards.service.impl;

import com.project.rampcards.dto.UserDto;
import com.project.rampcards.entity.User;
import com.project.rampcards.exceptionhandler.UserNotFoundException;
import com.project.rampcards.repository.UserRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UserServiceImplTest {

    @Mock
    private UserRepository userRepository;
    @Spy
    private ModelMapper modelMapper;
    private UserServiceImpl userServiceImpl;
    AutoCloseable autoCloseable;
    @Mock
    UserDto userDto;
    @Mock
    User user;
    @BeforeEach
    void setUp() {
        autoCloseable = MockitoAnnotations.openMocks(this);
        this.userServiceImpl = new UserServiceImpl(this.userRepository,this.modelMapper);
        userDto = new UserDto(1,"Raj","abcd");
        user = new User(1,"Raj","abcd");
    }

    @AfterEach
    void tearDown() throws Exception{
        autoCloseable.close();
    }

    @Test
    void givenUserInfoWhenSaveUserThenSaveUserStatus()
    {
        when(userRepository.save(user)).thenReturn(user);
        assertThat(userServiceImpl.saveUser(userDto)).isEqualTo(user);
    }

    @Test
    void whenFindAllUserThenGetUserStatus() {
        when(userRepository.findAll()).thenReturn( new ArrayList<User>(Collections.singleton(user)));
        assertThat(userServiceImpl.getUser().get(0).getUserId()).isEqualTo(userDto.getUserId());

    }

    @Test
    void givenUserIdWhenFindUserThenGetUserStatus() throws UserNotFoundException {
        when(userRepository.findById(1)).thenReturn(Optional.of(user));
        assertThat(userServiceImpl.getUserById(1).getUserId()).isEqualTo(user.getId());
    }

    @Test
    void givenUserIdWhenDeleteUserThenDeleteUserStatus() throws UserNotFoundException {

        when(userRepository.findById(1)).thenReturn(Optional.of(user));

        String result = userServiceImpl.deleteUser(1);
        verify(userRepository).deleteById(1);
        assertThat(result).isEqualTo("Success");
    }
}
