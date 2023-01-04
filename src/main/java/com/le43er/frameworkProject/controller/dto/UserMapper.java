package com.le43er.frameworkProject.controller.dto;

import com.le43er.frameworkProject.model.User;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserMapper {
    UserDto user2userDto(User user);

    User userDto2user(UserDto userDto);
}
