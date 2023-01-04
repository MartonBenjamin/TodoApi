package com.le43er.frameworkProject.controller.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TodoCategoryDto {
    private Long id;

    @NotBlank(message = "name cannot be empty")
    private String name;

    private String description;

    private LocalDateTime created_at = LocalDateTime.now();
}
