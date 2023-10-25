package com.project.rampcards.controller;

import com.project.rampcards.dto.UserDto;
import com.project.rampcards.entity.User;
import com.project.rampcards.exceptionhandler.UserNotFoundException;
import com.project.rampcards.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UserControllerTest {

    @InjectMocks
    private UserController userController;

    @Mock
    private UserService userService;

    int userId;
    String message;
    List<UserDto> userDtoList;
    UserDto userDto;
    User user;
    AutoCloseable autoCloseable;

    @BeforeEach
    public void setUp() {
        autoCloseable = MockitoAnnotations.openMocks(this);
        userDtoList = new ArrayList<>();
        userDtoList.add(new UserDto(1,"user1","aaa"));
        userDtoList.add(new UserDto(2,"user2","bbb"));
        userDto = new UserDto(1,"user1","aaa");
        user = new User(1, "user1","aaa");
        userId = 1;
        message = "Success";
    }

    @Test
    void whenShowAllUserThenGetAllUserStatus() {
        when(userService.getUser()).thenReturn(userDtoList);

        ResponseEntity<List<UserDto>> response = userController.showUser();

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(userDtoList).isEqualTo(response.getBody());
    }

    @Test
    void givenUserIdWhenShowAllUserThenGetAllUserStatus() throws UserNotFoundException {

        when(userService.getUserById(1)).thenReturn(userDto);

        ResponseEntity<UserDto> response = userController.showUserById(1);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(userDto).isEqualTo(response.getBody());
    }

    @Test
    void givenUserInfoWhenAddUserThenAddUserStatus(){

        when(userService.saveUser(userDto)).thenReturn(user);

        ResponseEntity<User> response = userController.addUser(userDto);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(user, response.getBody());
    }

    @Test
    void givenUserIdWhenRemoveUserThenRemoveUserStatus() throws UserNotFoundException {

        when(userService.deleteUser(userId)).thenReturn(message);

        ResponseEntity<String> response = userController.deleteUser(userId);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(message).isEqualTo(response.getBody());
    }

    //negative test cases

    @Test
    void givenInvalidUserIdWhenShowAllUserThenGetAllUserStatus() throws UserNotFoundException {
        int userId = 8;
        when(userService.getUserById(userId)).thenThrow(UserNotFoundException.class);

        assertThrows(UserNotFoundException.class, () -> {
            userController.showUserById(userId); });

  /*      ResponseEntity<UserDto> response = userController.showUserById(userId);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);*/
    }

    @Test
    void givenInvalidUserIdWhenDeleteUserThenDeleteUserStatus() throws UserNotFoundException {
        int userId = 8;
        when(userService.deleteUser(userId)).thenThrow(UserNotFoundException.class);


        assertThrows(UserNotFoundException.class, () -> {
            userController.deleteUser(userId);
/*        ResponseEntity<String> response = userController.deleteUser(userId);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);*/
    });
    }

}
