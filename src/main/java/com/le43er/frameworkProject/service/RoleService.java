package com.le43er.frameworkProject.service;

import com.le43er.frameworkProject.model.Role;
import com.le43er.frameworkProject.model.User;
import com.le43er.frameworkProject.service.Exceptions.RoleAlreadyExistsException;

import com.le43er.frameworkProject.service.Exceptions.UserNotFoundException;
import java.util.Collection;
import javax.management.relation.RoleNotFoundException;

public interface RoleService {
    Collection<Role> readAll();
    Role record(Role role) throws RoleAlreadyExistsException;
    Role delete(Role role) throws RoleNotFoundException;
    void addRoleToUser(String username, Role role) throws UserNotFoundException, RoleNotFoundException;
    void removeRoleFromUser(String username, Role role)
        throws UserNotFoundException, RoleNotFoundException;
}
