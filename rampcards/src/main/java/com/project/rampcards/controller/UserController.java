package com.project.rampcards.controller;

import com.project.rampcards.dto.UserDto;
import com.project.rampcards.entity.User;
import com.project.rampcards.exceptionhandler.UserNotFoundException;
import com.project.rampcards.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/ramp-card/v1/user")
public class UserController {
    @Autowired
    private UserService userService;

    @GetMapping("/")
    public ResponseEntity<List<UserDto>> showUser()
    {
        return new ResponseEntity<>(userService.getUser(), HttpStatus.OK);
    }
    @GetMapping("/{id}")
    public ResponseEntity<UserDto> showUserById(@PathVariable(value="id") int id) throws UserNotFoundException
    {
        return new ResponseEntity<>(userService.getUserById(id), HttpStatus.OK);
    }
    @PostMapping("/")
    public ResponseEntity<User> addUser(@RequestBody UserDto userDto)
    {

        return new ResponseEntity<>(userService.saveUser(userDto),HttpStatus.CREATED);
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteUser(@PathVariable(value="id") int id) throws UserNotFoundException
    {
        return new ResponseEntity<>(userService.deleteUser(id),HttpStatus.OK);
    }
}
