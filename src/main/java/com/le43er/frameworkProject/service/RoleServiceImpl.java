package com.le43er.frameworkProject.service;

import com.le43er.frameworkProject.dao.RoleRepository;
import com.le43er.frameworkProject.dao.entity.RoleEntity;
import com.le43er.frameworkProject.model.Role;
import com.le43er.frameworkProject.model.User;
import com.le43er.frameworkProject.service.Exceptions.RoleAlreadyExistsException;
import com.le43er.frameworkProject.service.Exceptions.UserNotFoundException;
import java.util.Collection;
import java.util.stream.Collectors;
import javax.management.relation.RoleNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class RoleServiceImpl implements RoleService {

  private final RoleRepository roleRepository;
  private final UserService userService;

  public static RoleEntity convertRole2RoleEntity(Role role) {
    return RoleEntity.builder()
        .id(role.getId())
        .role(role.getRole())
        .build();
  }

  public static Role convertRoleEntity2Role(RoleEntity roleEntity) {
    return new Role(
        roleEntity.getId(),
        roleEntity.getRole()
    );
  }

  @Override
  public Collection<Role> readAll() {
    return roleRepository
        .findAll()
        .stream()
        .map(RoleServiceImpl::convertRoleEntity2Role)
        .collect(Collectors.toList());
  }

  @Override
  public Role record(Role role) throws RoleAlreadyExistsException {
    if (roleRepository.findByRole(role.getRole()) != null) {
      throw new RoleAlreadyExistsException();
    }
    return convertRoleEntity2Role(
        roleRepository.save(convertRole2RoleEntity(role))
    );
  }

  @Override
  public Role delete(Role role) throws RoleNotFoundException {
    Role roleToRemove = convertRoleEntity2Role(roleRepository.findByRole(role.getRole()));
    roleRepository.delete(convertRole2RoleEntity(roleToRemove));
    return roleToRemove;
  }

  @Override
  public void addRoleToUser(String username, Role role)
      throws UserNotFoundException, RoleNotFoundException {
    User userToModify = getUser(username);
    RoleEntity roleEntity = getRoleEntity(role);
    Role roleToAdd = convertRoleEntity2Role(roleEntity);
    userToModify.getRoles().add(roleToAdd);
  }

  @Override
  public void removeRoleFromUser(String username, Role role)
      throws UserNotFoundException, RoleNotFoundException {
    User userToModify = getUser(username);
    RoleEntity roleEntity = getRoleEntity(role);
    Role roleToRemove = convertRoleEntity2Role(roleEntity);
    userToModify.getRoles().remove(roleToRemove);
  }

  private RoleEntity getRoleEntity(Role role) throws RoleNotFoundException {
    RoleEntity roleEntity = roleRepository.findByRole(role.getRole());
    if (roleEntity == null) {
      throw new RoleNotFoundException();
    }
    return roleEntity;
  }

  private User getUser(String user) throws UserNotFoundException {
    User userToModify = userService.findByUsername(user);
    if (userToModify == null) {
      throw new UserNotFoundException();
    }
    return userToModify;
  }

}
