package com.le43er.frameworkProject.controller;

import com.le43er.frameworkProject.controller.dto.UserDto;
import com.le43er.frameworkProject.controller.dto.UserMapper;
import com.le43er.frameworkProject.dao.entity.UserEntity;
import com.le43er.frameworkProject.model.User;
import com.le43er.frameworkProject.service.Exceptions.UserNotFoundException;
import com.le43er.frameworkProject.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import java.util.Collection;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@Api(tags = "User registration")
@RestController
@RequiredArgsConstructor
@RequestMapping("/user")
public class UserController {

    private final Logger log = LoggerFactory.getLogger(this.getClass());

    private final UserService userService;
    private final UserMapper  userMapper;

    @Autowired
    BCryptPasswordEncoder passwordEncoder;

    @GetMapping("/")
    @ResponseBody
    public String registerForm() {
        return "<html><body><h1>Registration</h1><form method='post' action='/user/register'>" +
            "Username: <input type='text' name='username' /><br />" +
            "Password: <input type='password' name='password' /><br />" +
            "Email: <input type='email' name='email' /><br />" +
            "<input type='submit' value='Regisztráció' /></form></body></html>";
    }

    @GetMapping("/admin/getUsers")
    @ResponseBody
    public Collection<UserDto> getUsers() {
        return userService
            .getUsers()
            .stream()
            .map(userMapper::user2userDto)
            .collect(Collectors.toList());
    }

    @ApiOperation("Register user")
    @PostMapping({"/register", "/", "/reg", "/add"})
    @ResponseBody
    public String register(UserDto userDto) {
        userDto.setPassword(passwordEncoder.encode(userDto.getPassword()));
        userService.registerUser(userMapper.userDto2user(userDto));
        return "<html><body><p>Thank you for registering!</p></body></html>";
    }

    @ApiOperation("Modify user")
    @RequestMapping(value = "/modify", method = RequestMethod.POST)
    public String modifyTodo(@RequestBody UserDto userDto) throws UserNotFoundException {
        log.debug(userMapper.userDto2user(userDto).toString());
        userDto.setPassword(passwordEncoder.encode(userDto.getPassword()));
        userService.modify(userMapper.userDto2user(userDto));
        return "ok";
    }

    @ApiOperation("Set user to inactive")
    @RequestMapping(value = "/delete", method = RequestMethod.DELETE)
    public String deleteTodo(@RequestBody UserDto userDto) throws UserNotFoundException {
        log.debug(userMapper.userDto2user(userDto).toString());
        userService.delete(userMapper.userDto2user(userDto));
        return "ok";
    }

}

