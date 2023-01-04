package com.le43er.frameworkProject.controller.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TodoDto {
    private Long id;

    @NotBlank(message = "name cannot be empty")
    private String name;

    @Positive(message = "due_to must be positive")
    private LocalDateTime due_to;

    private LocalDateTime created = LocalDateTime.now();

    @Valid
    private UserDto userDto;

    private TodoCategoryDto todoCategoryDto;
}
