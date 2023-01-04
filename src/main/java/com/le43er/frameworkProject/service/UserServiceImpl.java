package com.le43er.frameworkProject.service;

import com.le43er.frameworkProject.config.UserDetailsImpl;
import com.le43er.frameworkProject.dao.RoleRepository;
import com.le43er.frameworkProject.dao.UserRepository;
import com.le43er.frameworkProject.dao.entity.RoleEntity;
import com.le43er.frameworkProject.dao.entity.UserEntity;
import com.le43er.frameworkProject.model.User;
import com.le43er.frameworkProject.service.Exceptions.UserNotFoundException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService, UserDetailsService {

  private final UserRepository userRepository;

  private final EmailServiceImpl emailServiceImpl;

  private final RoleRepository roleRepository;

  private final String DEFAULT_ROLE = "USER";

  private final String ADMIN_ROLE = "ADMIN";

  private final List<String> adminEmails = new ArrayList<>(Arrays.asList(
      "${spring.mail.username}"
      , "admin@admin.com"
  ));

  public static UserEntity convertUser2UserEntity(
      User user) {
    return UserEntity.builder()
        .id(user.getId())
        .username(user.getUsername())
        .password(user.getPassword())
        .email(user.getEmail())
        .isActive(user.getIsActive())
        .created(user.getCreated())
        .roles(user.getRoles().stream().map(RoleServiceImpl::convertRole2RoleEntity).collect(
            Collectors.toSet()))
        .build();
  }

  public static User convertUserEntity2User(UserEntity user) {
    return new User(
        user.getId(),
        user.getUsername(),
        user.getPassword(),
        user.getEmail(),
        user.isActive(),
        user.getCreated(),
        user.getRoles().stream().map(RoleServiceImpl::convertRoleEntity2Role)
            .collect(Collectors.toSet())
    );
  }

  @Override
  public Collection<User> getUsers() {
    return userRepository
        .findAll()
        .stream()
        .map(UserServiceImpl::convertUserEntity2User)
        .collect(Collectors.toList());
  }

  @Override
  public User findByUsername(String username) {
    if(userRepository.findByUsername(username) != null)
    return convertUserEntity2User(
        userRepository.findByUsername(username)
    );
    return null;
  }

  @Override
  public String registerUser(User user) {
    User checkUser = findByUsername(user.getUsername());
    boolean isAdmin = adminEmails.contains(user.getEmail());

    if (checkUser != null) {
      return "exists";
    }

    RoleEntity userRole = roleRepository.findByRole(DEFAULT_ROLE);
    user.setRoles(new HashSet<>());
    user.setIsActive(true);
    user.setCreated(LocalDateTime.now());
    if (isAdmin) {
      RoleEntity adminRole = roleRepository.findByRole(ADMIN_ROLE);
      if (adminRole != null) {
        user.getRoles().add(RoleServiceImpl.convertRoleEntity2Role(adminRole));
      } else {
        user.addRoles(ADMIN_ROLE);
      }
    }
    if (userRole != null) {
      user.getRoles().add(RoleServiceImpl.convertRoleEntity2Role(userRole));
    } else {
      user.addRoles(DEFAULT_ROLE);
    }
    userRepository.save(convertUser2UserEntity(user));
    emailServiceImpl.sendRegistrationMessage(user.getEmail(), user.getUsername());
    log.debug("Registered user:" + user.getUsername() + "with email: " + user.getEmail());
    return "ok";
  }

  @Override
  public User modify(User user) throws UserNotFoundException {
    UserEntity checkUser = userRepository.findByUsername(user.getUsername());
    if (checkUser != null) {
      return convertUserEntity2User(
          userRepository.save(convertUser2UserEntity(user))
      );
    } else {
      throw new UserNotFoundException();
    }
  }

  @Override
  public void delete(User user) throws UserNotFoundException {
    UserEntity checkUser = userRepository.findByUsername(user.getUsername());
    if (checkUser != null) {
      userRepository.save(convertUser2UserEntity(user));
    }
    throw new UserNotFoundException();
  }

  @Override
  public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
    User user = findByUsername(username);
    if (user == null) {
      throw new UsernameNotFoundException(username);
    }

    return new UserDetailsImpl(convertUser2UserEntity(user));
  }
}
