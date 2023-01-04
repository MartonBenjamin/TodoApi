package com.le43er.frameworkProject.service;

import com.le43er.frameworkProject.model.User;
import com.le43er.frameworkProject.service.Exceptions.UserNotFoundException;
import java.util.Collection;

public interface UserService {
    Collection<User> getUsers();
    User findByUsername(String username);

    String registerUser(User user);

    User modify(User user) throws UserNotFoundException;

    void delete(User user) throws UserNotFoundException;
}
