package com.le43er.frameworkProject.controller.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RoleDto {
    private Long id;

    @NotBlank(message = "role name cannot be empty")
    private String role;
}
