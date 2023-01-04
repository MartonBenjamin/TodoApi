package com.le43er.frameworkProject.controller.dto;

import jakarta.validation.constraints.NotBlank;
import java.util.HashSet;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserDto {
    private Long id;

    @NotBlank(message = "username cannot be empty")
    private String username;

    @NotBlank(message = "password cannot be empty")
    private String password;

    @NotBlank(message = "email cannot be empty")
    private String email;

    private Set<RoleDto> roles = new HashSet<>();
}
