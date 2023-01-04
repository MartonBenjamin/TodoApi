package com.le43er.frameworkProject.controller;

import com.fasterxml.jackson.databind.node.ObjectNode;
import com.le43er.frameworkProject.controller.dto.RoleDto;
import com.le43er.frameworkProject.controller.dto.RoleMapper;
import com.le43er.frameworkProject.service.Exceptions.RoleAlreadyExistsException;
import com.le43er.frameworkProject.service.Exceptions.UserNotFoundException;
import com.le43er.frameworkProject.service.RoleService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import java.util.Collection;
import java.util.Map;
import java.util.stream.Collectors;
import javax.management.relation.RoleNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@Api(tags = "Roles")
@RequestMapping("/admin/role")
@RestController
@RequiredArgsConstructor
public class RoleController {

  private final RoleService roleService;
  private final RoleMapper roleMapper;

  @ApiOperation("Get Roles")
  @GetMapping(value = "/get")
  public Collection<RoleDto> getRoles() {
    return roleService
        .readAll()
        .stream()
        .map(roleMapper::role2RoleDto)
        .collect(Collectors.toList());
  }

  @ApiOperation("Add Role")
  @PostMapping(value = "/add")
  public RoleDto addCategory(@RequestBody RoleDto roleDto)
      throws RoleAlreadyExistsException {
    return roleMapper.role2RoleDto(
        roleService.record(roleMapper.roleDto2Role(roleDto))
    );
  }

  @ApiOperation("Remove Role")
  @DeleteMapping(value = "/delete")
  public RoleDto removeCategory(@RequestBody RoleDto roleDto)
      throws RoleNotFoundException {
    return
        roleMapper.role2RoleDto(
            roleService.delete(roleMapper.roleDto2Role(roleDto))
    );
  }

  @ApiOperation("Add Role to user")
  @PutMapping(value = "/addRole")
  public void addRoleToUser(@RequestBody ObjectNode objectNode)
      throws UserNotFoundException
      , RoleNotFoundException {
    RoleDto roleDto = new RoleDto();
    roleDto.setId(Long.parseLong(objectNode.get("roleDto").get("id").asText()));
    roleDto.setRole(objectNode.get("roleDto").get("role").asText());
    roleService.addRoleToUser(objectNode.get("username").asText(), roleMapper.roleDto2Role(roleDto));
  }

  @ApiOperation("Remove Role from user")
  @PutMapping(value = "/removeRole")
  public void removeRoleFromUser(@RequestBody ObjectNode objectNode)
      throws UserNotFoundException
      , RoleNotFoundException {
    RoleDto roleDto = new RoleDto();
    roleDto.setId(Long.parseLong(objectNode.get("roleDto").get("id").asText()));
    roleDto.setRole(objectNode.get("roleDto").get("role").asText());
    roleService.removeRoleFromUser(objectNode.get("username").asText(), roleMapper.roleDto2Role(roleDto));
  }
}
