package com.le43er.frameworkProject.controller.dto;

import com.le43er.frameworkProject.model.Role;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface RoleMapper {
    RoleDto role2RoleDto(Role role);

    Role roleDto2Role(RoleDto roleDto);
}
